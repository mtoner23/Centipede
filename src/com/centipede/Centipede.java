package com.centipede;

import java.lang.reflect.Array;
import java.util.*;

public class Centipede implements Commons {

    public ArrayList<Segment> segments;
    public int xdirection = -1;
    public int ydirection = 0;
    private final int INIT_X = 150;
    private final int INIT_Y = 5;

    public Centipede(){
        this(CENTIPEDE_LENGTH,150,5);
    };

    public Centipede(int size, int x, int y){

        segments = new ArrayList<>();

        for(int i = 0; i < size; i++){
            segments.add(new Segment(x,y));
            x += SEGMENT_WIDTH;
        }
    }

    public void act(){


        int direction = 1;

        //FOr loop thorugh all segments and then move the last segment. better way to do it
        int i;
        for(i = 0; i < segments.size() - 1; i++) {
            segments.get(i).setX(segments.get(i + 1).getX());
            segments.get(i).setY(segments.get(i + 1).getY());
        }

        Segment head = segments.get(i);
        head.setX(head.getX() + (SEGMENT_SPEED) * xdirection);

        if (head.getX() >= BOARD_WIDTH - BORDER_RIGHT) {
            xdirection = -1;
            head.setX(head.getX() + (SEGMENT_SPEED) * xdirection);
            head.setY(head.getY() + SEGMENT_HEIGHT);
        }

        if (head.getX() <= BORDER_LEFT) {
            xdirection = 1;
            head.setX(head.getX() + (SEGMENT_SPEED) * xdirection);
            head.setY(head.getY() + SEGMENT_HEIGHT);
        }
    }

}
