package com.centipede;

import java.awt.*;
import java.awt.event.*;

import java.util.*;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;
import javax.sound.sampled.*;

import static java.awt.event.KeyEvent.*;

public class Board extends JPanel implements Runnable, Commons {

    private Dimension d;
    private Vector<Mushroom> mushrooms;
    private Vector<Shot> shots;
    private Player player;
    private Centipede centipede;
    private Spider spider;

    private int score = 0;
    private int wait = 0;
    private int speed = INIT_SPEED;

    private boolean ingame = true;
    private boolean restart = false;
    private boolean pause = false;
    private boolean invincible = false;
    private boolean quit = false;
    private boolean newShot = true;
    private String message = "Game Over";
    Robot robot;



    private Thread animator;
    private Sound sounds;


    //AffineTransform backup = g2d.getTransform();

    public Board() {
        initBoard();
        try {
            robot = new Robot();
        } catch(java.awt.AWTException e){
            System.out.println("Cannont Make Robot Object");
        }
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
        ingame = true;
        mushrooms = new Vector<>();
        Random r = new Random();
        score = 0;
        speed = INIT_SPEED;
        spider.dx = 1.5;
        spider.dy = 1.5;

        boolean found = false;
        for (int i = 3 * GRID_SIZE; i < BOARD_HEIGHT - PLAYER_AREA_HEIGHT - GRID_SIZE; i += GRID_SIZE) {
            for (int j = BORDER_LEFT + GRID_SIZE; j < BOARD_WIDTH - BORDER_RIGHT - GRID_SIZE; j += GRID_SIZE) {
                grid[ i/GRID_SIZE ][ j/GRID_SIZE ] = 0;
                if(r.nextInt(CHANCE) == CHANCE - 1 || found) {
                    found = true;
                    if(grid[i/GRID_SIZE - 1][j/GRID_SIZE - 1] == 0 && grid[i/GRID_SIZE - 1][j/GRID_SIZE + 1] == 0) {
                        Mushroom mushroom = new Mushroom(j, i);
                        mushrooms.add(mushroom);
                        grid[i / GRID_SIZE][j / GRID_SIZE] = 1;
                        found = false;
                    }
                }
            }
        }

        player = new Player();
        shots = new Vector<>();
        centipede = new Centipede();
        spider = new Spider();

        if(sounds == null){
            sounds = new Sound();
            sounds.start();
        }
        if (animator == null || !ingame) {
            animator = new Thread(this,"animation");
            animator.start();
        }
    }


    public void drawCentipede(Graphics g) {
        for (Segment seg : centipede.segments) {
            if (!seg.isDying()) {
                g.drawImage(seg.getImage(), seg.getX(), seg.getY(), this);
            }
        }
    }

    public void drawPlayer(Graphics g) {

        if (player.isVisible()) {

            g.drawImage(player.getImage(), player.getX(), player.getY(), this);
        }

    }

    public void drawShot(Graphics g) {
        synchronized(shots) {
            for (Shot s : shots) {
                g.drawImage(s.getImage(), s.getX(), s.getY(), this);
            }
        }
    }

    public void drawMushrooms(Graphics g){
        for(Mushroom m : mushrooms){
            g.drawImage(m.getImage(), m.getX(), m.getY(), this);
        }
    }

    public void drawLives(Graphics g){
        for(int i = 0; i < player.lives; i++){
            g.drawImage(player.getImage(),BORDER_RIGHT + i * PLAYER_WIDTH, 0,this);
        }
    }
    public void drawScore(Graphics g){

        Font small = new Font("Helvetica", Font.BOLD, PLAYER_HEIGHT);
        FontMetrics metr = this.getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(Integer.toString(score) + "        'R' to restart \'Q' to quit",5*PLAYER_WIDTH,PLAYER_HEIGHT-2);
    }

    public void drawSpider(Graphics g){
        g.drawImage(spider.getImage(),spider.getX(),spider.getY(),this);
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.black);
        g.fillRect(0, 0, d.width, d.height);
        g.setColor(Color.green);

