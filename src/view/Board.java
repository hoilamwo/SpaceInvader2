package view;

import controller.MainController;
import model.gameObject.Attack;
import model.gameObject.Minion;
import model.gameObject.Player;
import model.world.Game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Board extends JPanel implements Runnable, Commons {

    private Game game;
    private Player player;
    private ArrayList<Minion> minions;

    private Thread animator;

    BufferedImage img;
    Image bg;

    public Board() {
        this.setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
        this.game = new Game();
        this.player = game.getPlayer();
        this.minions = game.getMinions();

        //Add Key Listener to JPanel
        this.addKeyListener(new MainController(this, player));

        animator = new Thread(this);
        animator.start();
        setDoubleBuffered(true);
        setFocusable(true);

        //Background
        try {
            img = ImageIO.read(new File("bg3.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        bg = img.getScaledInstance(750, 750, Image.SCALE_SMOOTH);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(bg, 0, 0, this);

        if (game.isInGame()) {
            drawShot(g);
            drawPlayer(g);
            drawAttack(g);
            drawMinion(g);
            drawHealthBar(g);
        }

        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }

    //
    //  Game Objects Graphics
    //
    public void drawBackground(Graphics g) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("bg3.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void drawPlayer(Graphics g) {
        if (player.isAlive()) {
            BufferedImage baseS = baseSS.getSprite(game.getBaseFrame());
            g.drawImage(baseS,player.getX(),player.getY(),null);
            BufferedImage playerS = playerSS.getSprite(game.getPlayerFrame());
            g.drawImage(playerS, player.getX(), player.getY(), null);
            BufferedImage musicS = musicNoteSS.getSprite(game.getMusicFrame());
            g.drawImage(musicS,player.getX(),player.getY(),null);
        }
    }

    public void drawShot(Graphics g) {
        for(Player.PlayerAttack s: player.getAttacks()) {
            if(!s.isDestroyed()){
                BufferedImage shotS = shotSS.getSprite(s.getShotFrame());
                g.drawImage(shotS, s.getX(), s.getY(),null);
            }
        }
    }

    public void drawHealthBar(Graphics g) {
        g.setColor(new Color(240, 98, 146));
        g.fillRect(HEALTHBAR_INIT_X, HEALTHBAR_INIT_Y, 550, HEALTHBAR_HEIGHT);
        g.setColor(new Color(88, 201, 190));
        g.fillRect(HEALTHBAR_INIT_X, HEALTHBAR_INIT_Y, (int)(player.getHealth()*5.5), HEALTHBAR_HEIGHT);
        g.setColor(new Color(1, 87, 155));
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(3));
        g.drawRect(HEALTHBAR_INIT_X, HEALTHBAR_INIT_Y, (int)(550) , HEALTHBAR_HEIGHT);

    }

    public void drawMinion(Graphics g) {
        for (Minion m : minions) {
            if (m.isAlive()) {
                BufferedImage sprite = minionSS.getSprite(game.getminionFrame());
//                BufferedImage sprite = minionSS.getSprite(0);
                g.drawImage(sprite, m.getX(), m.getY(), this);

            }
        }
    }

    public void drawAttack(Graphics g) {
        for (Minion m: minions) {
            Attack a = m.getAttack();

            if(!a.isDestroyed()) {
                BufferedImage sprite = minionAtackSS.getSprite(0);
                g.drawImage(sprite, a.getX(),a.getY(), this);
//                g.drawRect(a.getX(),a.getY(), MINION_ATTACK_WIDTH, MINION_ATTACK_HEIGHT);
//                g.fillRect(a.getX(),a.getY(), MINION_ATTACK_WIDTH, MINION_ATTACK_HEIGHT);
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

        while (game.isInGame()) {
            try {
                Thread.sleep(17);
            } catch(InterruptedException ie) {
                //Ignore this exception
            }

            game.update();
            repaint();
        }
        gameOver();
    }
}
