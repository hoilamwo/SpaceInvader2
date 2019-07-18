package model.gameObject;

public class Minion extends Sprite {

    private Attack attack = new Attack(0,0);
    private final int START_HEALTH = 10;

    public Minion(int x, int y) {
        initMinion(x, y);
    }

    private void initMinion(int x, int y) {
        this.x = x;
        this.y = y;
        setHealth(START_HEALTH);
        this.attack.setDestroyed(true);
    }

    //Position Minion in horizontal direction
    public void move(int direction) {
        this.x += (direction);
    }

    public Attack getAttack() {
        return attack;
    }
}
