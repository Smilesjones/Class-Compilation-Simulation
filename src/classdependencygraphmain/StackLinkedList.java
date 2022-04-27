
/*
******
Note:Using this class for Project 4, but created for Project 2
******
Filename: StackLinkedList.java
Author: Stephen Jones
Date: 14NOV2018
Purpose: Project 2 class that defines the stack linked list.

Note: Though "next" is commonly used in singly linked list and was used in the 
source code (reference 2) to represent the pointer to the next node in the list,
I used the word "prev" since that help me visualize the nature of a stack better
(the last node added would point to the previous last node that was added).  

References used to help create code:

    1. LinkedStack.java. (n.d.). Retrieved November 16, 2018, from 
https://algs4.cs.princeton.edu/13stacks/LinkedStack.java.html

    2. Stacks. (n.d.). Retrieved November 16, 2018, from 
http://cs.lmu.edu/~ray/notes/stacks/

 */

package classdependencygraphmain;



import java.util.NoSuchElementException;


public class StackLinkedList<T> implements MyStack<T>{
    
    //Inner Node class defining Node info and referencing the next Node
    private class Node {
        private T info;
        private Node prev;
        public Node(T info, Node prev) {
            this.info = info;
            this.prev = prev;
        }
    }
    //Variables
    private int num = 0;
    private Node top = null;
    
    //Methods
    //Add new item to top of stack
    public void push(T element) {
        Node prevTop = top;
        top = new Node(element, prevTop);
        num++;
    }
    //Remove the top item from the stack
    public T pop() throws NoSuchElementException {
        T element = peek();
        top = top.prev;
        num--;
        return element;
    }
    public void emptyStack()  {
        int size = num;
        for (int i = 0; i < size; i++){
            this.pop();
        }
    }
    
    public boolean isEmpty() {
        return top == null;
    }
    //Returns top nodes info without removal
    public T peek() throws NoSuchElementException {
        if (top == null) {
                throw new NoSuchElementException();
        }
        return top.info;
    }
    //Number of nodes in list
    public int size() {
        return num;
    }
   
}
