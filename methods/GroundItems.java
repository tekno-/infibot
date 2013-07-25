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

    public static GroundItem getNearest() {
        return instance.nearest(instance.ALL_FILTER);
    }

    public static GroundItem getNearest(Filter<GroundItem> groundItemFilter) {
        return instance.nearest(groundItemFilter);
    }

    public static GroundItem getNearest(int... ids) {
        return instance.nearest(ids);
    }

    public static List<GroundItem> getLoaded() {
        return instance.loaded(instance.ALL_FILTER);
    }

    public static List<GroundItem> getLoaded(Filter<GroundItem> filter) {
        return instance.loaded(filter);
    }

    public static List<GroundItem> getLoaded(int... ids) {
        return instance.loaded(ids);
    }

}
