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
    /**
     *
     * @return true if the menu is open; otherwise false
     */
    public static boolean isOpen() {
        return Game.getClient().isMenuOpen();
    }

    /**
     *
     * @return the X position of the menu
     */
    public static int getX() {
        return Game.getClient().getMenuX();
    }

    /**
     *
     * @return the Y position of the menu
     */
    public static int getY() {
        return Game.getClient().getMenuY();
    }

    /**
     *
     * @return the height of the menu
     */
    public static int getHeight() {
        return Game.getClient().getMenuHeight();
    }

    /**
     *
     * @return the width of the menu
     */
    public static int getWidth() {
        return Game.getClient().getMenuWidth();
    }

    /**
     *
     * @return the amount of options in the menu
     */
    public static int getOptionCount() {
        return Game.getClient().getMenuOptionCount();
    }

    /**
     *
     * @return an array of String actions available in the menu
     */
    public static String[] getActions() {
        return Game.getClient().getMenuActions();
    }

    /**
     * Selects an action from the menu.
     * @param action the action to select
     * @return true if succeeded; otherwise false
     */
    public static boolean select(String action) {
        return select(action, "");
    }

    /**
     * Selects an action with specified option from the menu.
     * @param action the action to select
     * @param option the option to select
     * @return true if succeeded; otherwise false
     */
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

    /**
     * Looks for the index of an action/option combination.
     * @param action the action to look for
     * @param option the option to look for
     * @return the index of the action/option combination; or -1 if not found
     */
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
