package entity.plant;

import data.GameState;
import data.TimeKeeper;

public class Sunflower extends Plant{
    public Sunflower(int row, int col) {
        super("Sunflower", 50, 100, 0, 0, 0, 10, row, col);
    }

    public void attack() {
        int currentTime = TimeKeeper.getInstance().getCurrentTime();
        if (currentTime - this.getlastAttackTime() >= 3) {
            GameState.getInstance().addSunPoints(25);
            this.setlastAttackTime(currentTime); // Update last attack time
        }
    }

}