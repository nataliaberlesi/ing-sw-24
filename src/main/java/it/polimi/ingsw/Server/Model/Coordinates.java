package it.polimi.ingsw.Server.Model;

public class Coordinates {

    private int x, y;

    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }
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
