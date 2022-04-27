/*
Filename: CycleDetectedException.java
Author: Stephen Jones
Date: 16DEC2018
Purpose: Project 4 class that defines the exception thrown when a cycle is 
detected.
 */

package classdependencygraphmain;


public class CycleDetectedException extends Exception {
    //Constructors
    //Empty 
    public CycleDetectedException() {
    }
    //Includes string message from parent class
    public CycleDetectedException(String msg) {
        super(msg);
    }
}
