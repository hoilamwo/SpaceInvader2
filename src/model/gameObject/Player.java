package model.gameObject;

import java.util.ArrayList;

import static view.Commons.*;

public class Player extends Sprite {

    ArrayList<PlayerAttack> attacks;

    private boolean walkRight = false;
    private boolean walkLeft = false;

    private int shotFrame = 0;
    private int Attack_Speed = 5;

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

    //Player's own Attack Class
    public class PlayerAttack extends Attack {

        private int shotFrame = 0;

        public PlayerAttack(int x, int y, int shotFrame) {
            super(x, y);
            this.shotFrame = shotFrame;
        }

        public int getShotFrame() {
            return shotFrame;
        }
    }

    //Add shot to Player
    public void addShot(int x, int y, int frame) {
        if(attacks.size() < 2) {
            PlayerAttack shot = new PlayerAttack(x + (PLAYER_WIDTH - SHOT_WIDTH)/2, y, frame);
            shot.setDestroyed(false);
            attacks.add(shot);

            if(shotFrame==5){
                shotFrame = 0;
            } else {
                shotFrame++;
            }
        }
    }


    //Attacks Getter
    public ArrayList<PlayerAttack> getAttacks() {
        return attacks;
    }

    //Getter and Setter For Attack Speed
    public int getAttack_Speed() {
        return Attack_Speed;
    }
    public void setAttack_Speed(int attack_Speed) {
        Attack_Speed = attack_Speed;
    }


    public int getShotFrame() {
        return shotFrame;
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
