import data.GameState;
import gui.GameGUI;

import javax.swing.SwingUtilities;

import data.GameEngine;


public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            System.out.println("Initializing GameState...");
            GameState gameState = GameState.getInstance();
            System.out.println("Initializing GameGUI...");
            GameGUI gameGUI = GameGUI.getInstance();
            System.out.println("Starting GameEngine...");
            GameEngine gameEngine = GameEngine.getInstance(); // Assuming 60 FPS
            gameEngine.start();
            System.out.println("Setting GameGUI visible...");
            gameGUI.setVisible(true);
        });
    }
}


