package com.centipede;

import javax.swing.*;
import java.awt.Image;
import java.awt.Rectangle;

public class Sprite {

    private boolean visible;
    protected Image image;
    protected int x;
    protected int y;
    protected boolean dying;
    protected int dx;
    protected int hit = 0;
    protected int width = 0;
    protected int height = 0;

    public Sprite() {

        visible = true;
    }

    public void die() {

        visible = false;
    }

    public boolean isVisible() {

        return visible;
    }

    protected void setVisible(boolean visible) {

        this.visible = visible;
    }

    public void setImage(String img) {
        ImageIcon ii = new ImageIcon(img);
        this.image = ii.getImage();
    }

    public Image getImage() {

        return image;
    }

    public void setX(int x) {

        this.x = x;
    }

    public void setY(int y) {

        this.y = y;
    }

    public int getY() {

        return y;
    }

    public int getX() {

        return x;
    }

    public void hit(){
        this.hit += 1;
    }

    public void setDying(boolean dying) {
        this.dying = dying;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public boolean isDying() {

        return this.dying;
    }
}