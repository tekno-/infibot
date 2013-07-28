package com.infibot.client.api.wrappers;

import com.infibot.client.api.methods.Mouse;

import java.awt.*;

public abstract class Interactable {
    private static final Rectangle GAME_SCREEN = new Rectangle(0, 0, 765, 503);

    public abstract Point getScreenPoint();

    public boolean hover() {
        Point p = getScreenPoint();
        Mouse.move(p.x, p.y);
        return true;
    }

    public boolean click() {
        return click(true);
    }

    public boolean click(boolean left) {
        if (hover()) {
            Mouse.click(left);
            return true;
        }
        return false;
    }

    public boolean interact(String action) {
        return interact(action, "");
    }

    public boolean interact(String action, String option) {
        return hover() && com.infibot.client.api.methods.Menu.select(action, option);
    }

    protected boolean isOnScreen(Point point) {
        return GAME_SCREEN.contains(point);
    }

    public boolean isOnScreen() {
        return isOnScreen(getScreenPoint());
    }
}
