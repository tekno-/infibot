package com.infibot.client.api.methods;

import com.infibot.client.api.Game;
import com.infibot.client.api.util.Random;
import com.infibot.client.api.util.Task;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class Keyboard {
    public static void pressKey(final char ch, final int code, final int delay, final int mask) {
        final KeyEvent e = getKeyEvent(KeyEvent.KEY_PRESSED, delay, mask, code, ch);
        Game.getClient().getKeyboard().keyPressed(e);
        if (ch != KeyEvent.VK_UNDEFINED) {
            Game.getClient().getKeyboard().keyTyped(e);
        }
    }

    /**
     * Used for pressing action keys. Eg. left, right, up, down, home
     *
     * @param code Key code from {@link KeyEvent}.
     * @param delay The time until the key is held down.
     */
    public static void pressKey(final int code, final int delay) {
        pressKey(KeyEvent.CHAR_UNDEFINED, code, delay, 0);
    }

    public static void pressKey(final int code) {
        pressKey(KeyEvent.CHAR_UNDEFINED, code, 0, 0);
    }

    public static void pressKey(final char ch, final int delay, final int mask) {
        pressKey(ch, ch, delay, mask);
    }

    public static void pressKey(final char ch, final int delay) {
        pressKey(ch, ch, delay, 0);
    }

    public static void pressKey(final char ch) {
        pressKey(ch, ch, 0, 0);
    }

    public static void releaseKey(final char ch, final int code, final int delay, final int mask) {
        final KeyEvent e = getKeyEvent(KeyEvent.KEY_RELEASED, delay, mask, code, ch);
        Game.getClient().getKeyboard().keyReleased(e);
    }

    public static void releaseKey(final int code, final int delay) {
        releaseKey(KeyEvent.CHAR_UNDEFINED, code, delay, 0);
    }

    public static void releaseKey(final int code) {
        releaseKey(KeyEvent.CHAR_UNDEFINED, code, 0, 0);
    }

    public static void releaseKey(final char ch, final int delay, final int mask) {
        releaseKey(ch, ch, delay, mask);
    }

    public static void releaseKey(final char ch, final int delay) {
        releaseKey(ch, ch, delay, 0);
    }

    public static void releaseKey(final char ch) {
        releaseKey(ch, ch, 0, 0);
    }

    private static KeyEvent getKeyEvent(final int type, final int delay, final int mask, final int code, final char ch) {
        return new KeyEvent(Game.getCanvas(), type, System.currentTimeMillis() + delay, mask, code, ch, getLocation(code, ch));
    }

    private static int getLocation(final int code, final char ch) {
        if (KeyEvent.VK_SHIFT <= code  && code <= KeyEvent.VK_ALT) {
            return Random.nextInt(KeyEvent.KEY_LOCATION_LEFT, KeyEvent.KEY_LOCATION_RIGHT + 1);
        }
        return KeyEvent.KEY_LOCATION_STANDARD;
    }

    public static void sendKey(final char ch) {
        sendKey(ch, 0);
    }

    public static void sendKey(char ch, final int delay) {
        int code = ch;
        if (ch >= 'a' && ch <= 'z') {
            code -= 32;
        }
        sendKey(ch, code, delay);
    }

    public static void sendKey(char ch, int code, final int delay) {
        final boolean shift = ch >= 'A' && ch <= 'Z' ? true : false;
        pressKey(ch, code, delay, shift ? InputEvent.SHIFT_DOWN_MASK: 0);
        Task.sleep(Random.nextGaussian(75, 25));
        releaseKey(ch, code, delay, shift ? InputEvent.SHIFT_DOWN_MASK : 0);
    }

    public static void sendText(final String text, final boolean pressEnter) {
        sendText(text, pressEnter, 100, 200);
    }

    public static void sendText(final String text, final boolean pressEnter, final int minDelay, final int maxDelay) {
        final char[] chars = text.toCharArray();
        for (final char element : chars) {
            final int wait = Random.nextInt(minDelay, maxDelay);
            sendKey(element, wait);
            if (wait > 0) {
                Task.sleep(wait);
            }
        }
        if (pressEnter) {
            sendKey((char) KeyEvent.VK_ENTER, Random.nextInt(minDelay, maxDelay));
        }
    }
}
