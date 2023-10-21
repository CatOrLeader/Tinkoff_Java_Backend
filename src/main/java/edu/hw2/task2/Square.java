package edu.hw2.task2;

public class Square extends Rectangle implements Shape {
    public Shape setSides(double side) {
        super.setWidth(side);
        super.setHeight(side);
        return this;
    }
}
