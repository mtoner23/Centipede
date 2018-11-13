package com.centipede;

import java.awt.*;
import java.awt.event.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import static java.awt.event.KeyEvent.VK_N;
import static java.awt.event.KeyEvent.VK_Q;
import static java.awt.event.KeyEvent.VK_R;

public class Board extends JPanel implements Runnable, Commons {

    private Dimension d;
    private ArrayList<Mushroom> mushrooms;
    private ArrayList<Shot> shots;
    private Player player;
    private Shot shot;
    private Centipede centipede;
    private int lives = 3;

    private final int ALIEN_INIT_X = 150;
    private final int ALIEN_INIT_Y = 5;
    private int direction = -1;
    //private int deaths = 0;
    private int wait = 0;
    private int speed = 4;



    private boolean ingame = true;
    private boolean restart = false;
    private final String explImg = "src/images/spaceinvaders/explosion.png";
    private String message = "Game Over";

    private Thread animator;

    //AffineTransform backup = g2d.getTransform();

    public Board() {
        initBoard();
    }

    private void initBoard() {

        System.setProperty("apple.awt.fullscreenhidecursor","true");

        addKeyListener(new Keyboard());
        Mouse m = new Mouse();
        addMouseMotionListener(m);
        addMouseListener(m);

        setFocusable(true);
        d = new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
        setBackground(Color.black);

        gameInit();
        setDoubleBuffered(true);
    }

    @Override
    public void addNotify() {

        super.addNotify();
        gameInit();
    }

    public void gameInit() {

        mushrooms = new ArrayList<>();
        Random r = new Random();
        for (int i = 2 * GRID_SIZE; i < BOARD_HEIGHT - PLAYER_AREA_HEIGHT; i += GRID_SIZE) {
            for (int j = BORDER_LEFT + GRID_SIZE; j < BOARD_WIDTH - BORDER_RIGHT - GRID_SIZE; j += GRID_SIZE) {
                if(r.nextInt(CHANCE) == CHANCE - 1) {
                    if(grid[i/GRID_SIZE - 1][j/GRID_SIZE - 1] == 0 && grid[i/GRID_SIZE - 1][j/GRID_SIZE + 1] == 0) {
                        Mushroom mushroom = new Mushroom(j, i);
                        mushrooms.add(mushroom);
                        grid[i / GRID_SIZE][j / GRID_SIZE] = 1;
                    }
                }else{
                    grid[ i/GRID_SIZE ][ j/GRID_SIZE ] = 0;
                }
            }
        }

        player = new Player();
        shots = new ArrayList<>();
        centipede = new Centipede();
        //shot = new Shot();

        if (animator == null || !ingame) {

            animator = new Thread(this);
            animator.start();
        }
    }


    public void drawCentipede(Graphics g) {
        for(Segment seg: centipede.segments) {
            g.drawImage(seg.getImage(), seg.getX(), seg.getY(), this);
        }
    }

    public void drawPlayer(Graphics g) {

        if (player.isVisible()) {

            g.drawImage(player.getImage(), player.getX(), player.getY(), this);
        }

        if (player.isDying()) {

            player.die();
            ingame = false;
        }
    }

    public void drawShot(Graphics g) {
        for(Shot s : shots){
            g.drawImage(s.getImage(), s.getX(), s.getY(), this);
        }
    }

    public void drawMushrooms(Graphics g){
        for(Mushroom m : mushrooms){
            g.drawImage(m.getImage(), m.getX(), m.getY(), this);
        }
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.black);
        g.fillRect(0, 0, d.width, d.height);
        g.setColor(Color.green);

        if (ingame) {

            //g.drawLine(0, GROUND, BOARD_WIDTH, GROUND);
            drawGrid(g);
            drawPlayer(g);
            drawShot(g);
            drawCentipede(g);
            drawMushrooms(g);

        }

        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }

    private void drawGrid(Graphics g){
        ImageIcon ii = new ImageIcon("src/images/centipede/dot.png");
        boolean dot = true;

        for(int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid[0].length; j++){
                if(grid[i][j] == 1 || dot){
                    g.drawImage(ii.getImage(),j*GRID_SIZE,i*GRID_SIZE, this );
                }
            }
        }
    }

    public void gameOver() {

        Graphics g = this.getGraphics();

        setCursor(Cursor.getDefaultCursor());

        g.setColor(Color.black);
        g.fillRect(0, 0, BOARD_WIDTH, BOARD_HEIGHT);

        g.setColor(new Color(0, 32, 48));
        g.fillRect(50, BOARD_WIDTH / 2 - 30, BOARD_WIDTH - 100, 50);
        g.setColor(Color.white);
        g.drawRect(50, BOARD_WIDTH / 2 - 30, BOARD_WIDTH - 100, 50);

        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = this.getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(message, (BOARD_WIDTH - metr.stringWidth(message)) / 2,
                BOARD_WIDTH / 2);
    }

    public void animationCycle() {

        if (lives == 0) {
            ingame = false;
            message = "Game Over!";
        }

        // player

        //Centipede Movement
        if(wait == speed) {
            centipede.act();
            wait = 0;
        }
        wait++;

        // shot
        ListIterator<Shot> iter = shots.listIterator();
        while(iter.hasNext()) {
            Shot s = iter.next();
            int shotX = s.getX();
            int shotY = s.getY();

            ListIterator<Mushroom> mushIter = mushrooms.listIterator();
            while (mushIter.hasNext()) {
                Mushroom mush = mushIter.next();

                int mushX = mush.getX();
                int mushY = mush.getY();

                if (mush.isVisible() && s.isVisible()) {
                    if (shotX >= (mushX)
                            && shotX <= (mushX + ALIEN_WIDTH)
                            && shotY >= (mushY)
                            && shotY <= (mushY + ALIEN_HEIGHT)) {

                        mush.hit();
                        if(mush.isDying()){
                            mushIter.remove();
                            grid[mushY/GRID_SIZE][mushX/GRID_SIZE] = 0;
                        }
                        iter.remove();
                        break;
                    }
                }
            }

            int y = s.getY();
            y -= SHOT_SPEED;

            if (y < 0) {
                iter.remove();
            } else {
                s.setY(y);
            }
        }

        // aliens


        // bombs


    }

    @Override
    public void run() {

        long beforeTime, timeDiff, sleep;

        beforeTime = System.currentTimeMillis();

        while (ingame) {
            if(restart){
                //gameOver();
                gameInit();
                restart = false;
            }

            repaint();
            animationCycle();

            timeDiff = System.currentTimeMillis() - beforeTime;
            sleep = DELAY - timeDiff;

            if (sleep < 0) {
                sleep = 2;
            }

            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                System.out.println("interrupted");
            }

            beforeTime = System.currentTimeMillis();
        }

        gameOver();

    }

    private class Mouse implements MouseMotionListener,MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {
            int x = player.getX();
            int y = player.getY();

            if (ingame) {
                shots.add(new Shot(x, y));
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseMoved(MouseEvent e){
            player.setX(e.getX());
            player.setY(e.getY());
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            player.setX(e.getX());
            player.setY(e.getY());
        }
    }

    private class Keyboard extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            if(key == VK_R){
                restart = true;
            }else if(key == VK_Q){
                ingame = false;
            }
        }
    }
}