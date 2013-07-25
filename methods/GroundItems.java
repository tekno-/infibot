package com.infibot.client.api.methods;

import com.infibot.client.accessors.Item;
import com.infibot.client.accessors.LinkedList;
import com.infibot.client.api.Game;
import com.infibot.client.api.util.filter.Filter;
import com.infibot.client.api.wrappers.GroundItem;

import java.util.ArrayList;
import java.util.List;

public class GroundItems extends Locatables<GroundItem> {

    private static final GroundItems instance = new GroundItems();
    
    @Override
    protected List<GroundItem> loaded(Filter<GroundItem> filter) {
        List<GroundItem> list = new ArrayList<>();
        LinkedList[][] linkedLists = Game.getClient().getGroundItems()[Game.getClient().getPlane()];
            for(int x = 0; x < linkedLists.length; x++) {
                for(int y = 0; y < linkedLists[y].length; y++) {
                    LinkedList l = linkedLists[x][y];
                    if(l != null && l.getHead() != null && l.getHead() instanceof Item) {
                        GroundItem gi = new GroundItem((Item) l.getHead(), x, y);
                        if(filter.accept(gi)) {
                            list.add(gi);
                        }
                    }
                }
            }

        return list;
    }

    /**
     *
     * @return the closest GameObject instance available
     */
    public static GroundItem getNearest() {
        return instance.nearest(instance.ALL_FILTER);
    }

    /**
     * Returns nearest grounditem that matches the filter
     * @param groundItemFilter the filter specified
     * @return nearest grounditem instance
     */
    public static GroundItem getNearest(Filter<GroundItem> groundItemFilter) {
        return instance.nearest(groundItemFilter);
    }

    /**
     * Returns nearest grounditem that matches any of the IDs
     * @param ids the ids specified
     * @return nearest grounditem instance
     */
    public static GroundItem getNearest(int... ids) {
        return instance.nearest(ids);
    }

    /**
     * @return all loaded grounditems
     */
    public static List<GroundItem> getLoaded() {
        return instance.loaded(instance.ALL_FILTER);
    }

    /**
     * Returns loaded grounditems that matches the filter
     * @param filter the filter specified
     * @return loaded grounditems
     */
    public static List<GroundItem> getLoaded(Filter<GroundItem> filter) {
        return instance.loaded(filter);
    }

    /**
     * Returns all loaded grounditem that matches any of the IDs
     * @param ids the ids specified
     * @return all loaded grounditem instance
     */
    public static List<GroundItem> getLoaded(int... ids) {
        return instance.loaded(ids);
    }

}
