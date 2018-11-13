package com.centipede;

import java.awt.EventQueue;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

public class CentipedeGame extends JFrame implements Commons {

    public CentipedeGame() {

        initUI();
    }

    private void initUI() {

        add(new Board());
        setTitle("Centipede");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(BOARD_WIDTH, BOARD_HEIGHT);
        setLocationRelativeTo(null);
        setResizable(false);

        // Transparent 16 x 16 pixel cursor image.
        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

        // Create a new blank cursor.
        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor( cursorImg, new Point(0, 0), "blank cursor");

        setCursor(blankCursor);
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            CentipedeGame ex = new CentipedeGame();
            ex.setVisible(true);
        });
    }
}