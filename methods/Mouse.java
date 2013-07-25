package com.infibot.client.api.methods;

import com.infibot.client.api.Game;
import com.infibot.client.api.util.Random;
import com.infibot.client.api.util.Task;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public final class Mouse {
    private static Point last = new Point();
    private static Speed speed = Speed.NORMAL;

    private Mouse() {
    }

    public static void hop(int x, int y) {
        last.move(x, y);
        com.infibot.client.accessors.Mouse m = Game.getClient().getMouse();
        m.mouseMoved(new MouseEvent(Game.getCanvas(), MouseEvent.MOUSE_MOVED, System.currentTimeMillis(), 0, x, y, 0, false));
    }

    public static void hop(Point p) {
        hop(p.x, p.y);
    }

    public static void click(boolean left) {
        com.infibot.client.accessors.Mouse m = Game.getClient().getMouse();
        m.mousePressed(new MouseEvent(Game.getCanvas(), MouseEvent.MOUSE_PRESSED, System.currentTimeMillis(), 0, last.x, last.y, 1, false, left ? MouseEvent.BUTTON1 : MouseEvent.BUTTON3));
        Task.sleep(Random.nextGaussian(100, 50));
        long l = System.currentTimeMillis();
        m.mouseReleased(new MouseEvent(Game.getCanvas(), MouseEvent.MOUSE_RELEASED, l, 0, last.x, last.y, 1, false));
        m.mouseClicked(new MouseEvent(Game.getCanvas(), MouseEvent.MOUSE_CLICKED, l, 0, last.x, last.y, 1, false));
    }

    public static void move(int x, int y) {
        while (distTo(x, y) > 2) {
            List<Point> list = genLine(last.x, last.y, x, y);
            for (Point p : list) {
                hop(p);
                Task.sleep(Random.nextGaussian(25 / speed.ordinal(), Random.nextInt(5, 15)));
            }
        }
    }

    public static Point getPosition() {
        return last;
    }

    private static List<Point> genLine(int currX, int currY, int endX, int endY) {
        List<Point> points = new ArrayList<>();
        while (currX != endX || currY != endY) {
            int newX = currX + (Math.random() < 0.5D ? 0 : (endX > currX ? 1 : -1));
            int newY = currY + (Math.random() > 0.5D ? 0 : (endY > currY ? 1 : -1));
            Point p = new Point(newX, newY);
            if (currX != newX || currY != newY) {
                points.add(p);
                currX = newX;
                currY = newY;
            }
        }
        return points;
    }

    private static double distBetween(int x1, int y1, int x2, int y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    private static int distTo(int x, int y) {
        return (int) distBetween(last.x, last.y, x, y);
    }

    public static void setSpeed(Speed speed) {
        if (speed != null) {
            Mouse.speed = speed;
        }
    }

    public static enum Speed {
        VERY_SLOW, SLOW, NORMAL, FAST, VERY_FAST
    }
}

