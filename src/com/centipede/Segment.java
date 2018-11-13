package com.centipede;

import javax.swing.ImageIcon;
import java.awt.*;

public class Segment extends Sprite implements Commons {


    private Segment leftSegment;
    private Segment rightSegment;
    private String segHitImg = "src/images/centipede/segment_hit.png";
    private String segRevHitImg = "src/images/centipede/segment_rotate_hit.png";
    private final String segImg = "src/images/centipede/segment.png";
    private final String headImg = "src/images/centipede/head.png";
    private final String headRevImg = "src/images/centipede/head_rotate.png";
    private final String segRevImg = "src/images/centipede/segment_rotate.png";
    protected Image revImage;
    public int direction = -1;
    public boolean head  = false;
    //public int head = 0;

    public Segment(int x, int y,boolean head) {
        this.x = x;
        this.y = y;
        this.width = GRID_SIZE;
        this.height = GRID_SIZE;
        this.head = head;

        if(head) {
            setImage(headImg);
            setRevImage(headRevImg);
        }else{
            setImage(segImg);
            setRevImage(segRevImg);
        }

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

    public void setHead(){
        this.head = true;
        setImage(headImg);
        setRevImage(headRevImg);
    }

    public void setRevImage(String img){
        ImageIcon ii = new ImageIcon(img);
        this.revImage = ii.getImage();
    }

    public void hit(){
        hit += 1;
        if(hit == 1){
            if(!head) {
                this.setImage(segHitImg);
                this.setRevImage(segRevHitImg);
            }
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
