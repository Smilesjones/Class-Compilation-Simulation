package classdependencygraphmain;

/*
Filename: ClassDependencyGraphMain.java
Author: Stephen Jones
Date: 16DEC2018
Purpose: Project 4 class that defines the GUI, creates a new DirectedGraph
object, and calls the doTopologicalOrder method of the new object depending on
the button clicked.  Catches exceptions InvalidClassNameException, 
FileNotFoundException, and CycleDetectedException 

References used to help create code:

 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;


public class ClassDependencyGraphMain extends JFrame implements ActionListener {
    
    //Variables
    private Border blackLine;
    private TitledBorder RecompileOrderBorder;
    private JLabel fileLabel, classLabel, emptyLabel; 
    private JTextField fileField, classField;
    private JButton buildButton, orderButton;
    private JTextArea recompileArea;
    private JPanel inputPanel, labelPanel, fieldPanel, buttonPanel;
    private DirectedGraph newGraph = null;
    
    //Constructor
    public ClassDependencyGraphMain(){
        
        //Borders
        blackLine = BorderFactory.createLineBorder(Color.BLACK);
        RecompileOrderBorder = BorderFactory.createTitledBorder(blackLine, 
                "Recompilation Order");
        
        //Labels
        fileLabel = new JLabel("  Input File Name:");
        classLabel = new JLabel("Class to Recompile:");
        emptyLabel = new JLabel("");//Used to space other labels
        
        //TextField
        fileField = new JTextField(15);
        classField = new JTextField(15);
        
        //Buttons
        buildButton = new JButton("Build Directed Graph");
        orderButton = new JButton("Topological Order");
        buildButton.addActionListener(this);
        orderButton.addActionListener(this);
        
        //TextArea
        recompileArea = new JTextArea(10,40);
        recompileArea.setEditable(false);
        recompileArea.setBorder(RecompileOrderBorder);
        
        //Panels
        inputPanel = new JPanel();
        //subPanels for the inputPanel
        labelPanel = new JPanel(new GridLayout(0,1));
        fieldPanel = new JPanel(new GridLayout(0,1));
        buttonPanel = new JPanel(new GridLayout(0,1));
        
        //Add compenents to the Panels
        labelPanel.add(fileLabel);
        labelPanel.add(emptyLabel);
        labelPanel.add(classLabel);
        
        fieldPanel.add(fileField);
        fieldPanel.add(classField);
        
        buttonPanel.add(buildButton);
        buttonPanel.add(orderButton);
        
        inputPanel.add(labelPanel);
        inputPanel.add(fieldPanel);
        inputPanel.add(buttonPanel);

        inputPanel.setBorder(blackLine);
        
        //Add main Panels to the Frame
        this.add(inputPanel, BorderLayout.NORTH);
        this.add(recompileArea, BorderLayout.SOUTH);
    }
    
    //Methods
    @Override
    public void actionPerformed(ActionEvent event) {
        //Reset the recompile area
        recompileArea.setText("");
        try{
            //If the buildButton is clicked
            if(event.getSource() == buildButton){
                //Instantiate new graph from input file only on the buildButton
                //click and success pane
                newGraph = new DirectedGraph(fileField.getText());
                JOptionPane.showMessageDialog(null, "Directed Graph Successfully Built",
                        "Success!", JOptionPane.INFORMATION_MESSAGE);
            }else{//Event source from the Top. Order button
                  //Call the Top. Order method of NewGraph
                recompileArea.setText("\n    " + 
                        newGraph.doTopologicalOrder(classField.getText()));
            }
        //Catches for multiple exceptions   
        }catch (InvalidClassNameException ie){
            JOptionPane.showMessageDialog(null, "Class Not Found.\n" + 
                    "Please Check Class Name.", 
                    "Class Name Error", JOptionPane.ERROR_MESSAGE);
        }catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "File Could Not Be Opened.\n" + 
                    "Please Check File Name.", 
                    "File Error", JOptionPane.ERROR_MESSAGE);
        }catch (CycleDetectedException ce){
            JOptionPane.showMessageDialog(null, "Cycle Detected In File Input.",
                    "Cycle Error", JOptionPane.ERROR_MESSAGE);
            }
    }
    
    //Method to set the frame characteristics
    public static void setFrame(JFrame frame){
        frame.setName("Class Dependency Graph");
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);    
    }
    //Main method
    public static void main(String[] args) {
        ClassDependencyGraphMain graphWindow = new ClassDependencyGraphMain();
        setFrame(graphWindow);
    }

    
    
}
