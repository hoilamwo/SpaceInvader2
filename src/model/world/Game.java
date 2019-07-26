package model.world;

import model.gameObject.Attack;
import model.gameObject.Minion;
import model.gameObject.Player;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

import static view.Commons.*;
import static view.Commons.MINION_INIT_Y;

public class Game{

    private boolean inGame;
    private Dimension d;

    private Player player;

    //Minions's moving direction
    private int direction = MINION_DIRECTION;

    private ArrayList<Minion> minions = new ArrayList<>();
    //Number of Minions alive
    private int minionAlive = 0;

    //Frame
    private int minionFrame = 4;
    private int musicFrame = 0;
    private int playerFrame = 0;
    private int baseFrame = 2;

    private String message = "Game Over! You Lost :( ";

    Random generator = new Random();

    private Timer timer;

    public Game() {
        initGame();
    }

    private void initGame() {
        d = new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
        addMinions();
        this.player = new Player();
        this.inGame = true;

        //Timer
        timer = new Timer();
        timer.schedule(new updateMinionFrame(),0,MINION_SPEED);

    }

    //Add Minions to minions
    public void addMinions() {
        for(int i = 0; i < 3; i++){
            for(int j = 0; j <11; j++) {
                Minion minion = new Minion(MINION_INIT_X + (MINION_WIDTH + 13) * j, MINION_INIT_Y + (MINION_HEIGHT + 13) * i);
                minions.add(minion);
            }
        }
        minionAlive = minions.size();
    }

    /*
        BIG UPDATE
     */
    public void update() {

        //Set inGame to false if player is not alive
        if(!player.isAlive()){
            this.inGame = false;
        }

        //Set inGame to false if player destroyed all minions
        if(minionAlive <= 0) {
            setInGame(false);
            message = "Game Over! You Won! :) ";
        }

        updatePlayer();
        updateMinion();
        updatePlayerAttack();

    }

    //Update Player
    public void updatePlayer() {

        //Check whether player is alive or not
        if(player.getHealth()<=0){
            player.die();
        }

        //Move player
        if(player.isWalkLeft()){
            player.setDx(PLAYER_SPEED*-1);
            player.setX(player.getX() + player.getDx());
            playerFrame = 1;
        }

        if(player.isWalkRight()){
            player.setDx(PLAYER_SPEED);
            player.setX(player.getX() + player.getDx());
            playerFrame = 2;
        }

        if(!player.isWalkRight() && !player.isWalkLeft()) {
            playerFrame = 0;
        }
        //Check Player Left Boundary
        if (player.getX()<= 5) {
            player.setX(5);
        }
        //Check {Player Right Boundary
        if (player.getX()>= BOARD_WIDTH-PLAYER_WIDTH*2+PLAYER_WIDTH/2-5) {
            player.setX(BOARD_WIDTH-PLAYER_WIDTH*2+PLAYER_WIDTH/2-5);
        }

    }

    //Update Player Attack
    public void updatePlayerAttack() {
        ArrayList<Player.PlayerAttack> playerAttack = player.getAttacks();

        for(Iterator<Player.PlayerAttack> it = playerAttack.iterator(); it.hasNext(); ){
            Attack shot = it.next();

            int shotY = shot.getY();

            //Shot velocity
            int newY = shotY-player.getAttack_Speed();

            //Check Top Boundary
            if(shotY <= 0) {
                shot.setDestroyed(true);
            } else {
                shot.setY(newY);
            }

            //Check collision with minions
            if(!shot.isDestroyed()) {
                for(Minion m: minions){
                    //If shot hits minion, minion dies and shot is destroyed
                    if(m.isAlive()){
                        Rectangle r = new Rectangle(m.getX(),m.getY(),MINION_WIDTH,MINION_HEIGHT);
                        Rectangle p = new Rectangle(shot.getX(),shot.getY(),SHOT_WIDTH,SHOT_HEIGHT);

                        if (r.intersects(p)) {
                            m.die();
                            shot.setDestroyed(true);
                            minionAlive--;
                        }
                    }
                }
            }
        }

        //Remove shot from player's attackArray if shot is destroyed
        playerAttack.removeIf(shot -> shot.isDestroyed());
    }

