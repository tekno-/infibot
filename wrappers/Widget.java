package com.infibot.client.api.wrappers;

import com.infibot.client.api.Validatable;
import com.infibot.client.api.util.Random;

import java.awt.*;

public class Widget extends Interactable implements Validatable {
    protected final com.infibot.client.accessors.Widget[] accessor;
    private WidgetChild[] children = null;
    private Rectangle areaCache;
    private int xCache;
    private int yCache;

    public Widget(com.infibot.client.accessors.Widget... accessor) {
        this.accessor = accessor;
    }

    protected boolean hasMoved() {
        return accessor != null && (accessor[0].getX() != xCache || accessor[0].getY() != yCache);
    }

    public int getX() {
        return accessor[0].getX();
    }

    public int getY() {
        return accessor[0].getY();
    }

    public int getHeight() {
        return accessor[0].getHeight();
    }

    public int getWidth() {
        return accessor[0].getHeight();
    }

    public Rectangle getArea() {
        if (hasMoved() || areaCache == null) {
            xCache = accessor[0].getX();
            yCache = accessor[0].getY();
            areaCache = new Rectangle(xCache, yCache, accessor[0].getWidth(), accessor[0].getHeight());
        }
        return areaCache;
    }

    public String[] getActions() {
        return accessor != null ? accessor[0].getActions() : new String[0];
    }

    public int[] getSlotContentIds() {
        return accessor != null ? accessor[0].getSlotContentIds() : new int[0];
    }

    public int[] getSlotStackSizes() {
        return accessor != null ? accessor[0].getSlotStackSizes() : new int[0];
    }

    public WidgetChild[] getChildren() {
        if (children == null) {
            //you can use the little converter if you want..
            WidgetChild[] children = new WidgetChild[accessor.length];
            for (int i = 0; i < children.length; i++) {
                children[i] = new WidgetChild(accessor[i]);
            }
            this.children = children;
        }
        return children;
    }

    public WidgetChild getChild(int id) {
        WidgetChild[] children = getChildren();
        for (WidgetChild child : children) {
            if (child.getId() == id)
                return child;
        }
        return null;
    }

    public String getText() {
        return accessor != null ? accessor[0].getText() : "";
    }

    public boolean isHidden() {
        return accessor != null && accessor[0].isHidden();
    }

    public int getId() {
        return accessor != null ? accessor[0].getId() : -1;
    }

    public String getName() {
        return accessor != null ? accessor[0].getName() : "";
    }

    public int getTextureId() {
        return accessor != null ? accessor[0].getTextureId() : -1;
    }

    @Override
    public Point getScreenPoint() {
        return new Point(Random.nextInt(getX(), getX() + getWidth()), Random.nextInt(getY(), getY() + getHeight()));
    }

    @Override
    public boolean validate() {
        return isOnScreen();
    }
}
