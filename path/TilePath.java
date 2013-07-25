package com.infibot.client.api.path;


import com.infibot.client.api.methods.Walking;
import com.infibot.client.api.util.Calculations;
import com.infibot.client.api.wrappers.Tile;
import com.infibot.client.utilities.time.Random;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ian
 * Date: 6/2/13
 * Time: 6:40 PM
 */
public class TilePath extends Path<Tile> {

    public TilePath(Tile... tiles) {
        super(tiles);
    }

    public Tile[] getTiles() {
        return this.toArray(new Tile[this.size()]);
    }

    /**
     * Translates all tiles in the path by a random amount.
     *
     * @param offsetX The maximum X offset
     * @param offsetY The maximum Y offset
     */
    public TilePath translate(int offsetX, int offsetY) {
        for (Tile t : this) {
            remove(t);
            add(t.derive(offsetX, offsetY));
        }
        return this;
    }

    /**
     * Returns a new path with interleaved tiles
     * <p/>
     * Note: The original path isn't modified
     *
     * @param min The minimum distance between tiles
     * @param max The maximum distance between tiles
     * @return A newly interleaved path
     */
    public Path interleave(int min, int max) {
        List<Tile> list = this;
        Tile last = list.get(0);
        int nextInterleave = Random.nextInt(min, max);
        for (int i = 1; i < list.size(); i++) {
            if (last.distanceTo(list.get(i)) < nextInterleave) {
                Tile t = list.get(i);
                list.remove(i);
                nextInterleave = Random.nextInt(min, max);
                last = t;
            }
        }
        return new TilePath(list.toArray(new Tile[list.size()]));
    }

    public boolean traverse() {
        Tile next = getNext();
        return next != null && Walking.setRun(true) && Walking.clickOnMinimap(next);
    }

    public Tile getNext() {
        Tile tile = Walking.getDestination();
        for (int i = this.size() - 1; i >= 0; i--) {
            Tile curr = this.get(i);
            if (Calculations.tileToMinimap(curr).x != -1 && (curr.distanceTo(tile) < 3 || tile.distanceTo() < 7)) {
                return curr;
            }
        }
        return null;
    }
}
