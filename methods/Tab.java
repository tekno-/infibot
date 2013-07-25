package com.infibot.client.api.methods;

import com.infibot.client.api.util.Task;
import com.infibot.client.api.util.Timer;
import com.infibot.client.api.wrappers.WidgetChild;

public enum Tab {
    ATTACK(47),
    SKILLS(48),
    QUEST(49),
    INVENTORY(50),
    EQUIPMENT(51),
    PRAYER(52),
    MAGIC(53),
    CLANCHAT(30),
    FRIENDS(31),
    IGNORES(32),
    LOGOUT(33),
    SETTINGS(34),
    EMOTES(35),
    MUSIC(36);

    private final int compId;
    private Tab(int compId) {
        this.compId = compId;
    }

    /**
     * Checks if a tab is open.
     * @return true if tab is open; otherwise false
     */
    public boolean isOpen() {
        WidgetChild wc = getComponent();
        return wc != null && wc.getTextureId() > 1000;
    }

    /**
     * Gets tab component.
     * @return the tab component
     */
    public WidgetChild getComponent() {
        return Widgets.get(548, compId);
    }

    /**
     * Gets current tab.
     * @return current tab; otherwise Inventory
     */
    public static Tab getCurrent() {
        for(Tab tab : Tab.values()) {
            if(tab.isOpen()) {
                return tab;
            }
        }
        return Tab.INVENTORY;
    }

    /**
     * Opens this tab.
     * @return true if succeeded; otherwise false
     */
    public boolean open() {
        if(isOpen()) {
            return true;
        }
        WidgetChild wc = getComponent();
        if(wc != null && wc.interact("Open")) {
            Timer t = new Timer(1000);
            while(t.isRunning() && !isOpen()) Task.sleep(50);
            return isOpen();
        }
        return false;
    }
}
