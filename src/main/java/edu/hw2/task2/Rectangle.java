package edu.hw2.task2;

public class Rectangle implements Shape {
    private double width;
    private double height;

    /**
     * This method returns an instance of Rectangle
     * @param width new width of the shape
     * @return Shape instance
     */
    @Override public Rectangle setWidth(double width) {
        this.width = width;
        return this;
    }

    /**
     * This method returns an instance of Rectangle
     * @param height new height of the shape
     * @return Shape instance
     */
    @Override public Shape setHeight(double height) {
        this.height = height;
        return this;
    }

    @Override
    public double area() {
        return width * height;
    }
}
