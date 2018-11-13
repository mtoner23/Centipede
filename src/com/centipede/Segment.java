package com.centipede;

import javax.swing.ImageIcon;
import java.awt.*;

public class Segment extends Sprite {


    private Segment leftSegment;
    private Segment rightSegment;
    protected Image revImage;
    public int direction = -1;
    public int head = 0;

    public Segment(int x, int y, String img, String revImg) {
        this.x = x;
        this.y = y;

        setImage(img);
        setRevImage(revImg);

    }

    public void flipSegment(){
        //ImageIcon img = this.image;
    }

    @Override
    public Image getImage(){
        if(direction == -1){
            return image;
        }else if(direction == 1){
            return revImage;
        }else{
            return null;
        }
    }

    public void setRevImage(String img){
        ImageIcon ii = new ImageIcon(img);
        this.revImage = ii.getImage();
    }

    public void setRightSegment(Segment rightSegment){
        this.rightSegment = rightSegment;
    }

    public void setLeftSegment(Segment leftSegment) {
        this.leftSegment = leftSegment;
    }
}
