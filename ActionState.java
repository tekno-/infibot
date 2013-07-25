package com.infibot.client.api;

/**
 * Created with IntelliJ IDEA.
 * User: Ian
 * Date: 7/21/13
 * Time: 11:24 PM
 */
public enum ActionState {
    WITHDRAW("Withdraw"), DEPOSIT("Store");
    private String action;

    ActionState(String action) {
        this.action = action;
    }

    public String getName() {
        return action;
    }

    public String getString(int count) {
        return new StringBuilder(action + " ").append(count == 1 ? "1" : count == 5 ? "5" : count == 10 ? "10" : count == 0 ? "All" : "X").toString();
    }
}
