package com.centipede;

import javax.swing.ImageIcon;

public class Segment extends Sprite {

    private final String segmentImg = "src/images/spaceinvaders/alien.png";
    private Segment leftSegment;
    private Segment rightSegment;

    public Segment(int x, int y) {
        this.x = x;
        this.y = y;

        ImageIcon ii = new ImageIcon(segmentImg);
        setImage(ii.getImage());
    }

    public void setRightSegment(Segment rightSegment){
        this.rightSegment = rightSegment;
    }

    public void setLeftSegment(Segment leftSegment) {
        this.leftSegment = leftSegment;
    }
}
