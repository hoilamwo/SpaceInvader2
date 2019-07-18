package view;

import controller.MainController;
import model.gameObject.Attack;
import model.gameObject.Minion;
import model.gameObject.Player;
import model.world.Game;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Board extends JPanel implements Runnable, Commons {

    private Game game;
    private Player player;
    private ArrayList<Minion> minions;

    private Dimension d;
    private Thread animator;

    public Board() {
        this.game = new Game();
        this.player = game.getPlayer();
        this.minions = game.getMinions();

        //Add Key Listener to JPanel
        this.addKeyListener(new MainController(this, player));

        animator = new Thread(this);
        animator.start();
        setDoubleBuffered(true);
        setFocusable(true);
        d = new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
        setBackground(Color.black);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.black);
        g.fillRect(0, 0, d.width, d.height);

        if (game.isInGame()) {
            g.setColor(new Color(255,125,192));
            drawPlayer(g);
            drawShot(g);
            drawHealthBar(g);
            g.setColor(new Color(150,8,31));
            drawMinion(g);
            drawAttack(g);
        }

        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }

    //
    //  Game Objects Graphics
    //
    public void drawPlayer(Graphics g) {
        if (player.isAlive()) {
            g.drawRect(player.getX(), player.getY(), PLAYER_WIDTH, PLAYER_HEIGHT);
            g.fillRect(player.getX(), player.getY(), PLAYER_WIDTH, PLAYER_HEIGHT);
        }
    }

    public void drawShot(Graphics g) {
        for(Attack s: player.getAttacks()) {
            if(!s.isDestroyed()){
                g.drawRect(s.getX(), s.getY(), 3, 5);
                g.fillRect(s.getX(), s.getY(), 3, 5);
            }
        }
    }

    public void drawHealthBar(Graphics g) {
        g.setColor(Color.WHITE);
        g.drawRect(50, 440, player.getHealth() * 4, 10);
        g.setColor(new Color(255,125,192));
        g.fillRect(50, 440, player.getHealth() * 4, 10);
    }

    public void drawMinion(Graphics g) {
        for (Minion m : minions) {
            if (m.isAlive()) {
                g.drawRect(m.getX(), m.getY(), MINION_WIDTH, MINION_HEIGHT);
                g.fillRect(m.getX(), m.getY(), MINION_WIDTH, MINION_HEIGHT);
            }
        }
    }

    public void drawAttack(Graphics g) {
        for (Minion m: minions) {
            Attack a = m.getAttack();

            if(!a.isDestroyed()) {
                g.drawRect(a.getX(),a.getY(), 5, 10);
                g.fillRect(a.getX(),a.getY(), 5, 10);
            }
        }
    }


    //
    //  Game Over Graphic
    //
    public void gameOver() {
        Graphics g = this.getGraphics();

        g.setColor(Color.black);
        g.fillRect(0, 0, BOARD_WIDTH, BOARD_HEIGHT);

        Font small = new Font("Helvetica", Font.BOLD, 20);
        FontMetrics metr = this.getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(game.getMessage(), (BOARD_WIDTH - metr.stringWidth(game.getMessage())) / 2, 100);
    }

    @Override
    public void run() {

        int count = 0;

        while (game.isInGame()) {
            try {
                Thread.sleep(30);
            } catch(InterruptedException ie) {
                //Ignore this exception
            }

            game.update();
            repaint();
            count++;
        }
        gameOver();

    }
}
