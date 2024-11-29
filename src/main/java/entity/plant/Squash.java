package entity.plant;

import data.GameState;
import data.TimeKeeper;
import entity.zombie.Zombie;
import manager.Tile;

public class Squash extends Plant{
    private boolean hasAttacked;

    public Squash(int row, int col) {
        super("Squash", 50, 100, 5000, 0, 1, 20, row, col);
        this.hasAttacked = false;
    }

    public void attack() {
        int currentTime = TimeKeeper.getInstance().getCurrentTime();
        if (currentTime - this.getlastAttackTime() >= this.getAttackSpeed() && !this.hasAttacked) {
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
                this.hasAttacked = true;
                this.setIsAlive(false); // The plant dies after attacking
                GameState.getInstance().getGameMap().getTile(this.getRow(), this.getCol()).removePlant(this);
                this.setlastAttackTime(currentTime); // Update last attack time
            }    
        }
    }

    
}