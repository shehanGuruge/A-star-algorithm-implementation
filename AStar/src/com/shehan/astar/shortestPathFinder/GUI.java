
package com.shehan.astar.shortestPathFinder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

/**
 *
 * @author Shehan Vishwajith Galappaththi Guruge
 * Student Id : 2015297
 * UOW ID: W1628058(6)
 */


public class GUI {

    private static JButton[][] labelSquares = new JButton[20][20]; //Button objects for showing displaying the images (20 * 20)
    
    /*
    Below elements are foe creating the gui.All are set to private
    */    
    private static JPanel panel;
    private static JButton btnSubmit;
    private static JTextField txtStartX;
    private static JTextField txtStartY;
    private static JTextField txtEndX;
    private static JTextField txtEndY;
    private JFrame frame;
    private static Container container = new Container();
    
   /*
     4 string arrays for storing the coordinates of the GUI [x,y].
    
        1. Array for storing the gui coordinates of Rocks or darkest Gray coordinates
        2. Array for storing the gui coordinates of Water which is black cells.
        3. Array for storing the gui coordinates of Trees or light Gray Coordinates.
        4. Array for storing the gui coordinates of Bushes or lightest Gray Coordinates.
    
    */
    private static final String[] ROCKGUICOORDINATES = {"0,1", "0,2", "0,3", "0,6", "0,13", "1,1", "1,2", "1,3", "1,5", "1,13", "2,2", "2,3",
        "2,4", "2,5", "2,12", "2,13", "2,14", "3,1", "3,2", "3,3", "3,12", "3,13", "3,14", "4,2", "4,3", "4,12", "4,13", "4,14", "5,2", "5,3", "5,12", "5,13", "5,14",
        "6,11", "6,12", "6,13", "7,11", "7,12", "7,13", "8,10", "8,11", "8,12", "8,13", "9,11", "9,12", "12,9", "12,10", "12,11", "13,9", "13,10", "13,11",
        "14,9", "14,10", "14,11", "15,9", "15,10", "16,9", "17,8", "17,9", "18,7", "18,8", "18,9", "19,7", "19,8"};

    private static final String[] WATERGUICOORDINATES = {"11,13", "11,14", "11,18", "11,19", "12,14", "12,15", "12,18", "12,19", "13,15", "13,16", "13,17",
        "13,18", "13,19", "14,15", "14,16", "14,17", "14,18", "14,19", "15,16", "15,17", "15,18", "15,19", "16,16", "16,17", "16,18", "16,19",
        "17,15", "17,16", "17,17", "17,18", "17,19", "18,15", "18,16", "18,17", "18,18", "18,19", "19,13", "19,14", "19,15", "19,16", "19,17", "19,18", "19,19"};

    private static final String[] TREEGUICOORDINATES = {"2,9", "2,10", "3,8", "3,9", "4,8", "4,9", "5,9", "6,10",
        "9,2", "9,3", "10,2", "10,3", "10,4", "11,2", "11,3", "11,4", "12,3", "12,4"};

    private static final String[] BUSHESGUICOORDINATES = {"0,17", "1,6", "1,7", "1,9", "1,10", "1,11", "1,16", "1,17", "2,8", "2,11",
        "2,16", "3,7", "3,10", "3,16", "4,5", "4,6", "4,10", "4,16", "5,5", "5,6", "5,8", "5,10", "5,16", "6,8", "6,9", "7,1", "7,9", "7,10", "8,1", "8,2", "8,3", "8,4",
        "9,1", "9,4", "9,5", "9,14", "9,16", "10,0", "10,1", "10,5", "10,14", "10,15", "11,1", "11,5", "12,0", "12,1", "12,2", "12,5", "13,3", "13,4", "13,5", "15,5", "15,6",
        "16,4", "16,5", "16,6", "17,4", "17,5"};

    
    
    private static List<Node> pathList = new ArrayList<>(); //list for storing all the nodes 
    private static List<Node> notViablePathList = new ArrayList<>(); // list for storing the nodes in which the user can't travel which is black cells
    private Node path; // a node object for the purpose of storing the nodes with their x,y and weight 
    
    private static final String[] HEURISTIC_TYPE = {"Manhattan", "Euclidean", "Chebyshev"};// a String array to store the 3 types of heuristics type. Set final as it only read from and never written to
   
