package com.infibot.client.api.path;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created with IntelliJ IDEA.
 * User: Ian
 * Date: 6/2/13
 * Time: 6:28 PM
 */
public class Path<T> extends ArrayList<T> {

    public Path(T... objects) {
        super();
        for (T t : objects)
            add(t);
    }

    public Path reverse() {
        Path path = this;
        Collections.reverse(path);
        return path;
    }
}
