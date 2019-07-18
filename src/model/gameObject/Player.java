package model.gameObject;

import java.util.ArrayList;

import static view.Commons.BOARD_WIDTH;
import static view.Commons.PLAYER_WIDTH;

public class Player extends Sprite {

    ArrayList<Attack> attacks;

    private final int START_X = (BOARD_WIDTH-PLAYER_WIDTH)/2;
    private final int START_Y = 410;
    private final int START_HEALTH = 100;

    private boolean walkRight = false;
    private boolean walkLeft = false;

    public Player(){
        initPlayer();
    }

    private void initPlayer() {
        setX(START_X);
        setY(START_Y);
        setDx(0);
        setHealth(START_HEALTH);

        attacks = new ArrayList<>();
    }

    //Add shot to Player
    public void addShot(int x, int y) {
        if(attacks.size() < 3) {
            Attack shot = new Attack(x, y);
            shot.setDestroyed(false);
            attacks.add(shot);
        }
    }

    //Attacks Getter
    public ArrayList<Attack> getAttacks() {
        return attacks;
    }

    //Use Boolean
    public boolean isWalkRight() {
        return walkRight;
    }

    public void setWalkRight(boolean walkRight) {
        this.walkRight = walkRight;
    }

    public boolean isWalkLeft() {
        return walkLeft;
    }

    public void setWalkLeft(boolean walkLeft) {
        this.walkLeft = walkLeft;
    }
}
