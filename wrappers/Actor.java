package com.infibot.client.api.wrappers;

import com.infibot.client.api.Game;
import com.infibot.client.api.Identifiable;
import com.infibot.client.api.Locatable;
import com.infibot.client.api.Validatable;
import com.infibot.client.api.util.Calculations;

import java.awt.*;

public abstract class Actor extends Interactable implements Identifiable, Locatable, Validatable {
    private final com.infibot.client.accessors.Actor accessor;

    public Actor(com.infibot.client.accessors.Actor accessor) {
        this.accessor = accessor;
    }

    public final Point getScreenPoint() {
        return accessor != null ? Calculations.worldToScreen(accessor.getGridX(), accessor.getGridY(), accessor.getModelHeight()) : new Point(-1, -1);
    }

    public int getAnimation() {
        return accessor != null ? accessor.getAnimation() : -1;
    }

    public int getHealth() {
        return accessor != null ? accessor.getHealth() : -1;
    }

    public int getMaxHealth() {
        return accessor != null ? accessor.getMaxHealth() : -1;
    }

    public int getSpeed() {
        return accessor != null ? accessor.getSpeed() : -1;
    }

    public boolean isAnimating() {
        return accessor != null && accessor.isAnimating();
    }

    public int getOrientation() {
        return accessor != null ? accessor.getOrientation() : -1;
    }

    public String getTextSpoken() {
        return accessor != null ? accessor.getSpokenText() : "";
    }

    public abstract String getName();

    @Override
    public Tile getLocation() {
        if (accessor == null) {
            return new Tile(-1, -1);
        }
        return new Tile((accessor.getGridX() >> 7) + Game.getBaseX(), (accessor.getGridY() >> 7) + Game.getBaseY(), Game.getClient().getPlane());
    }
}
