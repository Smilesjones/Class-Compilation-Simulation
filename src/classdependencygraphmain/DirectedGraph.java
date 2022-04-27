/*
Filename: DirectedGraph.java
Author: Stephen Jones
Date: 16DEC2018
Purpose: Project 4 class that defines the directed graph including methods for 
the topological order and depth-first search.  Throws exceptions InvalidClassNameException, 
FileNotFoundException, and CycleDetectedException. 

References used to help create code:

1. Different ways of Reading a text file in Java. (2018, September 06). Retrieved 
    from https://www.geeksforgeeks.org/different-ways-reading-text-file-java/

2. Introduction to Graphs. (n.d.). Retrieved December 14, 2018, 
    from https://learn.umuc.edu/d2l/le/content/330288/viewContent/13092742/View

3. Morin, P. (n.d.). 12.2 AdjacencyLists: A Graph as a Collection of Lists. 
    Retrieved December 15, 2018, from 
    http://opendatastructures.org/ods-java/12_2_AdjacencyLists_Graph_a.html

4. Project 4. (n.d.). Retrieved December 14, 2018, from 
    https://learn.umuc.edu/d2l/le/dropbox/330288/697864/DownloadAttachment?fid=13068974
 */

package classdependencygraphmain;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Scanner;

public class DirectedGraph {
    
    //Variables
    private File inputFile;
    private Scanner inputData;
    private LinkedHashMap<Integer, String> classHashMap; 
    private ArrayList<LinkedList> vertices;
    private StackLinkedList<Integer> vertexStack; 
    private int classIndex = 0;
    private boolean []  discovered, finished;
    
    
    //Constructor
    public DirectedGraph(String aFile) throws FileNotFoundException{
        //Initialize the new directed graph 
        classHashMap = new LinkedHashMap<>();
        vertices = new ArrayList<>();
        inputFile = new File(aFile);
        inputData = new Scanner(inputFile);
        
        //Populating the linked hash map, array list, and linked lists with the 
        //strings of the input file
        while (inputData.hasNext()){//Idea from intro to java text
            String nextInput = inputData.nextLine();
            String [] eachLine = nextInput.split(" ");
            //populate the HashMap and vertices arrayList
            for (int i = 0; i < eachLine.length;i++){
                //Makes sure each hash map value is unique
                if(!classHashMap.containsValue(eachLine[i])){
                    classHashMap.put(classIndex, eachLine[i]);
                    //Add a new linked list to the array list for every hashmap
                    //entry.  Linked list index will correspond to hashMap key.
                    LinkedList<Integer> list = new LinkedList<>();
                    vertices.add(list);
                    classIndex++;
                }
            }
            //Part of the initilization that relates parent vertices (the first 
            //string of a line) to it's adjacent vertices (the other strings of
            //that line)
            //Loop through the values in the hash map
            for(int j = 0 ; j < classHashMap.size(); j++){
                //If a value in the hashMap equals the first string of a line 
                if (classHashMap.get(j).equals(eachLine[0])){
                    //Loop through the other strings in each line
                    for (int k = 1; k < eachLine.length; k++){
                        //Then loop through the values of the hash map again
                        for (int l = 0 ;l < classHashMap.size(); l++){
                            //When a hash map value equals the string in a line
                            if (classHashMap.get(l).equals(eachLine[k])){
                                //Assign the key of the hash map that corresponds to
                                //the string of the line to the index of the 
                                //array list that represents the first string of
                                //the line.
                                vertices.get(j).add(l);
                            }
                        }
                    }
                }
            }
        }
    }
    
