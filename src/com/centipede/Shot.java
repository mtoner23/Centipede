package com.centipede;

import javax.swing.ImageIcon;

public class Shot extends Sprite {

    private final String shotImg = "src/images/spaceinvaders/shot.png";
    private final int H_SPACE = 6;
    private final int V_SPACE = 1;

    public Shot() {
    }

    public Shot(int x, int y) {

        initShot(x, y);
    }

    private void initShot(int x, int y) {

        setImage(shotImg);

        setX(x + H_SPACE);
        setY(y - V_SPACE);
    }
}