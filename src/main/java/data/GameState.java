package data;

import manager.*;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import entity.*;
import gui.GameGUI;

public class GameState {
    private GameMap gameMap;
    private DeckTanaman deck;
    private Inventory inventory;
    private Sun sunPoints;
    private Map<String, Integer> plantCooldowns;
    private Clip clip;

    private static GameState instance;

    public GameState() {
        this.gameMap = new GameMap();
        this.deck = new DeckTanaman();
        this.inventory = new Inventory();
        this.sunPoints = new Sun();
        this.plantCooldowns = new HashMap<>();
    }

    public static synchronized GameState getInstance() {
        if (instance == null) {
            instance = new GameState();
        }
        return instance;
    }

    public void update() {
        TimeKeeper.getInstance().update();
        System.out.println("Current Time: " + TimeKeeper.getInstance().getCurrentTime());
        spawnZombie();
        processPlantActions();
        processZombieActions();
        processProjectiles();
        // updateGameMap();
        checkGameOver();
    }
    
    private void spawnZombie() {
        if (TimeKeeper.getInstance().isZombieSpawnTime()){
            gameMap.zombieSpawner();
        }
    }

    private void processZombieActions() {
        // Zombies move and attack 
        gameMap.moveZombies();
    }

    private void processPlantActions() {
        // Plants perform actions 
        gameMap.plantAttack();
    }

    private void processProjectiles() {
        // Projectiles move and attack
        gameMap.moveProjectiles();
    }

    private void checkGameOver() {
        // Check conditions that would end the game
        if (gameMap.checkForGameOverConditions()) {
            if (gameMap.WinCondition()){
                GameGUI.getInstance().showWinScreen();
                stopGame();
            } else {
                GameGUI.getInstance().showLoseScreen();
                stopGame();
            }
        }
    }

    // Metode untuk menyimpan game
    public void saveGame(String filePath) {
        try (FileOutputStream fileOut = new FileOutputStream(filePath);
            ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(this);
            System.out.println("Permainan Berhasil di Simpan.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void stopGame() {
        GameState.getInstance().reset();
        GameEngine.getInstance().stop();
        System.out.println("Game Selesai!");
    }

    public GameMap getGameMap() {
        return gameMap;
    }

    public DeckTanaman getDeck() {
        return deck;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public int getSunPoints() {
        return sunPoints.getTotalSun();
    }

    public void addSunPoints(int amount) {
        playBackgroundSound("/resources/sound/plants-vs-zombies-sun-pickup.wav");
        sunPoints.addSun(amount);
    }

    public void setSunPoints(int amount) {
        sunPoints.setTotalSun(amount);
    }

    public Map<String, Integer> getPlantCooldowns() {
        return plantCooldowns;
    }

    public void reset(){
        instance = null;
        instance = new GameState();
        TimeKeeper.getInstance().setCurrentTime(0);
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
}
