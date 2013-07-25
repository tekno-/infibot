package com.infibot.client.api.methods;

import com.infibot.client.api.Game;
import com.infibot.client.api.util.filter.Filter;
import com.infibot.client.api.wrappers.Player;

import java.util.ArrayList;
import java.util.List;

public class Players extends Locatables<Player> {
    private static final Players instance = new Players();

    /**
     *
     * @return nearest Npc instance available
     */
    public static Player getNearest() {
        return instance.nearest(instance.ALL_FILTER);
    }

    /**
     *
     * @param filter the specified filter
     * @return nearest Npc instance that matches the filter
     */
    public static Player getNearest(Filter<Player> filter) {
        return instance.nearest(filter);
    }

    /**
     *
     * @return all loaded npcs
     */
    public static List<Player> getLoaded() {
        return instance.loaded(instance.ALL_FILTER);
    }

    /**
     * Returns all loaded Npcs that match the filter.
     * @param filter the filter
     * @return the loaded npcs accepting the filter
     */
    public static List<Player> getLoaded(Filter<Player> filter) {
        return instance.loaded(filter);
    }

    /**
     *
     * @return the local player instance
     */
    public static Player getLocal() {
        return new Player(Game.getClient().getLocalPlayer());
    }

    @Override
    protected List<Player> loaded(Filter<Player> filter) {
        List<Player> list = new ArrayList<>();
        for (com.infibot.client.accessors.Player p : Game.getClient().getPlayers()) {
            if (p != null) {
                Player pW = new Player(p);
                if (filter.accept(pW))
                    list.add(pW);
            }
        }
        return list;
    }

}
