package com.infibot.client.api.methods;

import com.infibot.client.api.wrappers.WidgetChild;
import com.infibot.client.utilities.time.Time;

import java.util.LinkedList;
import java.util.List;

public class PrayerBook {


    public static List<Prayer> getSelectedPrayers() {
        List<Prayer> list = new LinkedList<>();
        for (Prayer prayer : Normal.values()) {
            if (prayer.isActive()) {
                list.add(prayer);
            }
        }
        return list;
    }

    public static boolean activate(Prayer prayer) {
        return activate(prayer, true);
    }

    public static boolean open() {
        return Tab.PRAYER.open();
    }

    public static boolean activate(Prayer prayer, boolean active) {
        return active == prayer.isActive() || (open() && prayer.getChild().click());
    }

    public static boolean flash(Prayer prayer) {
        return flash(prayer, false);
    }

    public static boolean flash(Prayer prayer, int delay) {
        return flash(prayer, false, delay);
    }

    public static boolean flash(Prayer prayer, boolean endstate) {
        return flash(prayer, endstate, 100);
    }

    public static boolean flash(Prayer prayer, boolean endstate, int delay) {
        if (open() && prayer.getChild().click())
            Time.sleep(delay);
        return endstate == prayer.isActive() || flash(prayer, endstate, delay);
    }

    public static enum Normal implements Prayer {
        THICK_SKIN(0, 1, 1),
        BURST_OF_STRENGTH(1, 4, 2),
        CLARITY_OF_THOUGHT(2, 7, 4),
        SHARP_EYE(3, 8, 1 << 18),
        MYSTIC_WILL(4, 8, 1 << 19),
        ROCK_SKIN(5, 10, 8),
        SUPERHUMAN_STRENGTH(6, 13, 16),
        IMPROVED_REFLEXES(7, 16, 32),
        RAPID_RESTORE(8, 19, 64),
        RAPID_HEAL(9, 22, 128),
        PROTECT_ITEM_REGULAR(10, 25, 256),
        HAWK_EYE(11, 26, 1048576),
        MYSTIC_LORE(12, 27, 2097152),
        STEEL_SKIN(13, 28, 512),
        ULTIMATE_STRENGTH(14, 31, 1024),
        INCREDIBLE_REFLEXES(15, 34, 2048),
        PROTECT_FROM_SUMMONING(16, 35, 16777216),
        PROTECT_FROM_MAGIC(17, 37, 4096),
        PROTECT_FROM_MISSILES(18, 40, 8192),
        PROTECT_FROM_MELEE(19, 43, 16384),
        EAGLE_EYE(20, 44, 4194304),
        MYSTIC_MIGHT(21, 45, 8388608),
        RETRIBUTION(22, 46, 32768),
        REDEMPTION(23, 49, 65536),
        SMITE(24, 52, 131072),
        CHIVALRY(25, 60, 33554432),
        RAPID_RENEWAL(26, 65, 13421778),
        PIETY(27, 70, 67108864),
        RIGOUR(28, 74, 268435456),
        AUGURY(29, 77, 536870912);
        private final int index;
        private final int level;
        private final int setting;

        private Normal(int index, int level, int setting) {
            this.index = index;
            this.level = level;
            this.setting = setting;
        }

        public boolean isActive() {
            return (Settings.get(1395) & setting) != 0;
        }

        public WidgetChild getChild() {
            return Widgets.get(271, 7, index);
        }

        public int getWidgetIndex() {
            return index;
        }

        public int getRequiredLevel() {
            return level;
        }

        public int getSettings() {
            return setting;
        }
    }

    public interface Prayer {
        public boolean isActive();

        public WidgetChild getChild();

        public int getWidgetIndex();

        public int getRequiredLevel();

        public int getSettings();
    }
}
