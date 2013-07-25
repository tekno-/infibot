package com.infibot.client.api.util;

public abstract class Convertor<F, T> {
    public abstract T convert(F f);
    public static <K, V> V[] convert(K[] k, Convertor<K, V> convertor) {
        V[] v = (V[]) new Object[k.length];
        for(int i = 0; i < v.length - 1; i++) {
            v[i] = convertor.convert(k[i]);
        }
        return v;
    }
}
