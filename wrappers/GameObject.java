package com.infibot.client.api.wrappers;

import com.infibot.client.accessors.GenericObject;
import com.infibot.client.api.Game;
import com.infibot.client.api.Identifiable;
import com.infibot.client.api.Locatable;
import com.infibot.client.api.Validatable;
import com.infibot.client.api.methods.GameObjects;
import com.infibot.client.api.util.Calculations;
import com.infibot.client.api.util.filter.Filter;

import java.awt.*;

public class GameObject extends Interactable implements Identifiable, Locatable, Validatable {
    private final GameObjects.ObjectType type;
    private final GenericObject accessor;
    private final Filter<GameObject> validateFilter = new Filter<GameObject>() {
        @Override
        public boolean accept(GameObject gameObject) {
            return GameObject.this.equals(gameObject);
        }
    };

    public GameObject(GenericObject accessor, GameObjects.ObjectType type) {
        this.accessor = accessor;
        this.type = type;
    }

    @Override
    public int getId() {
        return accessor != null ? accessor.getId() >> 14 & 0x7fff : -1;
    }

    public GameObjects.ObjectType getType() {
        return type;
    }

    @Override
    public Tile getLocation() {
        return accessor != null ? new Tile(Game.getBaseX() + accessor.getId() & 0x7F, Game.getBaseY() + accessor.getId() >> 7 & 0x7F) : new Tile(-1, -1);
    }

    @Override
    public Point getScreenPoint() {
        return accessor != null ? Calculations.worldToScreen(accessor.getId() & 0x7F, accessor.getId() >> 7 & 0x7F, accessor.getRenderable().getModelHeight()) : new Point(-1, -1);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof GameObject) {
            GameObject go = (GameObject) o;
            return go.accessor.equals(accessor);
        }
        return false;
    }

    public boolean validate() {
        return GameObjects.getLoaded(validateFilter).size() > 0;
    }
}
