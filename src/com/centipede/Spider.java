package com.centipede;

import java.util.Random;

public class Spider extends Sprite implements Commons {
    private String spiderImg = "src/images/centipede/spider.png";

    public int error = 10;
    protected int speed = 3;
    private double dx = -1.5;
    private double dy = 1.5;

    Random r = new Random();

    public Spider(){
        setImage(spiderImg);
        this.y = BOARD_HEIGHT - PLAYER_AREA_HEIGHT + r.nextInt(20);
        this.x = BOARD_WIDTH - SPIDER_WIDTH - r.nextInt(20);
        this.width = SPIDER_WIDTH;
        this.height = SPIDER_HEIGHT;
    }

    public void hit(){
        hit += 1;
        if(hit == 1){
            error = 5;
        }else if(hit == 2){
            setDying(true);
        }
    }

    public void act(int x, int y){


        this.x += dx;
        this.y += dy;

        if(this.y <= BOARD_HEIGHT - PLAYER_AREA_HEIGHT * 2){
            dy = Math.abs(dy);
        }else if(this.y >= BOARD_HEIGHT - 5 * 6){
            dy = -Math.abs(dy);
        }

        if(this.x <= 0){
            dx = Math.abs(dx);
        }else if(this.x >= BOARD_WIDTH - SPIDER_WIDTH){
            dx = -Math.abs(dx);
        }


//        int dx = this.x - x;
//        int dy = this.y - y;
//        double angle = Math.atan2(dy,dx);
//
//        this.y += (int) (speed * Math.sin(angle));
//        this.x += (int) (speed * Math.cos(angle));
//        angle += (r.nextFloat() * 2 - 1) * error * 2 * Math.PI;

    }
}
