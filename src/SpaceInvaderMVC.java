import controller.MainController;
import view.Board;
import view.Commons;

import javax.swing.*;
import java.awt.*;

import static view.Commons.BOARD_HEIGHT;
import static view.Commons.BOARD_WIDTH;

public class SpaceInvaderMVC extends JFrame implements Commons {

    public SpaceInvaderMVC() {
        initUI();
    }

    private void initUI() {
        setTitle("Space Invaders MVC");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        add(new Board());
        this.pack();
        setLocationRelativeTo(null);         //Set frame to center of screen
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            SpaceInvaderMVC ex = new SpaceInvaderMVC();
            ex.setVisible(true);
        });
    }
}
