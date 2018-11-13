package com.centipede;

public class Mushroom extends Sprite implements  Commons{

    private final String mushroomImg = "src/images/centipede/mushroom.png";
    private final String mushroom1Hit = "src/images/centipede/mushroom1Hit.png";
    private final String mushroom2Hit = "src/images/centipede/mushroom2Hit.png";


    public Mushroom(int x, int y) {
        this.x = x;
        this.y = y;

        setImage(mushroomImg);
    }

    public void hit(){
        hit += 1;
        if(hit == 1){
            this.setImage(mushroom1Hit);
        }else if(hit == 2){
            this.setImage(mushroom2Hit);
        }else if(hit == 3){
            setDying(true);
        }
    }
}
