package entity;

import util.*;

public class Entity implements Attackable{
    private String name;
    private int health;
    private int attackDamage;
    private int attackSpeed;
    private int row;
    private int col;
    private boolean isAlive;
    private int lastAttackTime;

    public Entity(String name, int health, int attackDamage, int attackSpeed, int row, int col, boolean isAlive)
    {
        this.name = name;
        this.health = health;
        this.attackDamage = attackDamage;
        this.attackSpeed = attackSpeed;
        this.row = row;
        this.col = col;
        this.isAlive = isAlive;
        this.lastAttackTime = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health){
        this.health = health;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public void setAttackDamage(int attackDamage){
        this.attackDamage = attackDamage;
    }

    public int getAttackSpeed() {
        return attackSpeed;
    }

    public void setAttackSpeed(int attackSpeed){
        this.attackSpeed = attackSpeed;
    }

    public boolean getIsAlive() {
        return isAlive;
    }

    public void setIsAlive(boolean isAlive){
        this.isAlive = isAlive;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row){
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col){
        this.col = col;
    }

    public void takeDamage(int damage){
        health -= damage;
        if(health <= 0){
            isAlive = false;
        }
    }

    public int getlastAttackTime(){
        return lastAttackTime;
    }

    public int setlastAttackTime(int lastAttackTime){
        return this.lastAttackTime = lastAttackTime;
    }

    public void attack(){}
}
