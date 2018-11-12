package com.centipede;

import javax.swing.ImageIcon;

public class Segment extends Sprite {


    private Segment leftSegment;
    private Segment rightSegment;
    public int direction = -1;
    public int head = 0;
    private int hit = 0;

    public Segment(int x, int y, String img, String revImg) {
        this.x = x;
        this.y = y;

        setImage(img);
        setRevImage(revImg);

    }

    public void flipSegment(){
        //ImageIcon img = this.image;
    }

    public void setRightSegment(Segment rightSegment){
        this.rightSegment = rightSegment;
    }

    public void setLeftSegment(Segment leftSegment) {
        this.leftSegment = leftSegment;
    }
}
