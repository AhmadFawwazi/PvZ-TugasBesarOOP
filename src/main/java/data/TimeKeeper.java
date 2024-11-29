package data;

public class TimeKeeper {
    private int currentTime;
    public static final int DAY_LENGTH = 200; // Total seconds in a day
    public static final int NIGHT_START = 100; // Night starts at 100 seconds
    public static final int ZOMBIE_SPAWN_START = 20; // Zombies start spawning at 20 seconds
    public static final int ZOMBIE_SPAWN_END = 160; // Zombies stop spawning at 160 seconds

    // Singleton instance
    private static TimeKeeper instance;

    // Private constructor to prevent instantiation
    private TimeKeeper() {
        this.currentTime = 0; // Start time at the beginning of the day
    }

    // Public method to provide access to the singleton instance
    public static synchronized TimeKeeper getInstance() {
        if (instance == null) {
            instance = new TimeKeeper();
        }
        return instance;
    }

    // Method to update the current time
    public void update() {
        currentTime = (currentTime + 1); // Increment time and loop back every day
    }

    // Method to check if it is daytime
    public boolean isDaytime() {
        return currentTime < NIGHT_START;
    }

    // Method to check if it is zombie spawn time
    public boolean isZombieSpawnTime() {
        return currentTime >= ZOMBIE_SPAWN_START && currentTime <= ZOMBIE_SPAWN_END;
    }

    // Getter for currentTime
    public int getCurrentTime() {
        return currentTime;
    }

    // Setter for currentTime
    public void setCurrentTime(int currentTime) {
        if (currentTime >= 0 && currentTime < DAY_LENGTH) {
            this.currentTime = currentTime;
        } else {
            throw new IllegalArgumentException("Current time must be between 0 and " + (DAY_LENGTH - 1));
        }
    }
}
