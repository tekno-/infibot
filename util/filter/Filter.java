package com.infibot.client.api.util.filter;

public interface Filter<T> {
    public boolean accept(T t);
}
