/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shehan.astar.shortestPathFinder;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 *
 * @author Shehan Vishwajith Galappaththi Guruge
 * Student Id : 2015297
 * UOW ID: W1628058(6)
 */

public class AStar {

    private List<Node> nodeList = new ArrayList<>();// holds all the nodes 
    private List<Node> closedList = new ArrayList<>();// holds the nodes which are visited
    private List<Node> openList = new ArrayList<>();// holds the nodes which are not visited yet
    private List<Node> path = new ArrayList<>();// holds the nodes which is the path from the start to end
    private List<Node> successorList = new ArrayList<>();// holds the neighbours of a node
    
    private int totalCostWithHeuristics; // used to calculate the total cost
    private Node startNode;// starting node 
    private Node endNode;// destination node
    private Node nodeCurrent;// node in which is pointing to
    private Node nodeUpperLeft;
    private Node nodeLowerLeft;
    private long startTime = 0;// records the current time in milliseconds in which this code start to operate
    private long endTime = 0;// records the time at the place of the path finding operation is finished
    private GUI cw;

    public AStar() {
        super();
        cw = new GUI();
    }

    /**
     * Set the list to the node list
     * @param nodeList 
     */
    public void setList(List<Node> nodeList) {
//        startTime = System.currentTimeMillis();//get the current time
        this.nodeList = nodeList;
    }
    
    /**
     * 
     * @param node type = Node
     * @param endX type = int
     * @param endY type = int
     * @param selectedIndex type = int
     */
    public void aStar(Node node, int endX, int endY, int selectedIndex) {
        startTime = System.currentTimeMillis();//get the current time
        this.startNode = node;// set the node to the start node
        this.startNode.setMovementCost(0);// set the gcost of the start node to 0
        this.endNode = new Node(endX, endY);// node object is created and x and y values are passed to the constructor
        int heuristicDistance = 0;

        heuristicDistance = calculateBasedOnDistanceMetrics(startNode, endX, endY, selectedIndex);//calculate the h cost

        this.startNode.setfValue(startNode.getMovementCost() + heuristicDistance);// set the f value by adding the gcost and the hcost

        openList.add(startNode);//add the node to the openlist

        while (!openList.isEmpty()) {// while open list os not empty
            
            nodeCurrent = openList.get(0);// current node is the value at 0 th index of the open list 
            
            for (int i = 1; i < openList.size(); i++) {

                if (openList.get(i).getfValue() < nodeCurrent.getfValue()) {// takes the node with the least f value in the open list
                    nodeCurrent = openList.get(i);
                }
            }
//            
            // if the destination node is found
            if (nodeCurrent.getxAxis() == endX && nodeCurrent.getyAxis() == endY) {
                
                
                endTime = System.currentTimeMillis();// take the current time
                Node endNode = new Node(endX, endY);
                traceBack(startNode);// calls the trace back method
                 
                //calculates the total cost from start to end
                for (Node n : path) {
                  
                    cw.colorCells(n.getxAxis(), n.getyAxis(), Color.RED);//color the path of the cells

                }

                totalCostWithHeuristics = path.get(0).getMovementCost() ;//total cost is caluclated
                double time = endTime - startTime;// time differenece
                System.out.println("Total cost from START node to END node : " + totalCostWithHeuristics );
                System.out.println("Time taken to find the shortest Path : " + time + " milliseconds ");
                break;
            }

            nodeSuccessors(nodeCurrent, endX, endY, selectedIndex);// get the neighbour nodes by passing the node current, endX,endY,and the selected index
            openList.remove(nodeCurrent);// remove the current node from the list
            closedList.add(nodeCurrent);// add the node current to the list
            boolean tf = false;

            for (Node successor : successorList) {
                // checks whether the neigbour node is in the closed list

                boolean tf1 = false;
                Node openNeighbour = new Node();// temp object to save the neoighbour in the open list
                for (Node n : openList) {
                    // if the neigbou node in the successor list save it to teh openNeighbour and set the tf to true
                    if (n.getxAxis() == successor.getxAxis() && n.getyAxis() == successor.getyAxis()) {
                        openNeighbour = n;
                        tf1 = true;
                        break;
                    }
                }
                
                //if it is not in the openlist add it to the open list
                if (tf1 == false) {
                    openList.add(successor);
                } else {
                    // if it is in the open list then check whether the movecost is less than the node in the open list
                    // if the g cost is less then set the movement cost and the parent
                    if (successor.getMovementCost() < openNeighbour.getMovementCost()) {
                        
                        openNeighbour.setMovementCost(successor.getMovementCost());
                        openNeighbour.setParentNode(successor.getParentNode());
                        openNeighbour.setfValue(successor.getfValue());
                    }
                }
            }


        }

    }

