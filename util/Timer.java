package com.infibot.client.api.util;

import com.infibot.client.utilities.time.Time;

/**
 * All long variables specified in this class are in milliseconds.
 */
public class Timer
{
    private long end;
    private final long period;
    private final long start;
    public Timer(long l) {
        start = System.currentTimeMillis();
        period = l;
        end = period + start;
    }

    public long setEndIn(long a) {
        return (end = System.currentTimeMillis() + a);
    }

    public String toElapsedString() {
        return Time.format(getElapsed());
    }

    public String toRemainingString() {
        return Time.format(getRemaining());
    }

    public void reset() {
        end = System.currentTimeMillis() + period;
    }

    public long getElapsed() {
        return System.currentTimeMillis() - start;
    }

    public long getRemaining() {
        return isRunning() ? end - System.currentTimeMillis() : 0;
    }

    public boolean isRunning() {
        return System.currentTimeMillis() <= end;
    }

}