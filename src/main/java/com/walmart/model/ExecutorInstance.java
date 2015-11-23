package com.walmart.model;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorInstance{
    
    private static ExecutorInstance instance = null;
    public ExecutorService executor = Executors.newFixedThreadPool(5);
    
    private ExecutorInstance(){}
    
    public static ExecutorInstance getInstance(){
        if(instance == null) instance = new ExecutorInstance();
        return instance;
    }
    
}