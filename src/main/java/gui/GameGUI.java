package gui;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

import java.awt.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class GameGUI extends JFrame {
    private MainMenu mainMenu;
    private PlantList plantList;
    private ZombieList zombieList;
    private PlantSelectScreen plantSelectScreen;
    private HelpScreen helpScreen;  
    private GamePanel gameScreen;  
    private boolean isGameScreenDisplayed = false;
    private LoseScreen loseScreen;
    private WinScreen winScreen;
    private Clip clip;

    private static GameGUI instance;


    public static GameGUI getInstance() {
        if (instance == null) {
            instance = new GameGUI();
        }
        return instance;
    }

    public GameGUI() {
        initializeFrame();
        initializePanels();
        showMainMenu();
    }

    private void initializeFrame() {
        setTitle("Michael vs. Lalapan");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1280, 720);
        setLocationRelativeTo(null);
        setBackground(Color.BLACK);
        setVisible(true);
    }

    private void initializePanels() {
        mainMenu = new MainMenu(this);
        plantList = new PlantList(this);
        zombieList = new ZombieList(this);
        plantSelectScreen = new PlantSelectScreen(this);
        helpScreen = new HelpScreen(this); 
        loseScreen = new LoseScreen(this);
        winScreen = new WinScreen(this);
    }

    public void showMainMenu() {
        if (clip != null) {
            clip.stop();
        }
        playBackgroundSound("/resources/sound/backsound.wav");
        setPanel(mainMenu);
        this.setGameScreenDisplayed(false);
    }

    public void showPlantList() {
        setPanel(plantList);
        this.setGameScreenDisplayed(false);
    }

    public void showZombieList() {
        setPanel(zombieList);
        this.setGameScreenDisplayed(false);
    }

    public void showPlantSelectScreen(Runnable onContinue) {
        plantSelectScreen.setOnContinue(onContinue);
        setPanel(plantSelectScreen);
        this.setGameScreenDisplayed(false);
    }

    public void showHelpScreen() {
        setPanel(helpScreen);
        this.setGameScreenDisplayed(false);
    }

    public void showGameScreen() {
        clip.stop();
        gameScreen = new GamePanel(); // Initialize GamePanel here after deck is filled
        playBackgroundSound("/resources/sound/Sabilulungan (Trap Remix)  Prod. Marcel NTX - Indonesian Trap Beat.wav");
        this.setGameScreenDisplayed(true);
        setPanel(gameScreen);
    }

    public void showLoseScreen() {
        this.setGameScreenDisplayed(false);
        clip.stop();
        playBackgroundSound("/resources/sound/sad-hamster-made-with-Voicemod.wav");
        setPanel(loseScreen);
    }

    public void showWinScreen() {
        this.setGameScreenDisplayed(false);
        clip.stop();
        playBackgroundSound("/resources/sound/happy-happy-happy-cat.wav");
        setPanel(winScreen);
    }

    private void setPanel(JPanel panel) {
        setContentPane(panel);
        revalidate();
        repaint();
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
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public boolean isGameScreenDisplayed() {
        return isGameScreenDisplayed;
    }

    public void setGameScreenDisplayed(boolean gameScreenDisplayed) {
        isGameScreenDisplayed = gameScreenDisplayed;
    }

    public GamePanel getGameScreen() {
        return gameScreen;
    }

    public static void main(String[] args) {
        new GameGUI();  // Start the game GUI
    }
}
