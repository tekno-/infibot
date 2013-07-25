package com.infibot.client.api.wrappers;

import com.infibot.client.api.methods.Mouse;

import java.awt.*;

public class WidgetChild extends Widget {
    public WidgetChild(com.infibot.client.accessors.Widget... accessor) {
        super(accessor);
    }

    public int getParent() {
        return accessor != null ? accessor[0].getParent() : -1;
    }

    public int getParentId() {
        return accessor != null ? accessor[0].getParentId() : -1;
    }

    @Override
    public boolean hover() {
        Rectangle area = getArea();
        if (area.contains(Mouse.getPosition()))
            return true;
        return super.hover() && area.contains(Mouse.getPosition());
    }
}
