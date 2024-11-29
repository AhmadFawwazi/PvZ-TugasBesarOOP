package entity.plant;

import data.GameState;
import data.TimeKeeper;
import entity.Projectile;
import entity.zombie.Zombie;
import manager.Tile;

public class Repeater extends Plant{
    public Repeater(int row, int col) {
        super("Repeater", 200, 100, 25, 2, 0, 10, row, col);
    }

    public void attack() {
        int currentTime = TimeKeeper.getInstance().getCurrentTime();
        if (currentTime - this.getlastAttackTime() >= this.getAttackSpeed()) {
            for (int col = this.getCol() + 1; col < GameState.getInstance().getGameMap().getCols(); col++) {
                Tile tile = GameState.getInstance().getGameMap().getTile(this.getRow(), col);
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
                    Projectile projectile = new Projectile("ProjectTile1", this.getRow(), this.getCol(), this.getAttackDamage());
                    GameState.getInstance().getGameMap().addProjectile(projectile);

                    this.setlastAttackTime(currentTime); // Update last attack time
                    break;
                }
            }
        }
    }




}