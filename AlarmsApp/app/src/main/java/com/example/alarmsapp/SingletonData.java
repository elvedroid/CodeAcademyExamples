package com.example.alarmsapp;

public class SingletonData {
    private int counter = 0;

    private static SingletonData INSTANCE;

    public static SingletonData getInstance() {

        if (INSTANCE == null) {
            INSTANCE = new SingletonData();
        }

        return INSTANCE;
    }

    public int getCounter() {
        return counter;
    }

    public void incrementCounter(){
        this.counter++;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
}
