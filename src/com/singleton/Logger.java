package com.singleton;
public class Logger {
    // Static instance of Logger
    private static Logger instance;

    // Private constructor
    private Logger() {
        System.out.println("Logger Instance Created");
    }
    // Public method to return single instance
    public static Logger getInstance() {

        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }
    // Logging method
    public void log(String message) {
        System.out.println("LOG: " + message);
    }
}