package com.infibot.client.api.methods;

import com.infibot.client.api.ActionState;
import com.infibot.client.api.Locatable;
import com.infibot.client.api.util.Random;
import com.infibot.client.api.util.Task;
import com.infibot.client.api.util.Timer;
import com.infibot.client.api.wrappers.*;
import com.infibot.client.utilities.time.Time;

import java.awt.*;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Bank {
    private static final int PARENT = 12;
    private static final int VISUAL_ITEMS_WIDGET = 91;
    private static final int NOTE_ITEM = 92;
    private static final int UNNOTE_ITEM = 93;
    private static final int INSERT_MODE = 99;
    private static final int SWAP_MODE = 98;
    private static final int CLOSE = 104;
    private static final int ITEMS = 89;
    private static final Rectangle SCROLL_UP = new Rectangle(468, 59, 15, 15);
    private static final Rectangle SCROLL_AREA = new Rectangle(468, 96, 15, 175);
    private static final Rectangle SCROLL_DOWN = new Rectangle(468, 272, 15, 15);

    public static WidgetChild getVisibleBank() {
        return Widgets.get(PARENT, VISUAL_ITEMS_WIDGET);
    }

    public static int getScrollingTranslation() {
        return (int) Math.round(getPercentageScrolled() * 17.5);
    }

    public static double getPercentageScrolled() {
        if (!isOpen())
            return -1;
        int position = getScrollbarPosition();
        return position != -1 ? 100.0 - ((double) position / getBarHeight()) * 100.0 : -1;
    }

    private static int getBarHeight() {
        return ((int) SCROLL_AREA.getMaxY() - 1) - (int) SCROLL_AREA.getMinY();
    }

    private static int getScrollbarPosition() {
        if (!isOpen())
            return -1;
        int max = (int) SCROLL_AREA.getMaxY();
        for (int i = 0; i < SCROLL_AREA.getHeight(); i++) {
            //TODO:Find some way to locate bar.
            //if (Screen.getColorAt(470, max - i).equals(SCROLLBAR_BOTTOM))
            return i;
        }
        return -1;
    }

    public static boolean deposit(int count, int... ids) {
        Item item = Inventory.getItem(ids);
        String action = ActionState.DEPOSIT.getString(count);
        if (item != null) {
            if (item.interact(action)) {
                if (action.contains("X")) {
                    //TODO:Find a dynamic secure method for checking if the widgetchild pops up for the user to enter text
                    Time.sleep(900, 1500);
                    Keyboard.sendText("" + count, true);
                }
                return true;
            }
        }
        return false;
    }

    public static BankItem[] getItems() {
        List<BankItem> items = new ArrayList<>();
        WidgetChild wc = Widgets.get(PARENT, ITEMS);
        int[] slotIds = wc.getSlotContentIds();
        for (int i = 0; i < slotIds.length - 1; i++) {
            if (slotIds[i] != 0) {
                items.add(new BankItem(wc, i));
            }
        }
        return items.toArray(new BankItem[items.size()]);
    }

    public static BankItem getItem(int... ids) {
        for (BankItem item : getItems()) {
            for (int i : ids) {
                if (item.getId() == i) {
                    return item;
                }
            }
        }
        return null;
    }

    public static boolean isOpen() {
        WidgetChild wc = Widgets.get(PARENT, ITEMS);
        if (wc != null) {
            int[] ids = wc.getSlotContentIds(), stacks = wc.getSlotStackSizes();
            for (int i = 0; i < ids.length - 1; i++) {
                if (ids[i] == 0 && stacks[i] > 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean scroll(boolean up) {
        if (!isOpen())
            return false;
        Rectangle r = (up ? SCROLL_UP : SCROLL_DOWN);
        if (!r.contains(Mouse.getPosition()))
            Mouse.move(Random.nextInt((int) r.getMinX(), (int) r.getMaxX()), Random.nextInt((int) r.getMinY(), (int) r.getMaxY()));
        Mouse.click(true);
        return r.contains(Mouse.getPosition());
    }

    public static boolean close() {
        WidgetChild closeButton = Widgets.get(PARENT, CLOSE);
        return closeButton != null && closeButton.interact("Close");
    }

    public static boolean open() {
        for (BankType bt : BankType.values()) {
            Interactable interactable = bt.getNearest();
            if (interactable != null) {
                Camera.turnTo((Locatable) interactable);
                if (interactable.interact(bt.getAction())) {
                    Timer t = new Timer(2500);
                    Tile prevLoc = Players.getLocal().getLocation();
                    while (t.isRunning() && isOpen()) {
                        if (!prevLoc.equals(Players.getLocal().getLocation())) {
                            t.reset();
                        }
                        Task.sleep(250);
                    }
                    return isOpen();
                }
            }
        }
        return false;
    }

    public static enum BankType {
        BANKERS(Npcs.class, "Bank", 44, 45, 56, 57, 494, 495, 498, 499, 909, 958, 1036, 2271, 2354, 2355, 3824, 5488, 5901, 4456, 4457, 4458, 4459, 5912, 5913, 6362, 6532, 6355, 6534, 6635, 7605, 8948, 9710, 14367),
        BANK_BOOTH(GameObjects.class, "Use-quickly", 782, 2213, 2995, 5276, 6084, 10517, 11402, 11758, 12759, 14367, 19230, 20325, 24914, 25808, 26972, 29085, 52589, 34752, 35647, 36786, 2012, 2015, 2019),
        BANK_CHESTS(GameObjects.class, "Open", 693, 4483, 12308, 20607, 21301, 27663, 42192);
        private final Class<? extends Locatables<?>> clazz;
        private final String action;
        private final int[] ids;

        private BankType(Class<? extends Locatables<?>> clazz, String action, int... ids) {
            this.clazz = clazz;
            this.action = action;
            this.ids = ids;
        }

        public Interactable getNearest() {
            try {
                Method m = clazz.getMethod("getNearest", int[].class);
                return (Interactable) m.invoke(null, ids);
            } catch (Exception e) {
                return null;
            }
        }

        public int[] getIds() {
            return ids;
        }

        public String getAction() {
            return action;
        }
    }
}
