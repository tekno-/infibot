package com.infibot.client.api.methods.list;

import com.infibot.client.api.Locatable;

import java.util.Comparator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ian
 * Date: 7/20/13
 * Time: 5:35 PM
 */
public class LocatableSortList<T extends Locatable> extends SortList<T> {
    private final Comparator<T> COMPARATOR = new Comparator<T>() {
        @Override
        public int compare(T o1, T o2) {
            return (int) (o2.getLocation().distanceTo() - o1.getLocation().distanceTo());
        }
    };

    public LocatableSortList(T... locatables) {
        super(false, locatables);
    }

    public LocatableSortList(boolean preSort, T... locatables) {
        super(preSort, locatables);
    }

    public LocatableSortList(List<T> locatables) {
        super(locatables);
    }

    public LocatableSortList(boolean preSort, List<T> locatables) {
        super(preSort, locatables);
    }

    public T getNearest() {
        return get(0);
    }

    public T getNthAway(int index) {
        return get(index);
    }

    public T getFurthest() {
        return get(size() - 1);
    }

    @Override
    protected Comparator<T> getComparator() {
        return COMPARATOR;
    }
}
