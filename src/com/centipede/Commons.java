package com.centipede;

public interface Commons {



    int BOARD_WIDTH = 400;
    int BOARD_HEIGHT = 700;
    int GRID_SIZE = 10;
    int ALIEN_HEIGHT = 12;
    int ALIEN_WIDTH = 12;
    int BORDER_RIGHT = 10;
    int BORDER_LEFT = 10;
    int CHANCE = 12;
    int DELAY = 17;
    int PLAYER_WIDTH = 14;
    int PLAYER_HEIGHT = 16;
    int CENTIPEDE_LENGTH = 20;
    int SEGMENT_HEIGHT = GRID_SIZE;
    int SEGMENT_WIDTH = GRID_SIZE;
    int SHOT_SPEED = 7;
    int INIT_SPEED = 2;
    int PLAYER_AREA_HEIGHT = 100;
    int START_Y = BOARD_HEIGHT - PLAYER_AREA_HEIGHT - GRID_SIZE;
    int START_X = BOARD_WIDTH / 2;
    int [][] grid = new int[BOARD_HEIGHT/GRID_SIZE][BOARD_WIDTH/GRID_SIZE];
}