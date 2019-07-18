package model.gameObject;

public class Sprite {

    protected int x;
    protected int y;
    protected boolean alive;
    protected int health;

    protected int dx;

    public Sprite() {
        alive = true;
    }

    public void die() {
        this.alive = false;
    }

    public boolean isAlive() {
        return this.alive;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health += health;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public int getDx() {
        return this.dx;
    }

}
