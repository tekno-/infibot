package com.infibot.client.api.wrappers;

import com.infibot.client.api.methods.Tab;

import java.awt.*;

public class Item extends Interactable {
    private final WidgetChild widget;
    protected final int idx;

    public Item(WidgetChild wc, int idx) {
        this.widget = wc;
        this.idx = idx;
    }

    public int getId() {
        return widget.getSlotContentIds()[idx];
    }

    public int getStackSize() {
        return widget.getSlotStackSizes()[idx];
    }

    public String getName() {
        return widget != null ? widget.getName() : "";
    }

    public Rectangle getArea() {
        return widget != null ? widget.getArea() : null;
    }

    @Override
    public Point getScreenPoint() {
        return widget.getHeight() == 8 ?
                new Point((idx % 8) * 47 + widget.getX() + 50, (((idx / 8) * 37 + widget.getY() + 75))) :
                new Point(580 + (idx % 4) * 42, 228 + idx * 9);
    }

    @Override
    public boolean interact(String action, String option) {
        return Tab.INVENTORY.open() && super.interact(action, option);
    }
}
