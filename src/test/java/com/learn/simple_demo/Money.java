package com.learn.simple_demo;

public record Money(Integer x, String y) {
    public String newStr(){
        return x + y;
    }
}
