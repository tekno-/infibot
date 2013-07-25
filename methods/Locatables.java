package com.infibot.client.api.methods;

import com.infibot.client.api.Identifiable;
import com.infibot.client.api.Locatable;
import com.infibot.client.api.util.filter.Filter;

import java.util.List;

public abstract class Locatables<T extends Locatable & Identifiable> {
    protected final Filter<T> ALL_FILTER = new Filter<T>() {
        public boolean accept(T t) {
            return true;
        }
    };

    protected abstract List<T> loaded(Filter<T> filter);

    protected final List<T> loaded(final int... ids) {
        return loaded(ids(ids));
    }

    protected final T nearest(Filter<T> filter) {
        T closest = null;
        double dist = Double.MAX_VALUE;
        for (T t : loaded(filter)) {
            double _dist = t.getLocation().distanceTo();
            if (_dist < dist) {
                closest = t;
                dist = _dist;
            }
        }
        return closest;
    }

    protected final T nearest(final int... ids) {
        return nearest(ids(ids));
    }

    private Filter<T> ids(final int... ids) {
        return new Filter<T>() {
            @Override
            public boolean accept(T t) {
                for (int id : ids) {
                    if (t.getId() == id) {
                        return true;
                    }
                }
                return false;
            }
        };
    }
}
