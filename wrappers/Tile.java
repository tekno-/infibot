package com.infibot.client.api.wrappers;

import com.infibot.client.api.Locatable;
import com.infibot.client.api.methods.Players;

public class Tile implements Locatable, Comparable<Tile> {

    private final int x, y, plane;

    public Tile(int x, int y) {
        this(x, y, 0);
    }

    public Tile(int x, int y, int plane) {
        this.x = x;
        this.y = y;
        this.plane = plane;
    }

    public double distanceTo(Locatable loc) {
        return distanceTo(loc.getLocation());
    }

    public double distanceTo() {
        return distanceTo(Players.getLocal());
    }

    public double distanceTo(Tile tile) {
        return Math.sqrt(Math.pow(tile.x - x, 2) + Math.pow(tile.y - y, 2));
    }

    public Tile derive(int x, int y) {
        return new Tile(x + x, y + y, plane);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getPlane() {
        return plane;
    }

    @Override
    public Tile getLocation() {
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Tile) {
            Tile tile = (Tile) o;
            return tile.x == x && tile.y == y && tile.plane == plane;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 17;
        hash = 31 * hash + x;
        hash = 31 * hash + y;
        hash = 31 * hash + plane;
        return hash;
    }

    @Override
    public int compareTo(Tile o) {
        return (int) (o.distanceTo() - distanceTo());
    }
}