    //Methods
    //Method that takes in initial vertex, initializes search elements, calls 
    //Depth-First Search and indexToString methods, and returns a String list
    //of the vertices of the graph that were "recompiled".
    public String doTopologicalOrder(String initialVertex) 
            throws InvalidClassNameException, CycleDetectedException{
        //Initialize vertex stack and boolean arrays
        //Will start with empty stack and all false booleans
        String recompiledOutput = "";
        vertexStack = new StackLinkedList<>();
        discovered = new boolean[classHashMap.size()];
        finished = new boolean[classHashMap.size()];
        //If the vertex entered is in the hash map populated from the input file.
        if (classHashMap.containsValue(initialVertex)){
            //Loop through the hash map values
            for(int i = 0; i < classHashMap.size(); i++){
                //When a entered value corresponds with a hash map value
                if(classHashMap.get(i).equals(initialVertex)){
                    //Call the depthFirstSearch method
                    depthFirstSearch(i);
                }
            }
        }else{//Throws exception if entered value is not in the hash map.
            throw new InvalidClassNameException();
        }
        //Variable that will be assigned to the final output string of 
        //recompiled class list
        recompiledOutput = indexToString(); 
        return recompiledOutput;
    }
    
    //Method that converts stack of integer indexes into the String of classes
    public String indexToString(){
        String verticesString = "";
        //Loop through both the stack and hash map to compare values
        for (int i = vertexStack.size() - 1; i >= 0; i--){
            for(int j = 0; j < classHashMap.size(); j++){
                //When the vertex stack is empty, break from the loop so a
                //NoSuchElementException is not thrown
                if(vertexStack.isEmpty()){
                    break;
                }
                //If the top stack integer value is equal to a hash map key
                if (vertexStack.peek().equals(j)){
                    //Pop the top integer value
                    vertexStack.pop();
                    //Add the string value of the hash map to the front of the 
                    //string of vertices/classes.  Reverses stack order.
                    verticesString = classHashMap.get(j) + " " + verticesString;
                }
            }
        }
        //return the final string of vertices/classes
        return verticesString;
    }
    
    //Depth-First Search method based on project 4 instructions pseudocode
    //Ref # 4
    public void depthFirstSearch(int initialVertexIndex) throws CycleDetectedException{
        //If a previously discovered vertex is passed again, throws an exception. 
        if (isDiscovered(initialVertexIndex)){
            throw new CycleDetectedException();
        }
        //If a finished vertex is passed again, returns from the method.
        if (isFinished(initialVertexIndex)){
            return;
        }
        //Only classes that are parents to child classes are discovered and 
        //thus will throw a cycle error if passed over more than once.
        //Classes with no child classes can be detected more than once 
        //without the threat of causing a cycle error.
        if(!vertices.get(initialVertexIndex).isEmpty()){
            makeDiscovered(initialVertexIndex);
        }
        //Push this vertex into the stack
        vertexStack.push(initialVertexIndex);
        //If this vertex has children to recompile
        if(!vertices.get(initialVertexIndex).isEmpty()){
            //Loop through all the children and recursively call method
            for(int i = 0; i < vertices.get(initialVertexIndex).size();i++){
                depthFirstSearch((int)vertices.get(initialVertexIndex).get(i));
            }
        }
        makeFinished(initialVertexIndex);
    }
    
    //Methods that return if a vertex is discovered and finished.
    public boolean isDiscovered(int vertexIndex){
        return discovered[vertexIndex];
    }
    public boolean isFinished(int vertexIndex){
        return finished[vertexIndex];
    }
    //Methods that make a vertex discovered and finished.
    public void makeDiscovered(int vertexIndex){
        discovered[vertexIndex] = true;
    }
    public void makeFinished(int vertexIndex){
        finished[vertexIndex] = true;
    }
    
    
    //Method for adding an edge based on Ref # 3
    //--Note--This is not used in the program, but specified in the instructions.
    public void addNewEdge(int parentVertex, int childVertex){
        vertices.get(parentVertex).add(childVertex);
    }
    
    @Override
    public String toString(){
        String output = "";
        for (int i = 0; i < vertices.size(); i++){
            if(!vertices.get(i).isEmpty()){
                output += vertices.get(i) + ", ";
            }
        }
        return output;
    }
}
