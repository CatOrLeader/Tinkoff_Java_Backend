package edu.hw2.task2;

public class Square extends Rectangle implements Shape {
    /**
     * This method returns an instance of Square
     * @param width new width of the shape
     * @return Shape instance
     */
    @Override
    public Square setWidth(double width) {
        super.setHeight(width);
        super.setWidth(width);
        return this;
    }

    /**
     * This method returns an instance of Square
     * @param height new height of the shape
     * @return Shape instance
     */
    @Override
    public Square setHeight(double height) {
        super.setHeight(height);
        super.setWidth(height);
        return this;
    }
}
