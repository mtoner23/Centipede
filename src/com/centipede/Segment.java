package com.centipede;

import javax.swing.ImageIcon;
import java.awt.*;

public class Segment extends Sprite {


    private Segment leftSegment;
    private Segment rightSegment;
    private String segHitImg = "src/images/centipede/segment_hit.png";
    private String segRevHitImg = "src/images/centipede/segment_rotate_hit.png";
    protected Image revImage;
    public int direction = -1;
    //public int head = 0;

    public Segment(int x, int y, String img, String revImg) {
        this.x = x;
        this.y = y;
        this.width = 10;
        this.height = 10;

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

    public void hit(){
        hit += 1;
        if(hit == 1){
            this.setImage(segHitImg);
            this.setRevImage(segRevHitImg);
        }else if(hit == 2){
            setDying(true);
        }
    }

    public void setRightSegment(Segment rightSegment){
        this.rightSegment = rightSegment;
    }

    public void setLeftSegment(Segment leftSegment) {
        this.leftSegment = leftSegment;
    }
}
