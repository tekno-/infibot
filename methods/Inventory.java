package com.infibot.client.api.methods;

import com.infibot.client.api.Game;
import com.infibot.client.api.wrappers.Item;
import com.infibot.client.api.wrappers.WidgetChild;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ian
 * Date: 7/17/13
 * Time: 2:22 AM
 */
public class Inventory {
    public static boolean isItemSelected() {
        return Game.getClient().isItemSelected();
    }

    public static Item[] getItems() {
        final WidgetChild wc = Widgets.get(149, 0);
        List<Item> itemList = new ArrayList<>(28);
        for (int i = 0; i < 27; i++) {
            if (wc.getSlotContentIds()[i] != 0)
                itemList.add(new Item(wc, i));
        }
        return itemList.toArray(new Item[itemList.size()]);
    }

    public static Item getItem(int... id) {
        for (Item item : getItems()) {
            for (int i : id) {
                if (i == item.getId())
                    return item;
            }
        }
        return null;
    }

    public static int getCount() {
        return getItems().length;
    }

    public static int getCount(boolean countStacks, int... ids) {
        int count = 0;
        for (Item i : getItems()) {
            for (int id : ids) {
                if (i.getId() == id) {
                    count += countStacks ? i.getStackSize() : 1;
                }
            }
        }
        return count;
    }

    public boolean contains(int... ids) {
        return getCount(false, ids) > 0;
    }

    public boolean isFull() {
        return getCount() == 28;
    }
}
