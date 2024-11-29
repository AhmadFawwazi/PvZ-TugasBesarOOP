package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class HelpScreen extends JPanel{
    private Image backgroundImage;
    private GameGUI gameGUI;

    public HelpScreen(GameGUI gameGUI) {
        this.gameGUI = gameGUI;
        initializeGUI();
        try {
            backgroundImage = new ImageIcon(getClass().getResource("/resources/images/background/HelpPage.png")).getImage();
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
        add(createButton("Back", 20, 29, "/resources/images/description/backdescplant.png"));

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
            case "Back":
                gameGUI.showMainMenu();
                break;
            default:
                System.out.println("Unknown command: " + command);
                break;
        }
    }
}
