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
    /**
     *
     * @return true if any item is selected; otherwise false
     */
    public static boolean isItemSelected() {
        return Game.getClient().isItemSelected();
    }

    /**
     *
     * @return all items currently in the inventory
     */
    public static Item[] getItems() {
        final WidgetChild wc = Widgets.get(149, 0);
        List<Item> itemList = new ArrayList<>(28);
        for (int i = 0; i < 27; i++) {
            if (wc.getSlotContentIds()[i] != 0)
                itemList.add(new Item(wc, i));
        }
        return itemList.toArray(new Item[itemList.size()]);
    }

    /**
     * @param id the ids specified
     * @return the first item that matches any of the ids
     */
    public static Item getItem(int... id) {
        for (Item item : getItems()) {
            for (int i : id) {
                if (i == item.getId())
                    return item;
            }
        }
        return null;
    }

    /**
     *
     * @return total inventory count; not including stacks
     */
    public static int getCount() {
        return getItems().length;
    }

    /**
     * Calculates the total item count for a range of ids.
     * @param countStacks boolean indicating whether to count in stacks or not
     * @param ids the ids to look for
     * @return total count of all ids
     */
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

    /**
     *
     * @param ids the ids to look for
     * @return true if inventory contains any of the ids; otherwise false
     */
    public static boolean contains(int... ids) {
        return getCount(false, ids) > 0;
    }

    /**
     *
     * @return true if the inventory is full; otherwise false
     */
    public static boolean isFull() {
        return getCount() == 28;
    }
}
