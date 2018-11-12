package com.centipede;

import java.awt.geom.AffineTransform;
import java.lang.reflect.Array;
import java.util.*;

public class Centipede implements Commons {

    public ArrayList<Segment> segments;
    private final String segmentImg = "src/images/centipede/segment.png";
    private final String headImg = "src/images/centipede/head.png";
    private final String backHeadImg = "src/images/centipede/head_rotate.png";
    private final String backSegmentImg = "src/images/centipede/segment_rotate.png";
    private final int INIT_X = 150;
    private final int INIT_Y = 5;

    public Centipede(){
        this(CENTIPEDE_LENGTH,150,5);
    };

    public Centipede(int size, int x, int y){

        segments = new ArrayList<>();

        for(int i = 0; i < size - 1; i++) {
            segments.add(new Segment(x, y, segmentImg, backSegmentImg));
            x += SEGMENT_WIDTH;
        }

        segments.add(new Segment(x,y, headImg, backHeadImg));
    }

    public void act(){


        int direction = 1;

        //FOr loop thorugh all segments and then move the last segment. better way to do it
        int i;
        for(i = 0; i < segments.size() - 1; i++) {
            segments.get(i).setX(segments.get(i + 1).getX());
            segments.get(i).setY(segments.get(i + 1).getY());
            segments.get(i).direction = segments.get(i+1).direction;
        }

        Segment head = segments.get(i);
        head.setX(head.getX() + (SEGMENT_SPEED) * head.direction);

        if (head.getX() >= BOARD_WIDTH - BORDER_RIGHT) {
            head.direction = -1;
            head.setX(head.getX() + (SEGMENT_SPEED) * head.direction);
            head.setY(head.getY() + SEGMENT_HEIGHT);
        }

        if (head.getX() <= BORDER_LEFT) {
            head.direction = 1;
            head.setX(head.getX() + (SEGMENT_SPEED) * head.direction);
            head.setY(head.getY() + SEGMENT_HEIGHT);
        }
    }

}
