package com.infibot.client.api.methods;


import com.infibot.client.api.Game;
import com.infibot.client.api.wrappers.Widget;
import com.infibot.client.api.wrappers.WidgetChild;

/**
 * Created with IntelliJ IDEA.
 * User: Ian
 * Date: 7/17/13
 * Time: 2:39 AM
 */
public class Widgets {
    public static Widget get(int id) {
        return new Widget(Game.getClient().getWidgets()[id]);
    }

    public static WidgetChild get(int id, int... ids) {
        Widget parent = get(id);
        WidgetChild child = null;
        for (int i : ids) {
            child = child != null ? child.getChild(i) : parent.getChild(i);
        }
        return child;
    }
}
