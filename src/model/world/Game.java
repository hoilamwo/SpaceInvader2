package model.world;

import model.gameObject.Attack;
import model.gameObject.Minion;
import model.gameObject.Player;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import static view.Commons.*;
import static view.Commons.MINION_INIT_Y;

public class Game{

    private boolean inGame;
    private Dimension d;

    private Player player;

    //Minions's moving direction
    private int direction = -1;

    private ArrayList<Minion> minions = new ArrayList<>();
    //Number of Minions alive
    private int minionAlive = 30;

    private String message = "Game Over! You Lost :( ";

    Random generator = new Random();

    public Game() {
        initGame();
    }

    private void initGame() {
        d = new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
        addMinions();
        this.player = new Player();
        this.inGame = true;
    }

    //Add Minions to minions
    public void addMinions() {
        for(int i = 0; i < 3; i++){
            for(int j = 0; j <10; j++) {
                Minion minion = new Minion(MINION_INIT_X + 40 * j, MINION_INIT_Y + 30 * i);
                minions.add(minion);
            }
        }
    }

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

        System.out.println(player.getHealth());
        System.out.println(minionAlive);
    }

    //Update Player
    public void updatePlayer() {

        //Check whether player is alive or not
        if(player.getHealth()<=0){
            player.die();
        }

        //Move player
        if(player.isWalkLeft()){
            player.setDx(-3);
            player.setX(player.getX() + player.getDx());
        }

        if(player.isWalkRight()){
            player.setDx(3);
            player.setX(player.getX() + player.getDx());
        }

        //Check Player Boundaries
        if (player.getX() <= 5) {
            player.setX(5);
        }
        if (player.getX() >= BOARD_WIDTH-PLAYER_WIDTH*2-5) {
            player.setX(BOARD_WIDTH-PLAYER_WIDTH*2-5);
        }

    }

    //Update Player Attack
    public void updatePlayerAttack() {
        ArrayList<Attack> playerAttack = player.getAttacks();

        for(Iterator<Attack> it = playerAttack.iterator(); it.hasNext(); ){
            Attack shot = it.next();

            int shotY = shot.getY();

            //Shot velocity
            int newY = shotY-8;

            //Check Top Boundary
            if(shotY <= 0) {
                shot.setDestroyed(true);
            } else {
                shot.setY(newY);
            }

            //Check collision with minions
            if(!shot.isDestroyed()) {

                int shotX = shot.getX();

                for(Minion m: minions){
                    int mX = m.getX();
                    int mY = m.getY();

                    //If shot hits minion, minion dies and shot is destroyed
                    if(m.isAlive()){
                        if(shotX >= mX && shotX <= mX + MINION_WIDTH) {
                            if(shotY >= mY && shotY <= mY + MINION_HEIGHT) {
                                m.die();
                                shot.setDestroyed(true);
                                minionAlive--;

                            }
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

        //Move Minions + Update attack for each minion
        Iterator it = minions.iterator();
        while (it.hasNext()) {
            Minion minion = (Minion) it.next();
            if (minion.isAlive()) {
                minion.move(direction);
            }
            updateMinionAttack(minion);
        }

        //Check Boundaries
        for(Minion m: minions) {
            double x = m.getX();

            //Check Bottom Boundary
            if(m.getY()+ MINION_HEIGHT >= 410){
                this.inGame = false;
            }

            //Check Right Boundary
            if(x >= 455 && direction != -1){
                direction = -1;
                Iterator i1 = minions.iterator();

                while (i1.hasNext()) {
                    Minion m2 = (Minion) i1.next();
                    m2.setY(m2.getY() + 5);
                }
            }

            //Check Left Boundary
            if(x <= 0 && direction != 1) {
                direction = 1;
                Iterator i1 = minions.iterator();

                while (i1.hasNext()) {
                    Minion m2 = (Minion) i1.next();
                    m2.setY(m2.getY() + 5);
                }
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
        //Set destroyed if attack is on ground
        if(!attack.isDestroyed()) {
            //attack speed
            attack.setY(attack.getY() + 10);
            if(attack.getY() >= 435) {
                attack.setDestroyed(true);
            }
            if(player.isAlive()){
                if(attackX >= playerX && attackX <= playerX + PLAYER_WIDTH){
                    if (attackY >= playerY && attackY <= playerY + PLAYER_HEIGHT) {
                        player.setHealth(-10);
                        attack.setDestroyed(true);
                    }
                }
            }
        }
    }

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

}
