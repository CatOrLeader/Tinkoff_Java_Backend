package edu.project4;

public record Rect(double x, double y, double width, double height) {
    public boolean contains(Position position) {
        double posX = position.getX();
        double posY = position.getY();
        return (x <= posX && posX <= x + width) && (y <= posY && posY <= y + height);
    }
}
