package com.singleton;

public class SingletonTest {

    public static void main(String[] args) {

        Logger logger1 = Logger.getInstance();

        Logger logger2 = Logger.getInstance();

        logger1.log("First Message");

        logger2.log("Second Message");

        System.out.println();

        System.out.println("Logger1 HashCode : " + logger1.hashCode());

        System.out.println("Logger2 HashCode : " + logger2.hashCode());

        if (logger1 == logger2) {
            System.out.println("Only one Logger instance exists.");
        } else {
            System.out.println("Multiple instances exist.");
        }

    }
}