package manager;

import entity.plant.*;
import util.*;
public class Inventory {
    private ListOf<Plant> items;
    private static final int MAX_ITEMS = 10;  // Maximum number of items in the inventory

    public Inventory() {
        this.items = new ListOf<Plant>();
        items.add(PlantFactory.createPlant("Sunflower", 0,0));
        items.add(PlantFactory.createPlant("Peashooter",0,0));
        items.add(PlantFactory.createPlant("Repeater",0,0));
        items.add(PlantFactory.createPlant("SnowPea",0,0));
        items.add(PlantFactory.createPlant("Wallnut",0,0));
        items.add(PlantFactory.createPlant("Pumpkin",0,0));
        items.add(PlantFactory.createPlant("Chomper",0,0));
        items.add(PlantFactory.createPlant("TangleKelp",0,0));
        items.add(PlantFactory.createPlant("Squash",0,0));
        items.add(PlantFactory.createPlant("Lilypad",0,0));
    }

    public void addItem(Plant item) {
        if (items.size() < MAX_ITEMS) {
            items.add(item);
        } else {
            System.out.println("Inventory is full. Cannot add more items.");
        }
    }

    public void removeItem(Plant item) {
        items.remove(item);
    }

    public ListOf<Plant> getAllItems() {
        return items;
    }
}

