package com.centipede;

import javax.swing.ImageIcon;

public class Shot extends Sprite {

    private final String shotImg = "src/images/centipede/shot.png";
    private final int H_SPACE = 6;
    private final int V_SPACE = 1;

    public Shot() {
    }

    public Shot(int x, int y) {
        setImage(shotImg);
        this.x = x + H_SPACE;
        this.y = y - V_SPACE;
        this.width = 2;
        this.height = 12;
    }

}