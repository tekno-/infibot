package com.infibot.client.api.util;

import com.infibot.client.accessors.Client;
import com.infibot.client.api.Game;
import com.infibot.client.api.Locatable;
import com.infibot.client.api.methods.Players;
import com.infibot.client.api.wrappers.Player;
import com.infibot.client.api.wrappers.Tile;

import java.awt.Point;

public class Calculations {
    public static final int[] SIN_TABLE = new int[0x4000];
    public static final int[] COS_TABLE = new int[0x4000];

    static {
        final double d = 0.00038349519697141029D;
        for (int i = 0; i < 0x4000; i++) {
            Calculations.SIN_TABLE[i] = (int) (32768D * Math.sin(i * d));
            Calculations.COS_TABLE[i] = (int) (32768D * Math.cos(i * d));
        }
    }

    public static int calculateTileHeight(int x, int y, int plane) {
        Client c = Game.getClient();
        int x1 = x >> 9;
        int y1 = y >> 9;
        byte[][][] tileBytes = c.getTileBytes();
        if (tileBytes != null && x1 >= 0 && x1 < 104 && y1 >= 0 && y1 < 104) {
            int[][] heights = c.getTileHeights()[plane];
            return (heights[x][y] + heights[x + 1][y] + heights[x][y + 1] + heights[x + 1][y + 1]) / 4;
        }
        return 0;
    }

    public static Point tileToScreen(Tile tile) {
        int x = (tile.getX() - Game.getBaseX()) * 128;
        int y = (tile.getY() - Game.getBaseX()) * 128;
        return worldToScreen(x, y, calculateTileHeight(x, y, tile.getPlane()) - tile.getPlane());
    }

    public static Point worldToScreen(int x, int y, int height) {
        Client client = Game.getClient();
        x -= client.getCameraY();
        height -= client.getCameraZ();
        y -= client.getCameraX();
        int var = SIN_TABLE[client.getCameraPitch()];
        int var2 = COS_TABLE[client.getCameraPitch()];
        int var3 = SIN_TABLE[client.getCameraYaw()];
        int var4 = COS_TABLE[client.getCameraYaw()];
        int var5 = var3 * y + var4 * x >> 16;
        y = y * var4 - var3 * x >> 16;
        x = var5;
        var5 = var2 * height - y * var >> 16;
        y = y * var2 + var * height >> 16;
        if(y != 0) {
            return new Point((x << 9) / y + 256, (var5 << 9) / y + 167);
        }
        return new Point(-1, -1);
    }

    public static int angleToTile(Locatable locatable) {
        Tile tile = locatable.getLocation();
        Tile me = Players.getLocal().getLocation();
        int angle = (int) Math.toDegrees(Math.atan2(tile.getY() - me.getY(), tile.getX() - me.getX()));
        if(angle < 0) {
            angle += 360;
        }
        return angle;
    }

    public static Point tileToMinimap(Locatable locatable) {
        Tile tile = locatable.getLocation();
        int x = tile.getX() - Game.getBaseX();
        int y = tile.getY() - Game.getBaseY();
        Player p = Players.getLocal();
        return worldToMinimap((x * 4 + 2) - p.getLocation().getX() / 32, (y * 4 + 2) - p.getLocation().getY() / 32);
    }

    private static Point worldToMinimap(int rX, int rY) {
        Client c = Game.getClient();
        if(rX * rX + rY * rY > 6400) { //dist larger than 80
            return new Point(-1, -1);
        }
        int angle = c.getCompassAngle() & 0x7FF + c.getMinimapOffset(); //Might need to be swapped, unsure
        int sin = SIN_TABLE[angle] * 256 / (c.getMinimapScale() + 256);
        int cos = COS_TABLE[angle] * 256 / (c.getMinimapScale() + 256);
        int x = rX * sin + rX * cos >> 16;
        int y = rY * cos - rY * sin >> 16;
        return new Point(644 + x, 80 - y);
    }

}
