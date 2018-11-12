package com.centipede;

import java.util.*;

public class Centipede {

    private LinkedList<Segment> segments;

    public Centipede(){

    };

    public Centipede(int size, int x, int y){

        for(int i = 0; i < size; i++){
            segments.add(new Segment(x,y));
        }
    }


}
