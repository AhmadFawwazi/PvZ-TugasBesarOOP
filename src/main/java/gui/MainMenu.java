package gui;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;

public class MainMenu extends JPanel {
    private Runnable onContinue;
    private Image backgroundImage;
    private GameGUI gameGUI;

    public MainMenu(GameGUI gameGUI) {
        this.gameGUI = gameGUI;
        initializeGUI();
        try {
            backgroundImage = new ImageIcon(getClass().getResource("/resources/images/background/Menu.png")).getImage();
        } catch (Exception e) {
            e.printStackTrace(); // Handle potential errors gracefully
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this); // Draw the background image to fit the panel size
        }
    }

    private void initializeGUI() {
        setLayout(null); // Use null layout to manually position buttons

        // Create and place buttons
        add(createButton("Start", 535, 435, "/resources/images/background/StartButton.png"));
        add(createButton("Plants", 400, 540, "/resources/images/background/PlantsButton.png"));
        add(createButton("Zombies", 650, 540, "/resources/images/background/ZombiesButton.png"));
        add(createButton("Help", 50, 33, "/resources/images/background/HelpButton.png"));
        add(createButton("Exit", 1050, 33, "/resources/images/background/ExitButton.png"));

        revalidate();
        repaint();
    }

    private JButton createButton(String actionCommand, int x, int y, String iconPath) {
        ImageIcon icon = new ImageIcon(getClass().getResource(iconPath));
        JButton button = new JButton(icon);
        button.setActionCommand(actionCommand);
        button.setBounds(x, y, icon.getIconWidth(), icon.getIconHeight());
        button.addActionListener(this::buttonAction);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        return button;
    }
    
    private void buttonAction(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "Start":
                gameGUI.showPlantSelectScreen(onContinue);
                break;
            case "Plants":
                gameGUI.showPlantList();
                break;
            case "Zombies":
                gameGUI.showZombieList();
                break;
            case "Help":
                gameGUI.showHelpScreen();
                break;
            case "Exit":
                System.exit(0);
                break;
            default:
                System.out.println("Unknown command: " + command);
                break;
        }
    }

    public void setOnContinue(Runnable onContinue) {
        this.onContinue = onContinue;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Michael vs. Lalapan");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280, 720);
        frame.setLocationRelativeTo(null);

        GameGUI gameGUI = new GameGUI();
        MainMenu menu = new MainMenu(gameGUI);
        frame.setContentPane(menu);
        frame.setVisible(true);
    }
}
