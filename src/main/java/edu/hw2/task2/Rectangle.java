package edu.hw2.task2;

import org.apache.logging.log4j.LogManager;

public class Rectangle implements Shape {
    private double width;
    private double height;

    /**
     * This method returns an instance of Rectangle
     *
     * @param width new width of the shape
     * @return Shape instance
     */
    @Override public Shape setWidth(double width) {
        this.width = width;
        return this;
    }

    /**
     * This method returns an instance of Rectangle
     *
     * @param height new height of the shape
     * @return Shape instance
     */
    @Override public Shape setHeight(double height) {
        this.height = height;
        return this;
    }

    @Override public double area() {
        if (width == 0 || height == 0) {
            LogManager.getLogger().info("Your rectangle is not completed! One of the side (or both) equal to 0");
        }
        return width * height;
    }
}
