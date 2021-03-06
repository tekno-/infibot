package com.infibot.client.api.methods;

import com.infibot.client.api.Game;
import com.infibot.client.api.util.filter.Filter;
import com.infibot.client.api.wrappers.GameObject;
import com.infibot.client.api.wrappers.Tile;

import java.util.ArrayList;
import java.util.List;

public class GameObjects extends Locatables<GameObject> {

    private static final GameObjects instance = new GameObjects();
    public static enum ObjectType {
        WALL_DECORATION, FLOOR_DECORATION, INTERACTABLE
    }

    @Override
    protected List<GameObject> loaded(Filter<GameObject> filter) {
        List<GameObject> list = new ArrayList<>();
        for(int x = 0; x < 104; x++) {
            for(int y = 0; y < 104; y++) {
                GameObject go = getInRegion(x, y);
                if(go != null && filter.accept(go)) {
                    list.add(go);
                }
            }
        }
        return list;
    }

    private GameObject getInRegion(int x, int y) {
        com.infibot.client.accessors.Tile[] tiles = Game.getClient().getRegion().getTiles();
        for(com.infibot.client.accessors.Tile t : tiles) {
            if(t != null && t.getX() == x && t.getY() == y) {
                for(com.infibot.client.accessors.GameObject go : t.getGameObjects()) {
                    if(go != null && ((go.getId() >> 29) & 0x3) == 2) {
                        return new GameObject(go, ObjectType.INTERACTABLE);
                    }
                }
                if(t.getFloorDecoration() != null) {
                    return new GameObject(t.getFloorDecoration(), ObjectType.FLOOR_DECORATION);
                }
                if(t.getWallDecoration() != null) {
                    return new GameObject(t.getWallDecoration(), ObjectType.WALL_DECORATION);
                }
            }
        }
        return null;
    }

    /**
     *
     * @param tile the location for the GameObject
     * @return the GameObject at the tile specified
     */
    public static GameObject getAt(Tile tile) {
        return instance.getInRegion(tile.getX() >> 7, tile.getY() >> 7);
    }

    /**
     *
     * @return the closest GameObject instance available
     */
    public static GameObject getNearest() {
        return instance.nearest(instance.ALL_FILTER);
    }

    /**
     * Returns nearest object that matches filter.
     * @param filter the specified filter
     * @return nearest object that matches filter
     */
    public static GameObject getNearest(Filter<GameObject> filter) {
        return instance.nearest(filter);
    }

    /**
     * Returns nearest object that matches any of the IDs.
     * @param ids the specified ids
     * @return nearest object that mathces any of the ids
     */
    public static GameObject getNearest(int... ids) {
        return instance.nearest(ids);
    }

    /**
     *
     * @return a list of all loaded gameobjects in a 104x104 region
     */
    public static List<GameObject> getLoaded() {
        return instance.loaded(instance.ALL_FILTER);
    }

    /**
     * Returns all GameObjects that match filter.
     * @param filter specified filter
     * @return a list of all loaded gameobjects in a 104x104 region and that match the filter
     */
    public static List<GameObject> getLoaded(Filter<GameObject> filter) {
        return instance.loaded(filter);
    }

    /**
     * Returns all GameObjects that match any of the IDs.
     * @param ids ids to look for
     * @return a list of the found gameobjects matching ids
     */
    public static List<GameObject> getLoaded(int... ids) {
        return instance.loaded(ids);
    }


}
