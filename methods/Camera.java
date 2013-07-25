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
    /**
     *
     * @return the current compass angle
     */
    public static int getCompassAngle(){
        return Game.getClient().getCompassAngle();
    }

    /**
     *
     * @return the current camera yaw
     */
    public static int getYaw(){
        return (int) (Game.getClient().getCameraYaw() / 5.69D);
    }

    /**
     *
     * @return the current camera pitch
     */
    public static int getPitch(){
        return (int) (Game.getClient().getCameraPitch() / 390D * 100D);
    }

    /**
     * Turns the camera to the locatable.
     * @param loc the locatable
     */
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

    /**
     * Get relative angle to another angle
     * @param degrees the angle specified
     * @return the relative angle to degrees
     */
    public static int getAngleTo(int degrees) {
        int angle = getYaw();
        if(angle < degrees) angle += 360;
        int _angle = angle - degrees;
        return _angle > 180 ? _angle - 360 : _angle;
    }

    /**
     *
     * @param loc
     * @return the relative angle to the locatable
     */
    public static int getAngleTo(Locatable loc) {
        Tile t = Players.getLocal().getLocation();
        int degr = (int) Math.toDegrees(Math.atan2(t.getY() - loc.getLocation().getY(), t.getX() - loc.getLocation().getX()));
        return getAngleTo(degr);
    }

    /**
     *
     * @return internal camera x
     */
    public static int getX(){
        return Game.getClient().getCameraX();
    }

    /**
     *
     * @return internal camera y
     */
    public static int getY(){
        return Game.getClient().getCameraY();
    }

    /**
     *
     * @return internal camera x
     */
    public static int getZ(){
        return Game.getClient().getCameraZ();
    }
}
