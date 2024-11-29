package gui;

import javax.swing.*;

import data.GameState;

import java.awt.*;
import entity.plant.Plant;
import manager.DeckTanaman;
import util.ListOf;

public class PlantSelectScreen extends JPanel {
    private Image backgroundImage;
    private JPanel deckPanel;
    private JPanel inventoryPanel;
    private JPanel topPanel;
    private JButton startButton;
    private JButton backButton;
    private JButton swapModeButton;
    private GameGUI gameGUI;
    private Runnable onContinue;

    private boolean isSwapMode = false; // Flag to indicate if swap mode is enabled
    private Plant selectedPlantForSwap = null;
    private JButton selectedButtonForSwap = null;
    private ListOf<JButton> deckButtons = new ListOf<JButton>();
    private ListOf<JButton> inventoryButtons = new ListOf<JButton>();

    public PlantSelectScreen(GameGUI gameGUI) {
        this.gameGUI = gameGUI;
        setSize(1280, 720);
        setLayout(new BorderLayout());
        loadImage();
        initializeUI();
    }

    private void loadImage() {
        try {
            backgroundImage = new ImageIcon(getClass().getResource("/resources/images/background/SelectPlant.png")).getImage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    private void initializeUI() {
        setLayout(new BorderLayout());
        
        // Main top panel with BoxLayout for horizontal alignment
        topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.LINE_AXIS));
        topPanel.setOpaque(false);
        
        // Deck Panel setup
        deckPanel = new BackgroundPanel("/resources/images/background/FrameAtas.png");
        deckPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        deckPanel.setPreferredSize(new Dimension(530, 100));  // Adjust dimensions as needed
        deckPanel.setOpaque(false);
        
        // Button Panel setup
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
        
        startButton = new JButton(new ImageIcon(getClass().getResource("/resources/images/background/startselect.png")));
        backButton = new JButton(new ImageIcon(getClass().getResource("/resources/images/background/backselect.png")));
        swapModeButton = new JButton(new ImageIcon(getClass().getResource("/resources/images/background/swapselect.png"))); // New button for swap mode
        styleButton(startButton);
        styleButton(backButton);
        styleButton(swapModeButton);

        startButton.addActionListener(e -> {
            if (GameState.getInstance().getDeck().isFull()) {
                if (onContinue != null) {
                    onContinue.run();
                }
                gameGUI.showGameScreen();
            } else {
                JOptionPane.showMessageDialog(this, "Deck is not full");
            }
        });
        backButton.addActionListener(e -> gameGUI.showMainMenu());
        swapModeButton.addActionListener(e -> {
            isSwapMode = !isSwapMode;
            if (isSwapMode) {
            } else {
                clearSwapSelection();
            }
        });

        buttonPanel.add(Box.createHorizontalGlue()); // Add glue to push buttons to the right
        buttonPanel.add(startButton);
        buttonPanel.add(backButton);
        buttonPanel.add(swapModeButton);
        
        // Bot panel setup
        JPanel botPanel = new JPanel();
        botPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        botPanel.setOpaque(false);
        botPanel.setPreferredSize(new Dimension(1090, 160));  // Adjust dimensions as needed

        // South panel for inventory
        inventoryPanel = new BackgroundPanel("/resources/images/background/FrameBawah.png"); // specify the path to your background image
        inventoryPanel.setPreferredSize(new Dimension(920, 160));
        inventoryPanel.setLayout(new FlowLayout(3, 10, 50));
        
        // Populate inventory from inventory class
        ListOf<Plant> availablePlants = GameState.getInstance().getInventory().getAllItems();
        for (int i = 0; i < availablePlants.size(); i++) {
            final Plant plant = availablePlants.get(i);  // make plant final for use in lambda
            JButton button = new JButton(new ImageIcon(getClass().getResource("/resources/images/background/selectplant" + plant.getName() + ".png")));
            styleButton(button);
            button.setPreferredSize(new Dimension(80, 85));
            button.addActionListener(e -> handleInventoryPlantClick(plant, button));
            inventoryButtons.add(button);
            inventoryPanel.add(button);
        }
        
        // Add components to top panel
        topPanel.add(deckPanel);
        topPanel.add(Box.createHorizontalStrut(10)); // Spacer between deckPanel and buttonPanel
        topPanel.add(buttonPanel);
        
        // Add the top panel to the main frame
        add(topPanel, BorderLayout.NORTH);

        // add components to bot panel
        botPanel.add(inventoryPanel);
        botPanel.add(Box.createHorizontalStrut(10)); // Spacer between inventoryPanel and buttonPanel
        
        // Add the bot panel to the main frame
        add(botPanel, BorderLayout.SOUTH);
    }

