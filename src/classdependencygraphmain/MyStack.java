/*
******
Note:Using this class for Project 4, but created for Project 2
******
Filename: MyStack.java
Author: Stephen Jones
Date: 14NOV2018
Purpose: Project 2 class that defines the stack interface.

References used to help create code:

    1. LinkedStack.java. (n.d.). Retrieved November 16, 2018, from 
https://algs4.cs.princeton.edu/13stacks/LinkedStack.java.html

    2. Stacks. (n.d.). Retrieved November 16, 2018, from 
http://cs.lmu.edu/~ray/notes/stacks/

 */
package classdependencygraphmain;

import java.util.NoSuchElementException;

public interface MyStack<T> {
    
    void push(T element);
    T pop()throws NoSuchElementException;
    T peek()throws NoSuchElementException;
    int size();
    boolean isEmpty();
    
}
