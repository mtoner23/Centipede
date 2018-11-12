package com.centipede;

import javax.swing.*;

public class Mushroom extends Sprite implements  Commons{

    private final String mushroomImg = "src/images/centipede/mushroom.png";

    public Mushroom(int x, int y) {
        this.x = x;
        this.y = y;

        setImage(mushroomImg);
    }
}
