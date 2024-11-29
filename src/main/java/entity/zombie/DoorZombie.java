package entity.zombie;


import data.GameState;
import data.TimeKeeper;
import entity.plant.Plant;
import manager.Tile;

public class DoorZombie extends Zombie{

    public DoorZombie(int row, int col) {
        super("DoorZombie", 300, 100, 1, false, row, col, true, false, false);
    }

    @Override
    public void attack() {
        int currentTime = TimeKeeper.getInstance().getCurrentTime();
        if (currentTime - this.getlastAttackTime() >= this.getAttackSpeed()) {
            Tile tile = GameState.getInstance().getGameMap().getTile(this.getRow(), this.getCol() - 1);
            if (!tile.getPlants().isEmpty()) {
                // Directly attack the first plant in the tile
                Plant targetPlant = tile.getPlants().get(0);
                targetPlant.takeDamage(this.getAttackDamage());
                if (!targetPlant.getIsAlive()) {
                    tile.removePlant(targetPlant);
                }
                this.setlastAttackTime(currentTime); // Update last attack time
            }
        }
    }
}