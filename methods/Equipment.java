package com.infibot.client.api.methods;

import com.infibot.client.api.util.Convertor;
import com.infibot.client.api.wrappers.Item;
import com.infibot.client.api.wrappers.WidgetChild;

public class Equipment {
    public enum Slot {
        HEAD(12),
        CAPE(13),
        NECK(14),
        WEAPON(16),
        CHEST(17),
        SHIELD(18),
        LEGS(26),
        HANDS(21),
        FEET(20),
        RING(22),
        AMMO(15);

        private final int id;
        private Slot(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

    }

    public static WidgetChild getSlotWidget(Slot slot) {
        return Widgets.get(387, slot.getId());
    }

    public static WidgetChild[] getSlotWidgets() {
        return Convertor.convert(Slot.values(), SLOT_WIDGET_CHILD_CONVERTOR);
    }

    public static boolean isOneOfEquipped(int... ids) {
        if(openEquipment()) {
            for(WidgetChild wc : getSlotWidgets()) {
                if(wc != null) {
                    for(int i : ids) {
                        if(i == wc.getParentId()) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public static boolean equip(int... ids) {
        Item i = Inventory.getItem(ids);
        return i != null && i.interact("Equip"); //Might be wrong action
    }

    public static int getId(Slot slot) {
        return openEquipment() ? getSlotWidget(slot).getParentId() : -1;
    }

    public static boolean unequipOneOf(int... ids) {
        for(Slot slot : Slot.values()) {
            int slotId = getId(slot);
            for(int id : ids) {
                if(id == slotId) {
                    return getSlotWidget(slot).interact("Remove"); //Might be wrong action
                }
            }
        }
        return false;
    }

    public static boolean isEmpty(Slot slot) {
        return getId(slot) == -1;
    }

    private static boolean openEquipment() {
        return Tab.EQUIPMENT.open();
    }

    private static final Convertor<Slot, WidgetChild> SLOT_WIDGET_CHILD_CONVERTOR = new Convertor<Slot, WidgetChild>() {
        @Override
        public WidgetChild convert(Slot slot) {
            return getSlotWidget(slot);
        }
    };
}
