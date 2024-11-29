package entity.plant;

import data.GameState;
import data.TimeKeeper;
import entity.zombie.Zombie;
import manager.Tile;

public class Chomper extends Plant{
    public Chomper(int row, int col) {
        super("Chomper", 150, 100, 5000, 10, 1, 20, row, col);
    }

    public void attack() {
        int currentTime = TimeKeeper.getInstance().getCurrentTime();
        if (currentTime - this.getlastAttackTime() >= this.getAttackSpeed()) {
            Tile tile = GameState.getInstance().getGameMap().getTile(this.getRow(), this.getCol() + 1);
            if (!tile.getZombies().isEmpty()) {
                // Directly attack the zombie in the tile
                for (int i = 0; i < tile.getZombies().size(); i++) {
                    Zombie targetZombie = tile.getZombies().get(i);
                    targetZombie.takeDamage(this.getAttackDamage());
                    System.out.println(this.getAttackDamage() + " attack " + this.getName());
                    if (!targetZombie.getIsAlive()) {
                        tile.removeZombie(targetZombie);
                        i--;
                    }
                }
                // Add projectile for visualization
                this.setlastAttackTime(currentTime); // Update last attack time
            }    
        }
    }
    







}