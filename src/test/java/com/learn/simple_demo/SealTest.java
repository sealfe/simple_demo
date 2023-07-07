package com.learn.simple_demo;

public sealed class SealTest permits NewSeal {
    public String getStr(){
        return "getStr";
    }
}
