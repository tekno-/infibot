package com.infibot.client.api.methods;

import com.infibot.client.api.Game;
import com.infibot.client.api.Locatable;
import com.infibot.client.api.util.Random;
import com.infibot.client.api.util.Task;
import com.infibot.client.api.wrappers.Tile;

import java.awt.event.KeyEvent;

/**
 * Created with IntelliJ IDEA.
 * User: Ian
 * Date: 7/17/13
 * Time: 2:41 AM
 */
public class Camera {
    public static int getCompassAngle(){
        return Game.getClient().getCompassAngle();
    }
    public static int getYaw(){
        return (int) (Game.getClient().getCameraYaw() / 5.69D);
    }
    public static int getPitch(){
        return (int) (Game.getClient().getCameraPitch() / 390D * 100D);
    }

    public static void turnTo(Locatable loc) {
        if(Math.abs(getAngleTo(loc)) > 5) {
            boolean b = getAngleTo(loc) > 0;
            Keyboard.pressKey((char) (b ? KeyEvent.VK_LEFT : KeyEvent.VK_RIGHT));
            while(getAngleTo(loc) > 5) {
                Task.sleep(Random.nextInt(25, 75));
            }
            Keyboard.pressKey((char) (b ? KeyEvent.VK_LEFT : KeyEvent.VK_RIGHT));
        }
    }

    public static int getAngleTo(int degrees) {
        int angle = getYaw();
        if(angle < degrees) angle += 360;
        int _angle = angle - degrees;
        return _angle > 180 ? _angle - 360 : _angle;
    }

    public static int getAngleTo(Locatable loc) {
        Tile t = Players.getLocal().getLocation();
        int degr = (int) Math.toDegrees(Math.atan2(t.getY() - loc.getLocation().getY(), t.getX() - loc.getLocation().getX()));
        return getAngleTo(degr);
    }

    public static int getX(){
        return Game.getClient().getCameraX();
    }
    public static int getY(){
        return Game.getClient().getCameraY();
    }
    public static int getZ(){
        return Game.getClient().getCameraZ();
    }
}
