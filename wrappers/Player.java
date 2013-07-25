package com.infibot.client.api.wrappers;

import com.infibot.client.accessors.Model;
import com.infibot.client.api.methods.Mouse;
import com.infibot.client.api.methods.Players;
import com.infibot.client.api.util.Calculations;
import com.infibot.client.api.util.filter.Filter;

import java.awt.*;

public class Player extends Actor {

    private final com.infibot.client.accessors.Player accessor;
    private final Filter<Player> validateFilter = new Filter<Player>() {
        @Override
        public boolean accept(Player player) {
            return Player.this.equals(player);
        }
    };

    public Player(com.infibot.client.accessors.Player accessor) {
        super(accessor);
        this.accessor = accessor;
    }

    @Override
    public String getName() {
        return accessor != null ? accessor.getName() : "";
    }

    public int getCombatLevel() {
        return accessor != null ? accessor.getCombatLevel() : -1;
    }

    public int getTotalLevel() {
        return accessor != null ? accessor.getTotalLevel() : -1;
    }

    public int getSkullIcon() {
        return accessor != null ? accessor.getSkullIcon() : -1;
    }

    public int getPrayerIcon() {
        return accessor != null ? accessor.getPrayerIcon() : -1;
    }

    public boolean validate() {
        return Players.getLoaded(validateFilter).size() > 0;
    }

    public Model getModel() {
        return accessor != null ? accessor.getModel() : null;
    }

    @Override
    public boolean hover() {
        //TODO:Implement method to convert Model into a Polygon or make a wrapper and implement it there
        Polygon polygon = new Polygon();
        if (polygon.contains(Mouse.getPosition()))
            return true;
        return super.hover() && polygon.contains(Mouse.getPosition());
    }

    @Override
    public boolean isOnScreen() {
        Model model = getModel();
        if (model != null) {
            for (int i = 0; i < model.getVerticesX().length; i++) {
                if (!super.isOnScreen(Calculations.worldToScreen(model.getVerticesX()[i], model.getVerticesY()[i], model.getModelHeight())))
                    return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Player) {
            Player n = (Player) o;
            return n.accessor != null && accessor != null && n.accessor.equals(accessor);
        }
        return false;
    }

    @Override
    public int getId() {
        return -1;
    }
}
