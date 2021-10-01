package com.hoony;

@FunctionalInterface
public interface Calculator {
    public int cal(int num1, int num2);
    default int test(){

        return 1;
    };

    default String test2(){
        return "123";
    }
}
