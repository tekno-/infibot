package com.infibot.client.api.methods;

import com.infibot.client.api.Game;
import com.infibot.client.api.util.filter.Filter;
import com.infibot.client.api.wrappers.Npc;

import java.util.ArrayList;
import java.util.List;

public class Npcs extends Locatables<Npc> {
    private static final Npcs npcs = new Npcs();
    @Override
    protected List<Npc> loaded(Filter<Npc> filter) {
        List<Npc> npcs = new ArrayList<>();
        for(com.infibot.client.accessors.Npc npc : Game.getClient().getNpcs()) {
            Npc npcW = new Npc(npc);
            if(npc != null && filter.accept(npcW)) {
                npcs.add(npcW);
            }
        }
        return npcs;
    }

    /**
     *
     * @return nearest Npc instance available
     */
    public static Npc getNearest() {
        return npcs.nearest(npcs.ALL_FILTER);
    }

    /**
     *
     * @param npcFilter the specified filter
     * @return nearest Npc instance that matches the filter
     */
    public static Npc getNearest(Filter<Npc> npcFilter) {
        return npcs.nearest(npcFilter);
    }

    /**
     * Returns nearest Npc that matches any of the IDs.
     * @param ids the ids to look for
     * @return the nearest Npc with matching ID
     */
    public static Npc getNearest(int... ids) {
        return npcs.nearest(ids);
    }

    /**
     *
     * @return all loaded npcs
     */
    public static List<Npc> getLoaded() {
        return npcs.loaded(npcs.ALL_FILTER);
    }

    /**
     * Returns all loaded Npcs that match the filter.
     * @param filter the filter
     * @return the loaded npcs accepting the filter
     */
    public static List<Npc> getLoaded(Filter<Npc> filter) {
        return npcs.loaded(filter);
    }

    /**
     *
     * @param ids the ids to look for
     * @return the list of loaded npcs that match any of the ids
     */
    public static List<Npc> getLoaded(int... ids) {
        return npcs.loaded(ids);
    }
}