        if (ingame) {

            //drawGrid(g);
            drawShot(g);
            drawCentipede(g);
            drawMushrooms(g);
            drawPlayer(g);
            drawLives(g);
            drawScore(g);
            drawSpider(g);

        }else{
            gameOver(g);
        }

        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }

    private void drawGrid(Graphics g){
        ImageIcon ii = new ImageIcon("src/images/centipede/dot.png");
        boolean dot = false;

        for(int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid[0].length; j++){
                if(grid[i][j] == 1 || dot){
                    g.drawImage(ii.getImage(),j*GRID_SIZE,i*GRID_SIZE, this );
                }
            }
        }
    }

    public void gameOver(Graphics g) {

        //setCursor(Cursor.getDefaultCursor());

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

    private Mushroom findMushroom(int x, int y){
        Mushroom hit = null;
        for(Mushroom m : mushrooms){
            if(m.getX()/GRID_SIZE == x && m.getY()/GRID_SIZE == y){
                hit = m;
                break;
            }
        }
        return hit;
    }

    public void resetMouse(){
        Point p = getLocationOnScreen();

        robot.mouseMove(START_X + (int)p.getX(), START_Y + (int)p.getY());
    }

    public void animationCycle() {

        if (player.lives == 0) {
            ingame = false;
            message = "Game Over!";
            //gameOver();
        }

        // player

        //Centipede Movement
        if(wait >= speed) {
            centipede.act();
            wait = 0;
        }
        wait++;

        spider.act(player.getX(),player.getY());


        // shot
            Vector<Shot> delete = new Vector<>();
            synchronized (shots) {
                for (Shot s : shots) {
                    int shotX = s.getX();
                    int shotY = s.getY();
                    if (grid[shotY / GRID_SIZE][shotX / GRID_SIZE] == 1) {
                        Mushroom mush = findMushroom(shotX / GRID_SIZE, shotY / GRID_SIZE);
                        mush.hit();
                        score += 1;
                        if (mush.isDying()) {
                                score += 4;
                                mushrooms.remove(mush);
                            grid[shotY / GRID_SIZE][shotX / GRID_SIZE] = 0;
                        }
                        delete.add(s);
                    } else {
                        int hit_index = 0;
                        boolean hit = false;
                        for (Segment seg : centipede.segments) {
                            if (seg.getY() / GRID_SIZE == shotY / GRID_SIZE && seg.getX() / GRID_SIZE == shotX / GRID_SIZE) {
                                seg.hit();
                                score += 2;
                                delete.add(s);
                                hit = true;
                                break;
                            }
                            hit_index++;
                        }
                        if (hit) {
                            centipede.split(hit_index);
                        }
                    }

                    Rectangle shotRect = s.getBounds();
                    Rectangle spiderRect = spider.getBounds();
                    if (shotRect.intersects(spiderRect)) {
                        spider.hit();
                        delete.add(s);
                        score += 100;
                        if (spider.isDying()) {
                            score += 500;
                            spider = new Spider();

                            delete.add(s);
                        }
                    }


                    int y = s.getY();
                    y -= SHOT_SPEED;
                    if (y < 0) {
                        delete.add(s);
                        break;
                    } else {
                        s.setY(y);
                    }
                }
                for (Shot s : delete) {
                    shots.remove(s);
                }
            }

        if(!invincible) {
            Rectangle pR = player.getBounds();
            for (Segment seg : centipede.segments) {
                Rectangle sR = seg.getBounds();
                if (pR.intersects(sR)) {
                    player.hit();
                    resetMouse();
                    restore();
                    break;
                }
            }
            Rectangle sR = spider.getBounds();
            if (pR.intersects(sR)) {
                player.hit();
                resetMouse();
                restore();
            }
        }

        if(centipede.size == 0){
            score += 600;
            if(speed >= 2) {
                speed -= 2;
            }
            System.out.println(speed);
            restore();
        }

    }

    @Override
    public void run() {

        long beforeTime, timeDiff, sleep;

        beforeTime = System.currentTimeMillis();

        while (!quit) {
            if (restart) {
                gameInit();
                resetMouse();
                restart = false;
            }
            if (!pause) {
                repaint();
                animationCycle();
            }

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
        sounds.stopMusic();
        System.exit(0);
    }

    private void restore(){
        for(Mushroom m : mushrooms){
            m.restore();
        }
        centipede = new Centipede();
        shots = new Vector<>();
        spider = new Spider();

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

                sounds.shoot();
                synchronized (shots) {
                    shots.add(new Shot(x, y));
                }
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
                quit = true;
            }else if(key == VK_P){
                pause = !pause;
            }else if(key == VK_M){
                sounds.mute();
            }
        }
    }
}