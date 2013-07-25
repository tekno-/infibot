package com.infibot.client.api.methods;

import com.infibot.client.api.Game;
import com.infibot.client.api.methods.Mouse;
import com.infibot.client.api.util.Random;
import com.infibot.client.api.util.Task;
import com.infibot.client.api.util.Timer;

/**
 * Created with IntelliJ IDEA.
 * User: Ian
 * Date: 7/17/13
 * Time: 2:43 AM
 */
public class Menu {
    public static boolean isOpen() {
        return Game.getClient().isMenuOpen();
    }

    public static int getX() {
        return Game.getClient().getMenuX();
    }

    public static int getY() {
        return Game.getClient().getMenuY();
    }

    public static int getHeight() {
        return Game.getClient().getMenuHeight();
    }

    public static int getWidth() {
        return Game.getClient().getMenuWidth();
    }

    public static int getOptionCount() {
        return Game.getClient().getMenuOptionCount();
    }

    public static String[] getActions() {
        return Game.getClient().getMenuActions();
    }

    public static boolean select(String action) {
        return select(action, "");
    }

    public static boolean select(String action, String option) {
        int idx = getIndex(action, option);
        if(idx != -1) {
            Mouse.click(false);
            Timer t = new Timer(500);
            while(t.isRunning() && !isOpen()) Task.sleep(50);
            if(isOpen()) {
                Mouse.move(Random.nextInt(getX(), getX() + getWidth()), getY() + 29 + idx * 15 + Random.nextInt(-3, 3));
                Task.sleep(150);
                Mouse.click(true);
                return true;
            }
        }
        return false;
    }
    public static int getIndex(String action, String option) {
        String[] actions = getActions();
        for(int i = 0; i < actions.length - 1; i++) {
            String s = actions[i];
            if(s != null && s.contains(action) && s.contains(option)) {
                return i;
            }
        }
        return -1;
    }
}
