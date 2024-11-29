package manager;

import entity.Projectile;
import entity.plant.Plant;
import entity.zombie.Zombie;
import util.ListOf;

public class Tile {
    private ListOf<Plant> plants;
    private ListOf<Zombie> zombies;
    private ListOf<Projectile> projectiles;

    public Tile() {
        this.plants = new ListOf<Plant>();
        this.zombies = new ListOf<Zombie>();
        this.projectiles = new ListOf<Projectile>();
    }

    public ListOf<Plant> getPlants() {
        return plants;
    }

    public void addPlant(Plant plant) {
        plants.add(plant);
    }

    public ListOf<Zombie> getZombies() {
        return zombies;
    }

    public void addZombie(Zombie zombie) {
        zombies.add(zombie);
    }

    public void removePlant(Plant plant) {
        plants.remove(plant);
    }

    public void removeZombie(Zombie zombie) {
        zombies.remove(zombie);
    }

    public void addProjectile(Projectile projectile) {
        projectiles.add(projectile);
    }

    public ListOf<Projectile> getProjectiles() {
        return projectiles;
    }

    public void removeProjectile(Projectile projectile) {
        projectiles.remove(projectile);
    }

    public boolean containsPlant(String plant) {
        for (Plant p : plants.getAll()) {
            if (p.getName().equals(plant)) {
                return true;
            }
        }
        return false;
    }
}