    private void refreshDeckPanel() {
        deckPanel.removeAll();
        deckButtons.clear();
        for (int i = 0; i < GameState.getInstance().getDeck().getPlants().size(); i++) {
            final Plant plant = GameState.getInstance().getDeck().getPlants().get(i);
            JButton button = new JButton(new ImageIcon(getClass().getResource("/resources/images/background/selectplant" + plant.getName() + ".png")));
            styleButton(button);
            button.setPreferredSize(new Dimension(80, 85));
            button.addActionListener(e -> handleDeckPlantClick(plant, button));
            deckButtons.add(button);
            deckPanel.add(button);
        }
        deckPanel.revalidate();
        deckPanel.repaint();
    }

    private void handleInventoryPlantClick(Plant plant, JButton button) {
        if (isSwapMode) {
            if (selectedPlantForSwap == null) {
                selectedPlantForSwap = plant;
                selectedButtonForSwap = button;
                button.setBorderPainted(true);
                button.setBorder(BorderFactory.createLineBorder(Color.RED));
            } else if (selectedPlantForSwap == plant) {
                selectedPlantForSwap = null;
                selectedButtonForSwap.setBorderPainted(false);
                selectedButtonForSwap = null;
            } else {
                swapPlantsInInventory(selectedPlantForSwap, plant);
                selectedPlantForSwap = null;
                selectedButtonForSwap.setBorderPainted(false);
                selectedButtonForSwap = null;
                refreshInventoryPanel();
            }
        } else {
            // Add plant to deck if not in swap mode
            if (GameState.getInstance().getDeck().getPlants().contains(plant)) {
                JOptionPane.showMessageDialog(this, "Plant already in deck");
            } else if (GameState.getInstance().getDeck().isFull()) {
                JOptionPane.showMessageDialog(this, "Deck is full");
            } else {
                GameState.getInstance().getDeck().addPlant(plant);
                refreshDeckPanel();
            }
        }
    }

    private void handleDeckPlantClick(Plant plant, JButton button) {
        if (selectedPlantForSwap == null) {
            // Select plant for swap
            selectedPlantForSwap = plant;
            selectedButtonForSwap = button;
            button.setBorderPainted(true);
            button.setBorder(BorderFactory.createLineBorder(Color.RED));
        } else {
            // Swap logic for deck
            if (selectedPlantForSwap == plant) {
                GameState.getInstance().getDeck().removePlant(plant);
                refreshDeckPanel();
                selectedPlantForSwap = null;
                selectedButtonForSwap.setBorderPainted(false);
                selectedButtonForSwap = null;
            } else {
                swapPlantsInDeck(selectedPlantForSwap, plant);
                selectedPlantForSwap = null;
                selectedButtonForSwap.setBorderPainted(false);
                selectedButtonForSwap = null;
                refreshDeckPanel();
            }
        }
    }

    private void refreshInventoryPanel() {
        inventoryPanel.removeAll();
        for (int i = 0; i < GameState.getInstance().getInventory().getAllItems().size(); i++) {
            final Plant plant = GameState.getInstance().getInventory().getAllItems().get(i);
            JButton button = new JButton(new ImageIcon(getClass().getResource("/resources/images/background/selectplant" + plant.getName() + ".png")));
            styleButton(button);
            button.setPreferredSize(new Dimension(80, 85));
            button.addActionListener(e -> handleInventoryPlantClick(plant, button));
            inventoryButtons.add(button);
            inventoryPanel.add(button);
        }
        inventoryPanel.revalidate();
        inventoryPanel.repaint();
    }

    private void swapPlantsInDeck(Plant plant1, Plant plant2) {
        DeckTanaman deck = GameState.getInstance().getDeck();
        int index1 = deck.getPlants().indexOf(plant1);
        int index2 = deck.getPlants().indexOf(plant2);
        deck.getPlants().swap(index1, index2);
    }

    private void swapPlantsInInventory(Plant plant1, Plant plant2) {
        ListOf<Plant> inventory = GameState.getInstance().getInventory().getAllItems();
        int index1 = inventory.indexOf(plant1);
        int index2 = inventory.indexOf(plant2);
        inventory.swap(index1, index2);
    }

    private void styleButton(JButton button) {
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setOpaque(false);
    }

    private void clearSwapSelection() {
        if (selectedButtonForSwap != null) {
            selectedButtonForSwap.setBorderPainted(false);
            selectedButtonForSwap = null;
        }
        selectedPlantForSwap = null;
    }

    public void setOnContinue(Runnable onContinue) {
        this.onContinue = onContinue;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Plant Selection");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GameGUI gameGUI = new GameGUI();
        frame.setContentPane(new PlantSelectScreen(gameGUI));
        frame.setSize(1280, 720);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

class BackgroundPanel extends JPanel {
    private Image backgroundImage;

    // Constructor to load the background image
    public BackgroundPanel(String imagePath) {
        try {
            backgroundImage = new ImageIcon(getClass().getResource(imagePath)).getImage();
            setOpaque(false); // Make panel transparent
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            // Draw the background image scaled to fit the panel
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
