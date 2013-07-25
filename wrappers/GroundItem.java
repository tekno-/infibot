package com.infibot.client.api.wrappers;

import com.infibot.client.api.Game;
import com.infibot.client.api.Identifiable;
import com.infibot.client.api.Locatable;
import com.infibot.client.api.Validatable;

public class GroundItem implements Identifiable, Locatable, Validatable {

    private final com.infibot.client.accessors.Item accessor;
    private final int x;
    private final int y;

    public GroundItem(com.infibot.client.accessors.Item i, int x, int y) {
        this.accessor = i;
        this.x = x;
        this.y = y;
    }

    @Override
    public int getId() {
        return accessor != null ? accessor.getId() + 1 : -1;
    }

    public int getStackSize() {
        return accessor != null ? accessor.getStackSize() : -1;
    }

    @Override
    public Tile getLocation() {
        return new Tile(x + Game.getBaseX(), y + Game.getBaseY());
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof GroundItem) {
            GroundItem n = (GroundItem) o;
            return n.accessor != null && accessor != null && n.accessor.equals(accessor);
        }
        return false;
    }

    public boolean validate() {
        return getId() != -1 && getStackSize() != -1;
    }
}
