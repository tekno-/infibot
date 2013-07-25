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
    /**
     * Gets Widget at index.
     * @param id the base index
     * @return widget at index
     */
    public static Widget get(int id) {
        return new Widget(Game.getClient().getWidgets()[id]);
    }

    /**
     * Gets subchildren of a specified Widget. Doing Widgets.get(1, 2, 3, 4) has same behaviour as Widgets.get(1).getChild(2).getChild(3).getChild(4).
     * @param id the id
     * @param ids the sub ids
     * @return null if no ids specified; otherwise sub-widgetchild
     */
    public static WidgetChild get(int id, int... ids) {
        Widget parent = get(id);
        WidgetChild child = null;
        for (int i : ids) {
            child = child != null ? child.getChild(i) : parent.getChild(i);
        }
        return child;
    }
}
