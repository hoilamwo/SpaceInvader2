package model.gameObject;

public class Attack {

    protected int x;
    protected int y;
    private boolean destroyed;

    public Attack(int x, int y) {
        initAttack(x, y);
    }

    public void initAttack(int x, int y) {
        setDestroyed(true);
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }
}
