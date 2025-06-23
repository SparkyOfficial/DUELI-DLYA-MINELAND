package net.mineland.duels.arena;

import org.bukkit.Location;
import org.bukkit.World;

public class Arena {
    private final String name;
    private final World world;
    private final Location spawn1;
    private final Location spawn2;
    private boolean occupied;

    public Arena(String name, World world, Location spawn1, Location spawn2) {
        this.name = name;
        this.world = world;
        this.spawn1 = spawn1;
        this.spawn2 = spawn2;
        this.occupied = false;
    }

    public String getName() { return name; }
    public World getWorld() { return world; }
    public Location getSpawn1() { return spawn1; }
    public Location getSpawn2() { return spawn2; }
    public boolean isOccupied() { return occupied; }
    public void setOccupied(boolean occupied) { this.occupied = occupied; }

    public static Arena withWorld(Arena template, World newWorld) {
        Location s1 = template.getSpawn1().clone();
        Location s2 = template.getSpawn2().clone();
        s1.setWorld(newWorld);
        s2.setWorld(newWorld);
        return new Arena(template.getName(), newWorld, s1, s2);
    }
} 