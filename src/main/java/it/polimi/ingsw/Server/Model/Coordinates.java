package it.polimi.ingsw.Server.Model;

/**
 * Represents a set of coordinates in a two-dimensional space.
 */
public class Coordinates {
    /**
     * Declare private variables to store x and y coordinates
     */
    private int x, y;

    /**
     * Constructs a new Coordinates object with specified x and y values.
     *
     * @param x The x-coordinate value.
     * @param y The y-coordinate value.
     */
    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }
    /**
     * Constructs a new Coordinates object with default coordinates set to (0, 0).
     */
    public Coordinates() {
        new Coordinates(0, 0);
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

}
