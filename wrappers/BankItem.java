package com.infibot.client.api.wrappers;

import com.infibot.client.api.ActionState;
import com.infibot.client.api.methods.Bank;
import com.infibot.client.api.methods.Keyboard;
import com.infibot.client.utilities.time.Time;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Ian
 * Date: 7/21/13
 * Time: 11:11 PM
 */
public class BankItem extends Item {
    public BankItem(WidgetChild wc, int idx) {
        super(wc, idx);
    }
    public int getRow() {
        return (int) Math.floor(getIndex() / 8);
    }

    public int getColumn() {
        return getIndex() % 8;
    }

    private int getIndex() {
        BankItem[] items = Bank.getItems();
        for (int i = 0; i < items.length; i++) {
            if (items[i].getId() == getId()) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean isOnScreen() {
        return Bank.getVisibleBank().getArea().contains(getArea());
    }

    public boolean scrollTo() {
        if (Bank.isOpen()) {
            while (!isOnScreen()) {
                Bank.scroll(Bank.getPercentageScrolled() > getScrollingBounds()[1]);
            }
            Time.sleep(300, 500);
        }
        return isOnScreen();
    }

    public boolean withdraw(int count) {
        if (Bank.isOpen() && (isOnScreen() || scrollTo())) {
            String action = getString(count);
            if (interact(action)) {
                if (action.contains("X")) {
                    //TODO:Find a dynamic secure method for checking if the widgetchild pops up for the user to enter text
                    Time.sleep(900, 1500);
                    Keyboard.sendText("" + count, true);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public Point getScreenPoint() {
        Point point = super.getScreenPoint();
        point.translate(0, -getYDiff());
        return point;
    }

    private String getString(int count) {
        return ActionState.WITHDRAW.getString(count);
    }

    private int getYDiff() {
        int diff = Bank.getScrollingTranslation();
        int row = getRow();
        return row >= 0 && row <= 5 ? diff : diff - getArea().height;
    }

    private double[] getScrollingBounds() {
        int row = getRow();
        double one = row >= 0 && row <= 5 ? 0 : (row - 5) * 2;
        double two = row > 5 ? (one + 9.0) : row * 2;
        return new double[]{one, two};
    }
}
