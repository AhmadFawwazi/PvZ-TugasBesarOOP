package entity.plant;

public class PlantFactory {
    public static Plant createPlant(String type, int row, int col) {
        switch (type) {
            case "Chomper":
                return new Chomper(row, col);
            case "Lilypad":
                return new Lilypad(row, col);
            case "Peashooter":
                return new Peashooter(row, col);
            case "Pumpkin":
                return new Pumpkin(row, col);
            case "Repeater":
                return new Repeater(row, col);
            case "SnowPea":
                return new SnowPea(row, col);
            case "Squash":
                return new Squash(row, col);
            case "Sunflower":
                return new Sunflower(row, col);
            case "TangleKelp":
                return new TangleKelp(row, col);
            case "Wallnut":
                return new Wallnut(row, col);
            default:
                throw new IllegalArgumentException("Unknown plant type: " + type);
        }
    }
}