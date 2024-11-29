package gui;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

import data.GameEngine;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class LoseScreen extends JPanel {
    private Image backgroundImage;
    private GameGUI gameGUI;
    private Clip clip;

    public LoseScreen(GameGUI gameGUI) {
        this.gameGUI = gameGUI;
        initializeGUI();
        try {
            backgroundImage = new ImageIcon(getClass().getResource("/resources/images/background/39.png")).getImage();
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
        add(createButton("MainMenu", 535, 535, "/resources/images/background/mainMenu.png"));


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
            case "MainMenu":
                GameEngine.getInstance().start(); 
                System.out.println("Back to Main Menu");
                break;
            default:
                System.out.println("Unknown command: " + command);
                break;
        }
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
        frame.setSize(1280, 720);
        frame.setLocationRelativeTo(null);

        GameGUI gameGUI = new GameGUI();
        LoseScreen menu = new LoseScreen(gameGUI);
        frame.setContentPane(menu);
        frame.setVisible(true);
    }
}
