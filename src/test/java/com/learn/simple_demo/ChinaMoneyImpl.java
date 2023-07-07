package com.learn.simple_demo;

public class ChinaMoneyImpl extends ChinaMoney {
    public ChinaMoneyImpl(Integer x, String y) {
        super(x, y);
    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String toString() {
        return null;
    }
}
