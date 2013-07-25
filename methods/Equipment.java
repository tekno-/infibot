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

    /**
     * Returns the WidgetChild associated with a slot.
     * @param slot the slot to return the widget for
     * @return the widgetchild associated with the slot
     */
    public static WidgetChild getSlotWidget(Slot slot) {
        return Widgets.get(387, slot.getId());
    }

    /**
     * Get the slot widgets for all slots.
     * @return an array of all slot widgets, in the order of the Slot values
     */
    public static WidgetChild[] getSlotWidgets() {
        return Convertor.convert(Slot.values(), SLOT_WIDGET_CHILD_CONVERTOR);
    }

    /**
     * @param ids the ids to check for
     * @return true if one of the ids is found; otherwise false
     */
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

    /**
     * Equips the item with specified ids.
     * @param ids the ids to look for
     * @return true if equipping succeeded; otherwise false
     */
    public static boolean equip(int... ids) {
        Item i = Inventory.getItem(ids);
        return i != null && i.interact("Equip"); //Might be wrong action
    }

    /**
     *
     * @param slot the slot to check for
     * @return the ID in the specified slot
     */
    public static int getId(Slot slot) {
        return openEquipment() ? getSlotWidget(slot).getParentId() : -1;
    }

    /**
     * Unequips the item first found with any matching ID.
     * @param ids the ids to look for
     * @return true if unequpping succeeded; otherwise false
     */
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

    /**
     *
     * @param slot the slot to check for
     * @return true if the slot contains no item; otherwise false
     */
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
