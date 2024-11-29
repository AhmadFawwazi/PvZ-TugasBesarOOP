package entity.zombie;

public class ZombieFactory {
    public static Zombie createZombie(String type, int row, int col) {
        switch (type) {
            case "Normal":
                return new NormalZombie(row, col);
            case "ConeHead":
                return new ConeHeadZombie(row, col);
            case "Pole Vaulting":
                return new PoleVaultingZombie(row, col);
            case "Bucket Head":
                return new BucketHeadZombie(row, col);
            case "Ducky Tube":
                return new DuckyTubeZombie(row, col);
            case "Dolphin Rider":
                return new DolphinRiderZombie(row, col);
            case "Door":
                return new DoorZombie(row, col);
            case "Football":
                return new FootballZombie(row, col);
            case "Flag":
                return new FlagZombie(row, col);
            case "Newspaper":
                return new NewspaperZombie(row, col);
            default:
                throw new IllegalArgumentException("Unknown zombie type: " + type);
        }
    }
}
