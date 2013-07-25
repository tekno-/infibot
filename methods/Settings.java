package com.infibot.client.api.methods;

import com.infibot.client.api.Game;

/**
 * Created with IntelliJ IDEA.
 * User: Ian
 * Date: 7/17/13
 * Time: 2:15 AM
 */
public class Settings {
    public static int get(int index) {
        return Game.getClient().getSettings()[index];
    }
}
