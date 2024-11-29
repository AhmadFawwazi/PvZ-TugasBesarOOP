package entity.zombie;

import data.GameState;
import data.TimeKeeper;
import entity.plant.Plant;
import manager.Tile;
import util.ListOf;


public class PoleVaultingZombie extends Zombie {
    private boolean canJump = true;

    public PoleVaultingZombie(int row, int col) {
        super("PoleVaultingZombie", 175, 100, 1, false, row, col, true, true, false);
    }

    @Override
    public void attack() {
        int currentTime = TimeKeeper.getInstance().getCurrentTime();
        if (currentTime - this.getlastAttackTime() >= this.getAttackSpeed()) {
            Tile currentTile = GameState.getInstance().getGameMap().getTile(this.getRow(), this.getCol());
            Tile nextTile = this.getCol() - 1 >= 0 ? GameState.getInstance().getGameMap().getTile(this.getRow(), this.getCol() - 1) : null;

            if (nextTile != null && !nextTile.getPlants().isEmpty()) {
                if (canJump) {
                    // Remove up to two plants and jump over them
                    ListOf<Plant> plants = nextTile.getPlants();
                    if (plants.size() > 0) {
                        nextTile.removePlant(plants.get(0));
                    }
                    if (plants.size() > 0) {
                        nextTile.removePlant(plants.get(0)); // After removing the first plant, the second plant (if exists) is now at index 0
                    }
                    this.setCol(this.getCol() - 1); // Move to the next tile
                    currentTile.removeZombie(this);
                    nextTile.addZombie(this);
                    this.setIsFaster(false); // Increase speed
                    canJump = false; // Can only jump once
                    System.out.println("PoleVaultingZombie jumped over the plants");
                } else {
                    // Regular attack
                    Plant targetPlant = nextTile.getPlants().get(0);
                    targetPlant.takeDamage(this.getAttackDamage());
                    if (!targetPlant.getIsAlive()) {
                        nextTile.removePlant(targetPlant);
                    }
                }
            } else if (!currentTile.getPlants().isEmpty()) {
                // Attack the plant in the current tile
                Plant targetPlant = currentTile.getPlants().get(0);
                targetPlant.takeDamage(this.getAttackDamage());
                if (!targetPlant.getIsAlive()) {
                    currentTile.removePlant(targetPlant);
                }
            }
            this.setlastAttackTime(currentTime); // Update last attack time
        }
    }
}
