package com.infibot.client.api.methods.list;

import com.infibot.client.api.Locatable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ian
 * Date: 7/20/13
 * Time: 5:20 PM
 */
public abstract class SortList<T extends Locatable> extends ArrayList<T> {
    private int lastModCount;

    public SortList(T... locatables) {
        this(false, locatables);
    }

    public SortList(List<T> locatables) {
        this(false, locatables);
    }

    public SortList(boolean preSort, List<T> locatables) {
        super();
        for (T t : locatables)
            add(t);
        if (preSort)
            sort();
    }

    public SortList(boolean preSort, T... locatables) {
        super();
        for (T t : locatables)
            add(t);
        if (preSort)
            sort();
    }

    public boolean isSorted() {
        return modCount == lastModCount;
    }

    protected abstract Comparator<T> getComparator();

    public SortList<T> sort() {
        return sort(getComparator());
    }

    public SortList<T> sort(Comparator<T> comparator) {
        Collections.sort(this, comparator);
        lastModCount = modCount;
        return this;
    }

}
