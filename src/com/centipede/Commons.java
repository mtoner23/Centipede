package com.centipede;

public interface Commons {



    int BOARD_WIDTH = 400;
    int BOARD_HEIGHT = 700;
    int GRID_SIZE = 20;
    int ALIEN_HEIGHT = 12;
    int ALIEN_WIDTH = 12;
    int BORDER_RIGHT = GRID_SIZE;
    int BORDER_LEFT = GRID_SIZE;
    int CHANCE = 5;
    int DELAY = 17;
    int PLAYER_WIDTH = 14;
    int PLAYER_HEIGHT = 16;
    int SPIDER_WIDTH = 30;
    int SPIDER_HEIGHT = 12;
    int CENTIPEDE_LENGTH = 10;
    int SEGMENT_HEIGHT = GRID_SIZE;
    int SEGMENT_WIDTH = GRID_SIZE;
    int SHOT_SPEED = 9;
    int INIT_SPEED = 9;
    int PLAYER_AREA_HEIGHT = 100;
    int START_Y = BOARD_HEIGHT - PLAYER_AREA_HEIGHT - GRID_SIZE;
    int START_X = BOARD_WIDTH / 2;
    int [][] grid = new int[BOARD_HEIGHT/GRID_SIZE][BOARD_WIDTH/GRID_SIZE];
}