    //Update Minion
    public void updateMinion() {

        //Minion loop
        for(Minion m: minions) {

            //Update minion attack
            updateMinionAttack(m);

            int x = m.getX();

            //Check Bottom Boundary
            if(m.getY()+ MINION_HEIGHT >= START_Y+20){
                this.inGame = false;
            }

            //Check Right Boundary
            if(x >= BOARD_WIDTH-MINION_WIDTH-20 && direction > 0){
                direction = direction*-1;
                Iterator it = minions.iterator();
                while (it.hasNext()) {
                    Minion minion = (Minion) it.next();
                    minion.setY(minion.getY()+MINION_GO_DOWN);
                }
                minionFrame = 0;
            }

            //Check Left Boundary
            if(x <= 0 && direction < 0) {
                direction = direction*-1;
                Iterator it = minions.iterator();
                while (it.hasNext()) {
                    Minion minion = (Minion) it.next();
                    minion.setY(minion.getY()+MINION_GO_DOWN);
                }
                minionFrame = 4;
            }
        }
    }

    //Update Minion Attack
    public void updateMinionAttack(Minion m) {
        int r = generator.nextInt(1000);
        Attack attack = m.getAttack();

        int attackX = attack.getX();
        int attackY = attack.getY();

        int playerX = player.getX();
        int playerY = player.getY();

        //Check if Minion is lucky and alive and whether attack is destroyed
        //Set attack to not destroyed
        //Set x, y position of attack to minion position
        if(r == 1 && m.isAlive() && attack.isDestroyed()) {
            attack.setDestroyed(false);
            attack.setX(m.getX()+MINION_WIDTH/2);
            attack.setY(m.getY()+MINION_HEIGHT/2);
        }

        //If attack is not destroy
        //Keep moving down
        //Set destroyed if attack is greater than START_Y+20
        if(!attack.isDestroyed()) {
            //attack speed
            attack.setY(attack.getY() + MINION_ATTACK_SPEED);
            if(attack.getY() >= BOARD_HEIGHT) {
                attack.setDestroyed(true);
            }
            if(player.isAlive()){
                Rectangle rm = new Rectangle(attack.getX(),attack.getY(),MINION_ATTACK_WIDTH,MINION_ATTACK_HEIGHT);
                Rectangle rp = new Rectangle(player.getX(),player.getY(),PLAYER_WIDTH,PLAYER_HEIGHT);

                if (rm.intersects(rp)) {
                    player.setHealth(-10);
                    attack.setDestroyed(true);
                }
            }
        }
    }

    //Update Minion minionFrame
    class updateMinionFrame extends TimerTask {
        public void run(){
            Iterator it = minions.iterator();
            while (it.hasNext()) {
                Minion minion = (Minion) it.next();
                if (minion.isAlive()) {
                    minion.move(direction);
                }
            }

            musicFrame++;
            minionFrame++;

            //Check music frame
            if(musicFrame==2){
                musicFrame = 0;
            }

            //Check minion frame
            if(minionFrame == 8 && direction > 0) {
                minionFrame = 4;
            }
            if(minionFrame == 4 && direction < 0) {
                minionFrame = 0;
            }
        }
    }

    //Update Player frame

    //get Player
    public Player getPlayer() {
        return player;
    }

    //get Minions
    public ArrayList<Minion> getMinions() {
        return minions;
    }

    //Getter and Setter for inGame
    public boolean isInGame() {
        return inGame;
    }

    //Set inGame
    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }

    //Getter for Message
    public String getMessage() {
        return message;
    }

    //Getter for Frame
    public int getminionFrame() { return minionFrame; }

    public int getMusicFrame() {
        return musicFrame;
    }

    public int getPlayerFrame() {
        return playerFrame;
    }

    public int getBaseFrame() {
        return baseFrame;
    }

}
