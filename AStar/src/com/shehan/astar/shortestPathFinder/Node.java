/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shehan.astar.shortestPathFinder;

/**
 *
 * @author shehan Vishwajith Galappaththi Guruge
 * UOW ID: W1628058(6)
 * Student ID: 2015297
 */
public class Node {
    
    private int xAxis;
    private int yAxis;
    private int weight;
    private int heuristicCost;
    private int movementCost;
    private int fValue;
    private Node parentNode;
    
    /**
     * Default constructor initializes the values to 0
     */
    public Node(){
        super();
        this.xAxis = 0;
        this.yAxis = 0;
        this.weight = 0;
    }
    
    /**
     * Overloaded constructor sets the parameter values to the instance variables
     * @param xAxis type =  int
     * @param yAxis  type = int
     */
    public Node(int xAxis , int yAxis){
        super();
        this.xAxis = xAxis;
        this.yAxis = yAxis;
    }
    
    /**
     * Overloaded constructor sets the parameter values to the instance variables
     * @param xAxis type = int
     * @param yAxis type = int
     * @param weight type = int
     */
    public Node(int xAxis,int yAxis,int weight){
        super();
        this.xAxis = xAxis;
        this.yAxis = yAxis;
        this.weight = weight;
    }

    /**
     * Returns the X Value
     * @return type = int
     */
    public int getxAxis() {
        return xAxis;
    }

    /**
     * Sets the parameter value to the instance variable xAxis 
     * @param xAxis type = int
     */
    public void setxAxis(int xAxis) {
        this.xAxis = xAxis;
    }

    /**
     * Returns the Y Value
     * @return type = int
     */
    public int getyAxis() {
        return yAxis;
    }

    /**
     * Sets the parameter value to the instance variable yAxis 
     * @param yAxis type = int
     */
    public void setyAxis(int yAxis) {
        this.yAxis = yAxis;
    }

    /**
     * Returns the Weight
     * @return type = int
     */
    public int getWeight() {
        return weight;
    }

    /**
     * Sets the parameter value to the instance variable weight 
     * @param weight type = int
     */
    public void setWeight(int weight) {
        this.weight = weight;
    }

    /**
     * Returns the heuristicCost
     * @return type = int
     */
    public int getHeuristicCost() {
        return heuristicCost;
    }

    /**
     * Sets the parameter value to the instance variable heuristicCost 
     * @param heuristicCost type = int
     */
    public void setHeuristicCost(int heuristicCost) {
        this.heuristicCost = heuristicCost;
    }

    /**
     * Returns the movement cost or g cost
     * @return type = int
     */
    public int getMovementCost() {
        return movementCost;
    }

    /**
     * Sets the parameter value to the instance variable movementCost 
     * @param movementCost type = int
     */
    public void setMovementCost(int movementCost) {
        this.movementCost = movementCost;
    }

    /**
     * Returns the F Value
     * @return type = int
     */
    public int getfValue() {
        return fValue;
    }

    
    /**
     * Sets the parameter value to the instance variable fValue 
     * @param fValue type = int
     */
    public void setfValue(int fValue) {
        this.fValue = fValue;
    }
    
    /**
     * Sets the parameter value to the instance variable parentNode 
     * @param parentNode type = Node
     */
    public void setParentNode(Node parentNode){
        this.parentNode  = parentNode;
    }
    
    /**
     * Returns the parent node
     * @return type = Node
     */
    public Node getParentNode(){
        return this.parentNode;
    }
    
    @Override
    public String toString(){
        return "x =  "+getxAxis()+ " y =  "+getyAxis()+ " movement cost: " + getMovementCost();
//        +" heuristic cost = " +getHeuristicCost()+" movementcost = "+getMovementCost()+" f cost = "+getfValue()
    }
    
}
