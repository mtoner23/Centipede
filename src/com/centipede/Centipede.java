package com.centipede;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.lang.reflect.Array;
import java.util.*;

public class Centipede implements Commons {

    public Vector<Segment> segments;
    private final String segmentImg = "src/images/centipede/segment.png";
    private final String headImg = "src/images/centipede/head.png";
    private final String backHeadImg = "src/images/centipede/head_rotate.png";
    private final String backSegmentImg = "src/images/centipede/segment_rotate.png";
    public int size = CENTIPEDE_LENGTH;
    private final int INIT_X = 150;
    private final int INIT_Y = 5;

    public Centipede(){
        this(CENTIPEDE_LENGTH,BOARD_WIDTH/2 + (CENTIPEDE_LENGTH/2)*GRID_SIZE,GRID_SIZE * 2);
    };

    public Centipede(int s, int x, int y){

        this.size = s;
        segments = new Vector<>();

        for(int i = 0; i < s - 1; i++) {
            segments.add(new Segment(x, y, segmentImg, backSegmentImg));
            x -= SEGMENT_WIDTH;
        }

        segments.add(new Segment(x,y, headImg, backHeadImg));
    }

    public void act(){



        //FOr loop thorugh all segments and then move the last segment. better way to do it
        int i;
        for(i = 0; i < segments.size() - 1; i++) {
            segments.get(i).setX(segments.get(i + 1).getX());
            segments.get(i).setY(segments.get(i + 1).getY());
            segments.get(i).direction = segments.get(i+1).direction;
        }

        Segment head = segments.get(i);
        head.setX(head.getX() + (SEGMENT_WIDTH) * head.direction);

        if (head.getX() >= BOARD_WIDTH - BORDER_RIGHT || head.direction == 1 && grid[head.getY()/GRID_SIZE][head.getX()/GRID_SIZE] == 1) {
            head.direction = -1;
            head.setX(head.getX() + (SEGMENT_WIDTH) * head.direction);
            head.setY(head.getY() + SEGMENT_HEIGHT);
        }

        if (head.getX() < BORDER_LEFT || head.direction == -1 && grid[head.getY()/GRID_SIZE][head.getX()/GRID_SIZE] == 1) {
            head.direction = 1;
            head.setX(head.getX() + (SEGMENT_WIDTH) * head.direction);
            head.setY(head.getY() + SEGMENT_HEIGHT);
        }

        if(head.getY() >= BOARD_HEIGHT - PLAYER_AREA_HEIGHT){
            head.setY(BOARD_HEIGHT - PLAYER_AREA_HEIGHT - SEGMENT_HEIGHT);
        }
    }

}
