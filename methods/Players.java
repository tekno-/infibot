package com.infibot.client.api.methods;

import com.infibot.client.api.Game;
import com.infibot.client.api.util.filter.Filter;
import com.infibot.client.api.wrappers.Player;

import java.util.ArrayList;
import java.util.List;

public class Players extends Locatables<Player> {
    private static final Players instance = new Players();

    public static Player getNearest() {
        return instance.nearest(instance.ALL_FILTER);
    }

    public static Player getNearest(Filter<Player> filter) {
        return instance.nearest(filter);
    }

    public static Player getNearest(int... ids) {
        return instance.nearest(ids);
    }

    public static List<Player> getLoaded() {
        return instance.loaded(instance.ALL_FILTER);
    }

    public static List<Player> getLoaded(Filter<Player> filter) {
        return instance.loaded(filter);
    }

    public static List<Player> getLoaded(int... ids) {
        return instance.loaded(ids);
    }

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
