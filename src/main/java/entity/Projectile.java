package entity;

public class Projectile {
    private int row;
    private int col;
    private int damage;
    private String type;

    public Projectile(String type, int row, int col, int damage) {
        this.type = type;
        this.row = row;
        this.col = col;
        this.damage = damage;
    }

    public String getType() {
        return type;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getDamage() {
        return damage;
    }

    public void move() {
        this.col += 1; // Example movement: move right
    }
}