    /*
    Weights of cells with different shades is stored
    */
    private static final int ROCK_WEIGHT = 4;
    private static final int TREE_WEIGHT = 3;
    private static final int BUSH_WEIGHT = 2;
    private static final int WATER_WEIGHT = 10;

    private static AStar findPath; // object of FindPath class is created
    private List<Node> finalPathList = new ArrayList<>();// ArrayList of nodes is created here.
    private List<String> startAndEndXYCoordinates = new ArrayList<>(); //for storing the starting point X and Y coordinates

    
    
    public static void main(String[] args) {
        GUI obj = new GUI();
        obj.createGUI();// Calling the createGUI method to display the GUI by using a object of this class
    }

    
    /**
     * 
     * @param startX : int X Value of the starting point
     * @param startY : int Y Value of the starting point
     * @param endX : int X Value of the ending point
     * @param endY : int Y Value of the destination point
     * @return : boolean Value
     * Takes the entered values and checks whether those values are less than 0 or not
     */
    public static boolean checkUserInputs(int startX, int startY, int endX, int endY) {
        boolean isNegative = false;

        if (startX < 0 || startY < 0 || endX < 0 || endY < 0) {
            isNegative = true;
        }

        return isNegative;
    }

    
    
    /**
     * 
     * @param startX : int X Value of the starting point
     * @param startY : int Y Value of the starting point
     * @param endX : int X Value of the ending point
     * @param endY : int Y Value of the destination point
     * @return : boolean Value
     * Takes the values entered and check whether those values are greater than 19 or not
     */
    public static boolean checkTheMaximumLimit(int startX, int startY, int endX, int endY) {
        boolean isOutOfRange = false;

        if (startX > 19 || startY > 19 || endX > 19 || endY > 19) {
            isOutOfRange = true;
        }

        return isOutOfRange;
    }

    
    /**
     * This method is responsible for creating and designing the GUI elements
     */
    public void createGUI() {

        frame = new JFrame("Shortest Path Finding");
        panel = new JPanel();
        btnSubmit = new JButton("SUBMIT");
        btnSubmit.setBackground(Color.GREEN);
        JButton btnNew = new JButton("NEW");

        txtStartX = new JTextField();
        txtStartY = new JTextField();
        txtEndY = new JTextField();
        txtEndX = new JTextField();

        Container controlsContainer = new Container();
        JLabel label1 = new JLabel("Select the heuristic type: ");
        JComboBox cmbHeursitic = new JComboBox(HEURISTIC_TYPE);
        controlsContainer.setLayout(new BoxLayout(controlsContainer, BoxLayout.Y_AXIS));
        controlsContainer.add(label1);
        controlsContainer.add(cmbHeursitic);

        Container container2 = new Container();
        container2.setLayout(new BoxLayout(container2, BoxLayout.X_AXIS));
        container2.add(new JLabel("Starting point coordinates: [x , y]"));
        container2.add(txtStartX);
        container2.add(txtStartY);
        controlsContainer.add(container2);

        Container btnContainer = new Container();
        btnContainer.setLayout(new BoxLayout(btnContainer, BoxLayout.X_AXIS));
        btnContainer.add(btnNew);
        btnContainer.add(btnSubmit);

        Container container3 = new Container();
        container3.setLayout(new BoxLayout(container3, BoxLayout.X_AXIS));
        container3.add(new JLabel("Ending point coordinates:  [x , y]"));
        container3.add(txtEndX);
        container3.add(txtEndY);
        controlsContainer.add(container3);
        controlsContainer.add(btnContainer);

        container.setLayout(new GridLayout(20, 20));
        colorMovePath();
        getCoordinates(Color.DARK_GRAY, ROCKGUICOORDINATES, ROCK_WEIGHT);
        getCoordinates(Color.GRAY, TREEGUICOORDINATES, TREE_WEIGHT);
        getCoordinates(Color.BLACK, WATERGUICOORDINATES, WATER_WEIGHT);
        getCoordinates(new Color(214, 214, 194), BUSHESGUICOORDINATES, BUSH_WEIGHT);//new Color(214, 214, 194)

        panel.setLayout(new BorderLayout());
        panel.add(container, BorderLayout.CENTER);
        panel.add(controlsContainer, BorderLayout.AFTER_LAST_LINE);
        frame.add(panel);
        frame.setSize(700, 700);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);

        btnSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                long time = System.currentTimeMillis();
                try {

                    int startX = Integer.parseInt(txtStartX.getText());
                    int startY = Integer.parseInt(txtStartY.getText());
                    int endX = Integer.parseInt(txtEndX.getText());
                    int endY = Integer.parseInt(txtEndY.getText());

                    startAndEndXYCoordinates.add(Integer.toString(startX));
                    startAndEndXYCoordinates.add(Integer.toString(startY));
                    startAndEndXYCoordinates.add(Integer.toString(endX));
                    startAndEndXYCoordinates.add(Integer.toString(endY));

                    if (!checkUserInputs(startX, startY, endX, endY)) {

                        if (!checkTheMaximumLimit(startX, startY, endX, endY)) {

                            findPath = new AStar();
                            findPath.setList(pathList);
                            if (checkWaterExistence(notViablePathList, startX, startY, endX, endY)) {

                                JOptionPane.showMessageDialog(null, "Path is blocked.Please try another coordinate");

                            } else {
                                labelSquares[startX][startY].setBackground(Color.BLUE);
                                for (Node node : pathList) {
                                    if (node.getxAxis() == startX && node.getyAxis() == startY) {

                                        findPath.aStar(node, endX, endY, cmbHeursitic.getSelectedIndex());

                                        
                                        long time2 = System.currentTimeMillis();
                                        System.out.println("Time taken to update the gui and find the shortest path: " + (time2 - time));
                                        break;
                                    }
                                }

                                labelSquares[endX][endY].setBackground(Color.BLUE);
                            }

                        } else {
                            JOptionPane.showMessageDialog(null, "The maximum input number should be not more than 19");

                        }

                    } else {

                        JOptionPane.showMessageDialog(null, "Enter only positive Integers");

                    }
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(null, "Please enter numbers only");
                }
            }
        });

        btnNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                try{
                finalPathList = findPath.getPathList();
                
                for (Node nodeBeginning : pathList) {

                    for (Node node : finalPathList) {

                        if (nodeBeginning.getxAxis() == Integer.parseInt(startAndEndXYCoordinates.get(0)) && nodeBeginning.getyAxis() == Integer.parseInt(startAndEndXYCoordinates.get(1))) {
//                            System.out.println(startAndEndXYCoordinates.get(0) + " " + startAndEndXYCoordinates.get(1));
                            labelSquares[nodeBeginning.getxAxis()][nodeBeginning.getyAxis()].setBackground(Color.WHITE);
                            int weight = nodeBeginning.getWeight();

                            if (weight == 1) {
                                labelSquares[nodeBeginning.getxAxis()][nodeBeginning.getyAxis()].setBackground(Color.WHITE);
                            } else if (weight == 2) {
                                labelSquares[nodeBeginning.getxAxis()][nodeBeginning.getyAxis()].setBackground(new Color(214, 214, 194));
                            } else if (weight == 3) {
                                labelSquares[nodeBeginning.getxAxis()][nodeBeginning.getyAxis()].setBackground(Color.GRAY);
                            } else if (weight == 4) {
                                labelSquares[nodeBeginning.getxAxis()][nodeBeginning.getyAxis()].setBackground(Color.DARK_GRAY);
                            }
                        }

                        if (nodeBeginning.getxAxis() == node.getxAxis() && nodeBeginning.getyAxis() == node.getyAxis()) {
                            int weight = nodeBeginning.getWeight();

                            if (weight == 1) {
                                labelSquares[nodeBeginning.getxAxis()][nodeBeginning.getyAxis()].setBackground(Color.WHITE);
                            } else if (weight == 2) {
                                labelSquares[nodeBeginning.getxAxis()][nodeBeginning.getyAxis()].setBackground(new Color(214, 214, 194));
                            } else if (weight == 3) {
                                labelSquares[nodeBeginning.getxAxis()][nodeBeginning.getyAxis()].setBackground(Color.GRAY);
                            } else if (weight == 4) {
                                labelSquares[nodeBeginning.getxAxis()][nodeBeginning.getyAxis()].setBackground(Color.DARK_GRAY);
                            }
                        }
                    }

                }
                }catch(Exception exc){
                    
                }

                txtEndX.setText("");
                txtEndY.setText("");
                txtStartX.setText("");
                txtStartY.setText("");
                startAndEndXYCoordinates = new ArrayList<>();
            }
        });

    }

    
    
    /**
     *  This method contains the code for creating the buttons, setting button properties, creating nodes and adding nodes to the array list
     *  Please Note : i = Y coordinate, j = X coordinate
     */
    public void colorMovePath() {
        for (int i = 0; i < 20; i++) {// i = y
            for (int j = 0; j < 20; j++) {// j = x
                labelSquares[j][i] = new JButton(); // Create the button objects
                labelSquares[j][i].setEnabled(false);// Buttons are not clickable
                labelSquares[j][i].setBackground(Color.WHITE);// Default color: WHITE
                
                // if you want to see the coordinates uncomment this line
//                labelSquares[j][i].setText(j + " " + i);// display the X and Y coordinates on the buttons
                
                container.add(labelSquares[j][i]);// add the button to the container
                path = new Node(i, j, 1);// create a node object and pass the x,y and 1 as the weight 
                pathList.add(path);// add the created node to the array list
            }
        }
    }

    
    /**
     * 
     * @return type = Node : the array list of Nodes
     */
    public List<Node> returnPathList() {
        return pathList;
    }

    /**
     * 
     * @param color type = Color 
     * @param GUIElement type = Array[String]
     * @param weight type : int
     * Takes the weights of the GUI elements by passing the GUI coordinate arrays as parameters
     */
    public void getCoordinates(Color color, String[] GUIElement, int weight) {
        
        for (int i = 0; i < GUIElement.length; i++) {
            
            String coordinate = GUIElement[i]; //a temporary variable to store the current element in the array
            String[] splitArray = coordinate.split(",");// a temporary array to store the splitted text of the temp variable coordinate
            colorElements(Integer.parseInt(splitArray[0]), Integer.parseInt(splitArray[1]), color);// call the color elements and pass the array elements as the parameters
            
            /*
            * for loop until the correct node is found.
            * takes the current element of the pathList arraylist and check whether their x and y values are equal to the splitted array values
            * if the condition is true then set the weight and breaks the loop
            */
            for (int index = 0; index < pathList.size(); index++) {
                if (pathList.get(index).getxAxis() == Integer.parseInt(splitArray[0]) && pathList.get(index).getyAxis() == Integer.parseInt(splitArray[1])) {
                    pathList.get(index).setWeight(weight);
                    break;
                }
            }
            
            // if the weight is 10 then add the node to notViable path list
            if (weight == 10) {
                notViablePathList.add(path);
            }
        }
    }

    
    
    
    /**
     * 
     * @param x type = int
     * @param y type = int
     * @param color type = color
     */
    public void colorElements(int x, int y, Color color) {
        labelSquares[x][y].setBackground(color);// set the background color of the buttons at x and y coordinates with the color send as the parameter
    }
    
    
    
    
    /**
     * 
     * @param nonViablePathList type = List<Node>
     * @param startX type = int
     * @param startY type = int
     * @param endX type = int
     * @param endY type =int
     * @return type = boolean
     */
    public boolean checkWaterExistence(List<Node> nonViablePathList, int startX, int startY, int endX, int endY) {
        
        for (Node pathObj : pathList) {
            /*
            * if the current object of the pathList's X value is equals to starting or ending point X axis or the 
            * Y value equals to starting or ending point Y axis Values or
            * if the weights are same then return true, else return false
            */
            if ((pathObj.getxAxis() == startX && pathObj.getyAxis() == startY && pathObj.getWeight() == 10)
                    || (pathObj.getxAxis() == endX && pathObj.getyAxis() == endY && pathObj.getWeight() == 10)) {
                return true;
            }
        }
        return false;
    }

    
    
    
    /**
     * 
     * @param x type = int
     * @param y type = int
     * @param color type = Color
     */
    public void colorCells(int x, int y, Color color) {
        labelSquares[x][y].setBackground(color);
        labelSquares[x][y].setText(x + " " + y + "");// set the background color of the buttons at x and y coordinates with the color send as the parameter
    }

    
    
    
    /**
     * 
     * @param finalPathList type = List<Node>
     * set the final path list
     */
    public void setPathList(List<Node> finalPathList) {
        finalPathList = new ArrayList<>();
        this.finalPathList = finalPathList;
    }
}
