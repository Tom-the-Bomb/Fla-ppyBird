package org.joshtommy;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayDeque;
import javax.swing.*;

public class Frame extends JPanel implements ActionListener, KeyListener {
    //board size
    public static final int HEIGHT = 700;
    public static final int WIDTH = 1000;
    public static boolean ALIVE = true;
    //max distance from current to the next pipes opening
    public static final int MAX_PIPE_JUMP = 150;
    //spacing between pipes in ticks
    public static final int DIST_NEXT_PIPE = 100;
    //used to keep track of pipe distance
    private int timeElapsed;
    public static final int PIPES_ON_SCREEN = 6;
    // objects that appear on the game board
    private final Player player;
    private final ArrayDeque<Pipe> pipes;

    public Frame() {
        // set the frames size
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        // set the frames background color
        setBackground(new Color(32, 168, 244));

        // initialize the game state
        player = new Player();
        pipes = new ArrayDeque<>();
        timeElapsed = 0;

        // this timer will call the actionPerformed() method every DELAY ms
        // controls the delay between each tick in ms
        int DELAY = 25;
        Timer timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    //changes state of game before repaint
    public void actionPerformed(ActionEvent e) {
        if (!ALIVE) return;
        //gets min and max for next pipes opening depending on last ones
        if (timeElapsed % DIST_NEXT_PIPE == 0) {
            int min;
            int max;
            if (!pipes.isEmpty()) {
                min = Math.max(pipes.getLast().pipeHeight - MAX_PIPE_JUMP, MAX_PIPE_JUMP);
                max = Math.min(pipes.getLast().pipeHeight + MAX_PIPE_JUMP, Frame.HEIGHT - MAX_PIPE_JUMP);
            } else {
                min = MAX_PIPE_JUMP;
                max = Frame.HEIGHT - MAX_PIPE_JUMP;
            }
            //random number not to far from last pipe opening
            int random = (int) (Math.random() * (max - min + 1)) + min;
            pipes.add(new Pipe(random));
            if (pipes.getFirst().pos.x <= 0) {
                pipes.removeFirst();
            }
        }
        player.tick();
        for (Pipe pipe : pipes) {
            pipe.tick();
        }
        //calls paintComponent redrawing the graphics
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // draw graphics.
        drawBackground(g);
        //keeps time for pipes
        timeElapsed++;
        player.draw(g, this);
        //redraw all pipes
        for (Pipe pipe : pipes) {
            pipe.draw(g, this);
        }
        //if (timeElapsed > PIPES_ON_SCREEN * DIST_NEXT_PIPE) {//TODO: deque not efficient (not traversable)
        int i = 1;
        Pipe middlePipe = new Pipe(0);
        for (Pipe pipe : pipes) {
            if (pipe.getPointTL().x < player.getPos().x && pipe.getPointTR().x > player.getPos().x) {
                //under pipe in x pos
                if (pipe.getPointTL().y > player.getPos().y || pipe.getPointBL().y < player.getPos().y) {
                    //not between gap in y
                    ALIVE = false;
                    drawDefeatScreen(g); //TODO: show screen not working
                }
            }
        }

        // smooths out animations on some systems
        Toolkit.getDefaultToolkit().sync();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // this is not used but must be defined as part of the KeyListener interface
    }

    @Override
    public void keyPressed(KeyEvent e) {
        player.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // react to key up events
    }

    private void drawBackground(Graphics g) {
        g.setColor(new Color(70, 190, 230));
        //TODO: add clouds and things
//        for (int row = 0; row < ROWS; row++) {
//            for (int col = 0; col < COLUMNS; col++) {
//                // only color every other tile
//                if ((row + col) % 2 == 1) {
//                    // draw a square tile at the current row/column position
//                    g.fillRect(
//                            col * TILE_SIZE,
//                            row * TILE_SIZE,
//                            TILE_SIZE,
//                            TILE_SIZE
//                    );
//                }
//            }
//        }
    }

    //resets the games
    public void reset() {
        pipes.clear();
        ALIVE = true;
    }

    public void drawDefeatScreen(Graphics g) {
        g.setColor(new Color(70, 190, 230));
        g.fillRect(200, 200, 200, 200);
        g.drawString("Defeat", Frame.WIDTH / 2, Frame.HEIGHT / 2);
        reset();//TODO: only reset on button press
    }
}
