package com.infibot.client.api;

import com.infibot.client.accessors.Client;
import com.infibot.core.storage.Storage;

import java.applet.Applet;
import java.awt.*;

public class Game {

    public static Client getClient() {
        return (Client) Storage.loader.getApplet();
    }

    public static int getBaseX() {
        return getClient().getBaseX();
    }

    public static Canvas getCanvas() {
        for(Component c : ((Applet) Game.getClient()).getComponents()) {
            if(c instanceof Canvas) {
                return (Canvas) c;
            }
        }
        return null;
    }

    public static int getBaseY() {
        return getClient().getBaseY();
    }

}
