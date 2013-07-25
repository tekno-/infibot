package com.infibot.client.api.wrappers;

import com.infibot.client.api.methods.Mouse;
import com.infibot.client.api.methods.Npcs;
import com.infibot.client.api.util.filter.Filter;

import java.awt.*;

public class Npc extends Actor {
    private final com.infibot.client.accessors.Npc accessor;
    public Npc(com.infibot.client.accessors.Npc accessor) {
        super(accessor);
        this.accessor = accessor;
    }

    public int getId() {
        return accessor != null && accessor.getComposite() != null ? accessor.getComposite().getId() : -1;
    }

    public String[] getActions() {
        return accessor != null && accessor.getComposite() != null ? accessor.getComposite().getActions() : new String[0];
    }

    @Override
    public String getName() {
        return accessor != null && accessor.getComposite() != null ? accessor.getComposite().getName() : "";
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
    public boolean equals(Object o) {
        if(o instanceof Npc) {
            Npc n = (Npc) o;
            return n.accessor != null && accessor != null && n.accessor.equals(accessor);
        }
        return false;
    }

    public boolean validate() {
        return Npcs.getLoaded(validateFilter).size() > 0;
    }

    private final Filter<Npc> validateFilter = new Filter<Npc>() {
        @Override
        public boolean accept(Npc npc) {
            return Npc.this.equals(npc);
        }
    };

    @Override
    public int hashCode() {
        return 17 * accessor.hashCode();
    }
}