    /**
     * Get the neighbors for the current node
     * @param startNode type = Node
     * @param endX type = int
     * @param endY type = int
     * @param selectedIndex  type = int
     */
    public void nodeSuccessors(Node startNode, int endX, int endY, int selectedIndex) {
        successorList = new ArrayList<>();
        Node node;
        for (int i = 0; i < nodeList.size(); i++) {
            boolean tf = false;
            for (int j = 0; j < closedList.size(); j++) {
                if (closedList.get(j).getxAxis() == nodeList.get(i).getxAxis() && closedList.get(j).getyAxis() == nodeList.get(i).getyAxis()) {
                    tf = true;
                    break;
                }
            }

            // if the weight of the cell is not 10 and if it  is not in the closed list
            if (nodeList.get(i).getWeight() != 10 && !tf) {

                // UPPER NEIGHBOR
                if (startNode.getyAxis() != 0) {
                    if (nodeList.get(i).getxAxis() == startNode.getxAxis() && nodeList.get(i).getyAxis() == startNode.getyAxis() - 1) {
                        node = new Node(startNode.getxAxis(), startNode.getyAxis() - 1, nodeList.get(i).getWeight());
                        node.setHeuristicCost(calculateBasedOnDistanceMetrics(node, endX, endY, selectedIndex));
                        node.setMovementCost(startNode.getMovementCost() + nodeList.get(i).getWeight() + calculateBasedOnDistanceMetrics(startNode, startNode.getxAxis(), startNode.getyAxis() - 1, selectedIndex));
                        node.setfValue(node.getHeuristicCost() + node.getMovementCost());
                        node.setParentNode(startNode);
                        successorList.add(node);

                    }
                }

                //LOWER NEIGHBOR
                if (startNode.getyAxis() != 19) {//down
                    if (nodeList.get(i).getxAxis() == startNode.getxAxis() && nodeList.get(i).getyAxis() == startNode.getyAxis() + 1) {
                        node = new Node(startNode.getxAxis(), startNode.getyAxis() + 1, nodeList.get(i).getWeight());
                        node.setHeuristicCost(calculateBasedOnDistanceMetrics(node, endX, endY, selectedIndex));
                        node.setMovementCost(startNode.getMovementCost() + nodeList.get(i).getWeight() + calculateBasedOnDistanceMetrics(startNode, startNode.getxAxis(), startNode.getyAxis() + 1, selectedIndex));
                        node.setfValue(node.getHeuristicCost() + node.getMovementCost());
                        node.setParentNode(startNode);
                        successorList.add(node);
                    }
                }

                
                
                if (startNode.getxAxis() != 0) {//left
                    
                    // LEFT NEIGHBOR
                    if (nodeList.get(i).getxAxis() == startNode.getxAxis() - 1 && nodeList.get(i).getyAxis() == startNode.getyAxis()) {
                        node = new Node(startNode.getxAxis() - 1, startNode.getyAxis(), nodeList.get(i).getWeight());
                        node.setHeuristicCost(calculateBasedOnDistanceMetrics(node, endX, endY, selectedIndex));
                        node.setMovementCost(startNode.getMovementCost() + nodeList.get(i).getWeight() + calculateBasedOnDistanceMetrics(startNode, startNode.getxAxis() - 1, startNode.getyAxis(), selectedIndex));
                        node.setfValue(node.getHeuristicCost() + node.getMovementCost());
                        node.setParentNode(startNode);
                        successorList.add(node);
                        
                        //  UPPER LEFT
                    } else if (nodeList.get(i).getxAxis() == startNode.getxAxis() - 1 && nodeList.get(i).getyAxis() == startNode.getyAxis() - 1) {
                        nodeUpperLeft = new Node(startNode.getxAxis() - 1, startNode.getyAxis() - 1, nodeList.get(i).getWeight());
                        nodeUpperLeft.setHeuristicCost(calculateBasedOnDistanceMetrics(nodeUpperLeft, endX, endY, selectedIndex));
                        nodeUpperLeft.setMovementCost(startNode.getMovementCost() + nodeList.get(i).getWeight() + calculateBasedOnDistanceMetrics(startNode, startNode.getxAxis() - 1, startNode.getyAxis() - 1, selectedIndex));
                        nodeUpperLeft.setfValue(nodeUpperLeft.getHeuristicCost() + nodeUpperLeft.getMovementCost());
                        nodeUpperLeft.setParentNode(startNode);
                        successorList.add(nodeUpperLeft);
                        

                        //LOWER LEFT
                    } else if (nodeList.get(i).getxAxis() == startNode.getxAxis() - 1 && nodeList.get(i).getyAxis() == startNode.getyAxis() + 1) {
                        nodeLowerLeft = new Node(startNode.getxAxis() - 1, startNode.getyAxis() + 1, nodeList.get(i).getWeight());
                        nodeLowerLeft.setHeuristicCost(calculateBasedOnDistanceMetrics(nodeLowerLeft, endX, endY, selectedIndex));
                        nodeLowerLeft.setMovementCost(startNode.getMovementCost() + nodeList.get(i).getWeight() + calculateBasedOnDistanceMetrics(startNode, startNode.getxAxis() - 1, startNode.getyAxis() + 1, selectedIndex));
                        nodeLowerLeft.setfValue(nodeLowerLeft.getHeuristicCost() + nodeLowerLeft.getMovementCost());
                        nodeLowerLeft.setParentNode(startNode);
                        successorList.add(nodeLowerLeft);
                    }
                }

                if (startNode.getxAxis() != 19) {//right
                    
                    // RIGHT NEIGHBOR
                    if (nodeList.get(i).getxAxis() == startNode.getxAxis() + 1 && nodeList.get(i).getyAxis() == startNode.getyAxis()) {
                        node = new Node(startNode.getxAxis() + 1, startNode.getyAxis(), nodeList.get(i).getWeight());
                        node.setHeuristicCost(calculateBasedOnDistanceMetrics(node, endX, endY, selectedIndex));
                        node.setMovementCost(startNode.getMovementCost() + nodeList.get(i).getWeight() + calculateBasedOnDistanceMetrics(startNode, startNode.getxAxis() + 1, startNode.getyAxis(), selectedIndex));
                        node.setfValue(node.getHeuristicCost() + node.getMovementCost());
                        node.setParentNode(startNode);
                        successorList.add(node);
                        
                        // UPPER RIGHT NODE
                    } else if (nodeList.get(i).getxAxis() == startNode.getxAxis() + 1 && nodeList.get(i).getyAxis() == startNode.getyAxis() + 1) {
                        node = new Node(startNode.getxAxis() + 1, startNode.getyAxis() + 1, nodeList.get(i).getWeight());
                        node.setHeuristicCost(calculateBasedOnDistanceMetrics(node, endX, endY, selectedIndex));
                        node.setMovementCost(startNode.getMovementCost() + nodeList.get(i).getWeight() + calculateBasedOnDistanceMetrics(startNode, startNode.getxAxis() + 1, startNode.getyAxis() + 1, selectedIndex));
                        node.setfValue(node.getHeuristicCost() + node.getMovementCost());
                        node.setParentNode(startNode);
                        successorList.add(node);

                        // LOWER RIGHT NODE
                    } else if (nodeList.get(i).getxAxis() == startNode.getxAxis() + 1 && nodeList.get(i).getyAxis() == startNode.getyAxis() - 1) {
                        node = new Node(startNode.getxAxis() + 1, startNode.getyAxis() - 1, nodeList.get(i).getWeight());
                        node.setHeuristicCost(calculateBasedOnDistanceMetrics(node, endX, endY, selectedIndex));
                        node.setMovementCost(startNode.getMovementCost() + nodeList.get(i).getWeight());
                        node.setfValue(node.getHeuristicCost() + node.getMovementCost());
                        node.setParentNode(startNode);
                        successorList.add(node);
                        
                    }
                }
            }

        }
    }
    
    
    
    
    public int calculateManhattanDistance(Node node, int endX, int endY) {
        return Math.abs((node.getxAxis() - endX) + (node.getyAxis() - endY));
    }

    
    
    
    public int calculateEuclideanDistance(Node node, int endX, int endY) {
        int X = node.getxAxis() - endX;
        int Y = node.getyAxis() - endY;
        
        int squaredXY = (int) (Math.pow(X, 2) + Math.pow(Y, 2));
        return (int) Math.sqrt(squaredXY);
    }
    
    
    
    

    public int calculateChebyshevDistance(Node node, int endX, int endY) {
        return Math.max(Math.abs(node.getxAxis() - endX), Math.abs(node.getyAxis() - endY));
    }

    
    
    
    
    
    public int calculateBasedOnDistanceMetrics(Node node, int endX, int endY, int selectedIndex) {

        int heursiticCost = 0;
        switch (selectedIndex) {
            case 0:
                heursiticCost = calculateManhattanDistance(node, endX, endY);
                break;

            case 1:
                heursiticCost = calculateEuclideanDistance(node, endX, endY);
                break;

            case 2:
                heursiticCost = calculateChebyshevDistance(node, endX, endY);
                break;
        }

        return heursiticCost;
    }

    
    
    
    public void traceBack(Node startNode) {
        
        while (nodeCurrent != startNode) {
            path.add(nodeCurrent);
            nodeCurrent = nodeCurrent.getParentNode();
        }
        
    }

    
    
    public List<Node> getPathList() {
        return path;
    }

}
