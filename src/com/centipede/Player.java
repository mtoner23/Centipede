package com.centipede;

import java.awt.*;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

public class Player  extends Sprite implements Commons {

    private final String playerImg = "src/images/centipede/player_2x.png";
    public int lives = 3;


    public Player() {
        setImage(playerImg);

        this.width = PLAYER_WIDTH;
        this.height = PLAYER_HEIGHT;
        this.x = START_X;
        this.y = START_Y;
    }

    public void hit(){

        this.x = START_X;
        this.y = START_Y;
        lives -= 1;
    }

}