package gui;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import manager.DeckTanaman;
import util.ListOf;
import data.GameState;
import data.TimeKeeper;
import entity.Projectile;
import entity.plant.Plant;
import entity.zombie.Zombie;

public class GamePanel extends JPanel implements ActionListener {
    private Image backgroundImage;
    private final int CELL_SIZE = 97;
    private final int startX = 10;
    private final int startY = 20;
    private JPanel topBarPanel;
    private JPanel deckPanel;
    private JLabel sunPointsLabel;
    private String selectedPlant;
    private boolean shovelActive = false; // Track shovel state
    private Timer timer = new Timer(1000, this);
    private Clip clip;

    public GamePanel() {
        setPreferredSize(new Dimension(1280, 720));
        setLayout(new BorderLayout());
        // drawMenuButton();
        drawTopBar();
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                handleMouseClick(e.getX(), e.getY());
            }
        });

        timer.start();
    }

    private void loadImage() {
        try {
            if (TimeKeeper.getInstance().isDaytime()) {
                backgroundImage = new ImageIcon(getClass().getResource("/resources/images/background/PoolBg.png")).getImage();
            } else {
                backgroundImage = new ImageIcon(getClass().getResource("/resources/images/background/NightPoolBg.png")).getImage();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        loadImage();

        // int panelWidth = getWidth();
        // int panelHeight = getHeight();

        // // Original configuration values
        // int originalPanelWidth = 1280; // Original panel width
        // int originalPanelHeight = 720; // Original panel height

        // // Calculate scaling factor based on width and height
        // double widthScaleFactor = (double) panelWidth / originalPanelWidth;
        // double heightScaleFactor = (double) panelHeight / originalPanelHeight;
        // scaleFactor = Math.min(widthScaleFactor, heightScaleFactor); // Maintain aspect ratio

        // // Calculate dynamic CELL_SIZE, startX, and startY
        // CELL_SIZE_scaled = (int) (CELL_SIZE * scaleFactor);
        // startX_scaled = (int) (startX * scaleFactor);
        // startY_scaled = (int) (startY * scaleFactor);

        drawBackground(g, startX, startY, CELL_SIZE);
        drawPlants(g, startX, startY, CELL_SIZE);
        drawZombies(g, startX, startY, CELL_SIZE);
        drawProjectiles(g, startX, startY, CELL_SIZE);
    }

    private void drawBackground(Graphics g, int startX, int startY, int CELL_SIZE) {
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }

        for (int row = 1; row < 7; row++) {
            for (int col = 2; col < 13; col++) {
                g.drawRect(startX + col * CELL_SIZE, startY + row * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }
    }

    private void drawPlants(Graphics g, int startX, int startY, int CELL_SIZE) {
        for (int i = 0; i < 6; i++) {
            for (int j = 1; j < 10; j++) {
                ListOf<Plant> plants = GameState.getInstance().getGameMap().getTile(i, j).getPlants();
                for (int k = 0; k < plants.size(); k++) {
                    Plant plant = plants.get(k);
                    ImageIcon plantIcon = new ImageIcon(getClass().getResource("/resources/images/plants/" + plant.getName() + ".png"));
                    g.drawImage(plantIcon.getImage(), startX + (j + 2) * CELL_SIZE + (CELL_SIZE - plantIcon.getIconWidth()), startY + (i + 1) * CELL_SIZE + (CELL_SIZE - plantIcon.getIconHeight()), plantIcon.getIconWidth(), plantIcon.getIconHeight(), this);
                }
            }
        }
    }

    private void drawZombies(Graphics g, int startX, int startY, int CELL_SIZE) {
        for (int i = 0; i < GameState.getInstance().getGameMap().getRows(); i++) {
            for (int j = 0; j < GameState.getInstance().getGameMap().getCols(); j++) {
                ListOf<Zombie> zombies = GameState.getInstance().getGameMap().getTile(i, j).getZombies();
                for (int k = 0; k < zombies.size(); k++) {
                    Zombie zombie = zombies.get(k);
                    ImageIcon zombieIcon = new ImageIcon(getClass().getResource("/resources/images/zombie/" + zombie.getName() + ".png"));
                    g.drawImage(zombieIcon.getImage(), startX + (j + 2) * CELL_SIZE, startY + (i + 1) * CELL_SIZE, zombieIcon.getIconWidth(), zombieIcon.getIconHeight(), this);
                }
            }
        }
    }

    private void drawProjectiles(Graphics g, int startX, int startY, int CELL_SIZE) {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 11; j++) {
                ListOf<Projectile> projectiles = GameState.getInstance().getGameMap().getTile(i, j).getProjectiles();
                for (int k = 0; k < projectiles.size(); k++) {
                    Projectile projectile = projectiles.get(k);
                    boolean hitZombie = false;

                    ListOf<Zombie> zombies = GameState.getInstance().getGameMap().getTile(i, j).getZombies();
                    if (!zombies.isEmpty()) {
                        hitZombie = true;
                    }

                    if (hitZombie) {
                        playBackgroundSound("/resources/sound/plants-vs-zombies-hit-2.wav");
                        projectiles.remove(projectile);
                    } else {
                        ImageIcon projectileIcon = new ImageIcon(getClass().getResource("/resources/images/background/" + projectile.getType() + ".png"));
                        g.drawImage(projectileIcon.getImage(), startX + (j + 2) * CELL_SIZE, startY + (i + 1) * CELL_SIZE, projectileIcon.getIconWidth(), projectileIcon.getIconHeight(), this);
                    }
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        if (ev.getSource() == timer) {
            repaint(); // This will call repaint every 1 second
        }
    }

    private void drawTopBar() {
        // Panel top bar
        topBarPanel = new JPanel();
        topBarPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        topBarPanel.setPreferredSize(new Dimension(1280, 130));
        topBarPanel.setOpaque(false);

        // Placeholder for deck tanaman panel
        deckPanel = new BackgroundPanel("/resources/images/background/SunDeck.png");
        deckPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        deckPanel.setPreferredSize(new Dimension(646, 111));  // Adjust size as needed
        deckPanel.setOpaque(false);

        // Label sun points
        sunPointsLabel = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawSunPoints(g);
            }
        };
        sunPointsLabel.setPreferredSize(new Dimension(100, 100));
        sunPointsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        deckPanel.add(sunPointsLabel);

        // membuat panel untuk tanaman
        JPanel plantPanel = new JPanel();
        plantPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 8, -26));
        plantPanel.setPreferredSize(new Dimension(505, 121));  // Adjust size as needed
        plantPanel.setOpaque(false);

        // Menambahkan tanaman ke deckPanel
        DeckTanaman plantDeck = GameState.getInstance().getDeck();

        // Menambahkan tanaman ke deckPanel
        for (int i = 0; i < plantDeck.getPlants().size(); i++) {
            String plant = plantDeck.getPlants().get(i).getName();
            ImageIcon plantIcon = new ImageIcon(getClass().getResource("/resources/images/cards/cards_" + plant + ".png"));
            JButton plantButton = new JButton(plantIcon);
            plantButton.setPreferredSize(new Dimension(76, 144));
            plantButton.setBorderPainted(false);
            plantButton.setContentAreaFilled(false);
            plantButton.setFocusPainted(false);
            plantButton.addActionListener(e -> selectedPlant = plant);
            plantPanel.add(plantButton);
        }

        deckPanel.add(plantPanel);
        topBarPanel.add(deckPanel);

        // Panel untuk shovel
        JPanel shovelPanel = new BackgroundPanel("/resources/images/background/Shovel.png");
        shovelPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        shovelPanel.setPreferredSize(new Dimension(82, 82));  // Adjust size as needed
        shovelPanel.setOpaque(false);

        // Button shovel
        ImageIcon shovelIcon = new ImageIcon(getClass().getResource("/resources/images/background/shovelremove.png"));
        JButton shovelButton = new JButton(shovelIcon);
        shovelButton.setBorderPainted(false);
        shovelButton.setContentAreaFilled(false);
        shovelButton.setFocusPainted(false);
        shovelButton.addActionListener(e -> toggleShovel());

        shovelPanel.add(shovelButton);

        // Add shovel panel to top bar panel
        topBarPanel.add(shovelPanel);

        // Panel untuk progress bar
        JPanel progressBarPanel = new BackgroundPanel("/resources/images/background/FlagLevel.png");
        progressBarPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        progressBarPanel.setPreferredSize(new Dimension(430, 111));  // Adjust size as needed
        progressBarPanel.setOpaque(false);

        // Progress bar
        JLabel progressBarlabel = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawProgressBar(g);
            }
        };
        progressBarlabel.setPreferredSize(new Dimension(430, 111));  // Adjust size as needed
        progressBarlabel.setOpaque(false);
        progressBarPanel.add(progressBarlabel);
        topBarPanel.add(progressBarPanel);

        // Add progress bar panel to top bar panel
        topBarPanel.add(progressBarPanel);

        add(topBarPanel, BorderLayout.NORTH);
    }

    private void handleMouseClick(int x, int y) {
        int col = ((x - startX) / CELL_SIZE) - 2;
        int row = ((y - startY) / CELL_SIZE) - 1;

        if (col >= 1 && col < 10 && row >= 0 && row < 6) {  // Adjust according to the grid range
            if (shovelActive) {
                if(GameState.getInstance().getGameMap().getTile(row, col).getPlants().size() > 0){
                    GameState.getInstance().getGameMap().removePlant(row, col);
                }
                if(GameState.getInstance().getGameMap().getTile(row, col).getPlants().size() > 0){
                    GameState.getInstance().getGameMap().removePlant(row, col);
                }
                if(GameState.getInstance().getGameMap().getTile(row, col).getPlants().size() > 0){
                    GameState.getInstance().getGameMap().removePlant(row, col);
                }
                shovelActive = false;
            } else if (selectedPlant != null) {
                GameState.getInstance().getGameMap().plantSpawner(selectedPlant, row, col);
                selectedPlant = null;
            }
        }
    }

    private void drawSunPoints(Graphics g) {
        // Draw sun points
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("" + GameState.getInstance().getSunPoints(), 30, 80);
    }

    private void drawProgressBar(Graphics g) {
        int x = 87;
        int y = 32; // Vertical position of the bar
        int totalDuration = TimeKeeper.DAY_LENGTH; // Total duration in seconds
        int maxWidth = 69 * 4; // Maximum width of the progress bar

        // Get current time from TimeKeeper
        int currentTime = TimeKeeper.getInstance().getCurrentTime();

        // Calculate the width of the progress bar based on current time
        int currentWidth = (int) ((double) currentTime / totalDuration * maxWidth);

        // max currentWidth is maxWidth
        if (currentWidth > maxWidth) {
            currentWidth = maxWidth;
        }

        // Draw the progress bar
        g.setColor(Color.RED);
        g.fillRect(x, y, currentWidth, 23);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, maxWidth, 23);
    }

    // private void drawMenuButton() {
    //     ImageIcon menuIcon = new ImageIcon(getClass().getResource("/resources/images/background/MenuButton.png"));
    //     JButton menuButton = new JButton(menuIcon);
    //     menuButton.setBorderPainted(false);
    //     menuButton.setContentAreaFilled(false);
    //     menuButton.setFocusPainted(false);
    //     menuButton.addActionListener(e -> GameState.getInstance().saveGame("jkwjson"));
    //     add(menuButton, BorderLayout.WEST);
    // }

    private void toggleShovel() {
        shovelActive = !shovelActive;
        System.out.println("Shovel Active: " + shovelActive);
    }

    private void playBackgroundSound(String resourcePath) {
        try {
            // Use getClass().getResourceAsStream inside AudioSystem to get an AudioInputStream
            InputStream audioSrc = getClass().getResourceAsStream(resourcePath);
            // Check if the input stream is null
            if (audioSrc == null) {
                System.err.println("Resource not found: " + resourcePath);
                return;
            }
            InputStream bufferedIn = new BufferedInputStream(audioSrc);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(bufferedIn);
            clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Michael vs. Lalapan");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new GamePanel());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}


    // private void drawBackground(Graphics g) {
    //     // Draw grid
    //     for (int row = 1; row < 7; row++) {
    //         for (int col = 2; col < 13; col++) {
    //             g.drawRect(startX + col * CELL_SIZE, startY + row * CELL_SIZE, CELL_SIZE, CELL_SIZE);
    //         }
    //     }
    // }

    // private void drawPlants(Graphics g) {
    //     // Draw every plant in the grid
    //     for(int i = 0; i < 6; i++){
    //         for(int j = 1; j < 10; j++){
    //             ListOf<Plant> plants = GameState.getInstance().getGameMap().getTile(i, j).getPlants();
    //             for(int k = 0; k < plants.size(); k++){
    //                 Plant plant = plants.get(k);
    //                 ImageIcon planticon = new ImageIcon(getClass().getResource("/resources/images/plants/" + plant.getName() + ".png"));
    //                 g.drawImage(planticon.getImage(), startX + (j + 2) * CELL_SIZE, startY + (i + 1) * CELL_SIZE, 90, 90, this);
    //             }
    //         }
    //     }
    // }

    // private void drawZombies(Graphics g) {
    //     // Draw every zombie in the grid
    //     for (int i = 0; i < GameState.getInstance().getGameMap().getRows(); i++) {
    //         for (int j = 0; j < GameState.getInstance().getGameMap().getCols(); j++) {
    //             ListOf<Zombie> zombies = GameState.getInstance().getGameMap().getTile(i, j).getZombies();

    //             for (int k = 0; k < zombies.size(); k++) {
    //                 Zombie zombie = zombies.get(k);
    //                 ImageIcon zombieIcon = new ImageIcon(getClass().getResource("/resources/images/zombie/" + zombie.getName() + ".png"));
    //                 g.drawImage(zombieIcon.getImage(), startX + (j + 2) * CELL_SIZE, startY + (i + 1) * CELL_SIZE, 90, 90, this);
    //             }
    //         }
    //     }
    // }

    // private void drawProjectiles(Graphics g) {
    //     // Implement drawing projectiles based on gameState

    //     // Draw every projectile in the grid
    //     for(int i = 0; i < 6; i++){
    //         for(int j = 0; j < 11; j++){
    //             ListOf<Projectile> projectiles = GameState.getInstance().getGameMap().getTile(i, j).getProjectiles();
    //             for(int k = 0; k < projectiles.size(); k++){
    //                 Projectile projectile = projectiles.get(k);
    //                 boolean hitZombie = false;

    //                 // Check if the projectile hits a zombie
    //                 ListOf<Zombie> zombies = GameState.getInstance().getGameMap().getTile(i, j).getZombies();
    //                 if (!zombies.isEmpty()) {
    //                     hitZombie = true;
    //                 }

    //                 // If the projectile hits a zombie, remove the projectile
    //                 if (hitZombie) {
    //                     projectiles.remove(projectile);
    //                 } else {
    //                     ImageIcon projectileicon = new ImageIcon(getClass().getResource("/resources/images/background/" + projectile.getType() + ".png"));
    //                     g.drawImage(projectileicon.getImage(), (startX + (j + 2) * CELL_SIZE) + 53, (startY + (i + 1) * CELL_SIZE) + 5, 33, 30, this);                      
    //                 }
    //             }
    //         }
    //     }
    // }