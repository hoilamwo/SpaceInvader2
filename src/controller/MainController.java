package controller;

import model.gameObject.Player;
import model.world.Game;
import view.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static view.Commons.BOARD_HEIGHT;
import static view.Commons.BOARD_WIDTH;


public class MainController implements KeyListener {

    Board board;
    Player player;

    public MainController(Board board, Player player) {
        this.board = board;
        this.player = player;
    }


    /*
     * keyboard inputs
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        //Use boolean to fix lag !!!
        //Register as "keep moving"
        switch (keyCode) {
            case KeyEvent.VK_LEFT:
                player.setWalkLeft(true);
                break;
            case KeyEvent.VK_RIGHT:
                player.setWalkRight(true);
                break;
            case KeyEvent.VK_SPACE:
                player.addShot(player.getX(), player.getY());
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();

        switch (keyCode) {
            case KeyEvent.VK_LEFT:
                player.setWalkLeft(false);
                break;
            case KeyEvent.VK_RIGHT:
                player.setWalkRight(false);
                break;

        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not used
    }
}
