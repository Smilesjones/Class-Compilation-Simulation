/*
Filename: InvalidClassNameException.java
Author: Stephen Jones
Date: 16DEC2018
Purpose: Project 4 class that defines the exception thrown when an invalid class
input is detected, an input value that does not correspond to any value that is 
included in the original text file.
 */

package classdependencygraphmain;


public class InvalidClassNameException extends Exception {

    //Constructors
    //Empty
    public InvalidClassNameException() {
    }
    //Includes string message from parent class
    public InvalidClassNameException(String msg) {
        super(msg);
    }
}
