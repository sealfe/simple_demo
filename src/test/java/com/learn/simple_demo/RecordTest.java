package com.learn.simple_demo;

import org.junit.jupiter.api.Test;

public class RecordTest {


    @Test
    void test_money(){
        Money newMoney = new Money(10, "new Money");
        System.out.println(newMoney.x());
        System.out.println(newMoney.y());
        System.out.println(newMoney.newStr());
    }





}
