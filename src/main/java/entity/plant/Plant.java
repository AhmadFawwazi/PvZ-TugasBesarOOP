package entity.plant;

import entity.Entity;

public class Plant extends Entity{
    private int cost;
    private int range;
    private int cooldown;

    public Plant(String name, int cost, int health, int attackDamage, int attackSpeed, int range, int cooldown, int row, int col) {
        super(name,health,attackDamage,attackSpeed, row, col, true);
        this.cost = cost;
        this.range = range;
        this.cooldown = cooldown;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost){
        this.cost = cost;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range){
        this.range = range;
    }

    public int getCooldown() {
        return cooldown;
    }

    public void setCooldown(int cooldown){
        this.cooldown = cooldown;
    }

    public void die() {
        super.setIsAlive(false);
        System.out.println(this.getName() + " died!");
    }

    @Override
    public void attack(){}
}


