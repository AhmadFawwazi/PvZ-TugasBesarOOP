package entity.zombie;

import util.Moveable;
import data.TimeKeeper;
import entity.Entity;

public class Zombie extends Entity implements Moveable{
    private boolean is_aquatic;
    private boolean isFaster;
    private boolean isSlowed;
    private int lastMoveTime;
    private double partialMove;
    private int slowedTime;
    private int moveInterval;

    public Zombie(String name, int health, int attackDamage, int attackSpeed, boolean is_aquatic, int row, int col, boolean isAlive, boolean isFaster, boolean isSlowed) {
        super(name, health, attackDamage, attackSpeed, row, col, true);
        this.is_aquatic = is_aquatic;
        this.isFaster = false;
        this.isSlowed = false;
        this.lastMoveTime = TimeKeeper.getInstance().getCurrentTime();
    }

    public boolean getIsAquatic() {
        return is_aquatic;
    }

    public void setIsAquatic(boolean is_aquatic) {
        this.is_aquatic = is_aquatic;
    }

    public boolean getIsFaster() {
        return isFaster;
    }

    public void setIsFaster(boolean isFaster) {
        this.isFaster = isFaster;
    }

    public boolean getIsSlowed() {
        return isSlowed;
    }

    public void setIsSlowed(boolean isSlowed) {
        this.isSlowed = isSlowed;
    }

    public int getLastMoveTime() {
        return lastMoveTime;
    }

    public void setLastMoveTime(int lastMoveTime) {
        this.lastMoveTime = lastMoveTime;
    }

    public double getPartialMove() {
        return partialMove;
    }

    public void setPartialMove(double partialMove) {
        this.partialMove = partialMove;
    }

    public boolean canMove(int currentTime) {
        moveInterval = 10; // 5 seconds
        if (isFaster) {
            moveInterval /= 1.5; // Increase speed by 50%
        } else if (isSlowed) {
            moveInterval *= 1.5; // Decrease speed by 50%
        }
        return (currentTime - lastMoveTime) >= moveInterval;
    }

    public void move() {
        this.setCol(this.getCol() - 1);
        this.lastMoveTime = TimeKeeper.getInstance().getCurrentTime();
    }

    public void die() {
        super.setIsAlive(false);
        System.out.println(this.getName() + " died!");
    }
    
    public void setSlowedTime(int slowedTime) {
        this.slowedTime = slowedTime;
    }
    
    public boolean isSlowed() {
        int currentTime = TimeKeeper.getInstance().getCurrentTime();
        // Check if the slow effect should still be active
        if (isSlowed && (currentTime - slowedTime) > 3) {
            this.isSlowed = false; // Reset the slowed status after 3 seconds
        }
        return isSlowed;
    }

    public int getSlowedTime() {
        return slowedTime;
    }

    public int getmoveInterval() {
        return moveInterval;
    }

    public void attack() {}
}
    
    
//     @Override
//     public void attackPlants() {
//         int currentTime = TimeKeeper.getInstance().getCurrentTime();
//         if (currentTime - this.getlastAttackTime() >= this.getAttackSpeed()) {
//             for (int col = this.getCol(); col >= 0; col--) {
//                 Tile tile = GameState.getInstance().getGameMap().getTile(this.getRow(), col);
//                 if (!tile.getPlants().isEmpty()) {
//                     // Directly attack the first plant in the tile
//                     Plant targetPlant = tile.getPlants().get(0);
//                     targetPlant.takeDamage(this.getAttackDamage());
//                     if (!targetPlant.getIsAlive()) {
//                         tile.removePlant(targetPlant);
//                     }
//                     this.setlastAttackTime(currentTime); // Update last attack time
//                     break;
//                 }
//             }
//         }
//     }
// }

