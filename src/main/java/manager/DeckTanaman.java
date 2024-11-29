package manager;

import entity.plant.*;
import util.*;

public class DeckTanaman {
    private ListOf<Plant> plants;
    private static final int MAX_SIZE = 6;  // Maximum number of plants in the deck

    public DeckTanaman() {
        this.plants = new ListOf<Plant>();
    }

    public boolean addPlant(Plant plant) {
        if (plants.size() < MAX_SIZE) {
            plants.add(plant);
            return true;
        } else {
            System.out.println("Deck is full. Cannot add more plants.");
        }
        return false;
    }

    public void removePlant(Plant plant) {
        plants.remove(plant);
    }

    public ListOf<Plant> getPlants() {
        return plants;
    }

    public boolean containsPlant(Plant plant) {
        return plants.contains(plant);
    }

    public boolean isEmpty() {
        return plants.isEmpty();
    }

    public int size() {
        return plants.size();
    }

    // public void clear() {
    //     for (int i = 0; i < plants.size(); i++) {
    //         plants.remove(i);
    //     }
    // }

    public boolean isFull() {
        return plants.size() == MAX_SIZE;
    }
}

