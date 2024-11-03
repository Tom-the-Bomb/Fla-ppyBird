package org.joshtommy;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayDeque;
import java.util.Random;
import javax.swing.*;

public class Frame extends JPanel implements ActionListener, KeyListener {
    //board size
    public static final int HEIGHT = 700;
    public static final int WIDTH = 1000;
    //delay to next tick in ms
    public static final int DELAY = 25;
    //max distance from current to the next pipes opening
    public static final int MAX_PIPE_JUMP = 150;
    //spacing between pipes in ticks
    public static final int DIST_NEXT_PIPE = 300;
    public static final int TICKS_SPEED_INCREASE = 100;
    //pipe speed
    private int pipeSpeed = 5;//positive number for left movement
    //used to keep track of pipe distance
    private int ticksElapsed;
    private int lastPipeTick;
    // objects that appear on the game board
    private final transient Player player;
    private final transient ArrayDeque<Pipe> pipes;
    private final Random random;
    private boolean alive;

    public Frame() {
        // set the frames size
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        // set the frames background color
        setBackground(new Color(32, 168, 244));

        // initialize the game state
        player = new Player();
        pipes = new ArrayDeque<>();
        ticksElapsed = 0;
        lastPipeTick = 0;
        alive = true;
        random = new Random();

        // this timer will call the actionPerformed() method every DELAY ms
        // controls the delay between each tick in ms
        Timer timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    //changes state of game before repaint
    public void actionPerformed(ActionEvent e) {
        //keeps time for pipes
        ticksElapsed++;
        //if your not alive your DEAD!!!
        if (!alive) return;
        //gets min and max for next pipes opening depending on last ones
        if ((ticksElapsed - lastPipeTick) * pipeSpeed >= DIST_NEXT_PIPE) {
            int min;
            int max;
            if (!pipes.isEmpty()) {
                min = Math.max(pipes.getLast().pipeHeight - MAX_PIPE_JUMP, MAX_PIPE_JUMP);
                max = Math.min(pipes.getLast().pipeHeight + MAX_PIPE_JUMP, Frame.HEIGHT - MAX_PIPE_JUMP - Pipe.SPACE);
            } else {
                min = MAX_PIPE_JUMP;
                max = Frame.HEIGHT - MAX_PIPE_JUMP - Pipe.SPACE;
            }
            //random number not to far from last pipe opening
            int randomInt = random.nextInt(max - min + 1) + min;
            pipes.add(new Pipe(this, randomInt));
            lastPipeTick = ticksElapsed;
            if (pipes.getFirst().pos.x <= 0) {
                pipes.removeFirst();
            }
        }
        if (ticksElapsed % TICKS_SPEED_INCREASE == 0) pipeSpeed++;
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
        player.draw(g);
        //redraw all pipes
        for (Pipe pipe : pipes) {
            pipe.draw(g);
        }
        //TODO: deque not efficient (not traversable)
        for (Pipe pipe : pipes) {
            if (pipe.getPointTL().x < player.getPos().x + Player.PLAYER_SIZE
                    && pipe.getPointTR().x > player.getPos().x
                    && (pipe.getPointTL().y > player.getPos().y
                    || pipe.getPointBL().y < player.getPos().y + Player.PLAYER_SIZE)) {
                alive = false;
                drawDefeatScreen(g);
                break; //TODO: show screen not working
            }

        }
        drawScore(g);

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
    }

    //resets the games
    public void reset() {
        pipes.clear();
        alive = true;
        ticksElapsed = 0;
        lastPipeTick = 0;
        pipeSpeed = 5;
    }

    public void drawDefeatScreen(Graphics g) {
        String lossMessage = "YOU LOOOSE";
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHint(
                RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(
                RenderingHints.KEY_FRACTIONALMETRICS,
                RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2d.setColor(new Color(0, 0, 0));
        g2d.setFont(new Font("Lato", Font.BOLD, 25));
        FontMetrics metrics = g2d.getFontMetrics(g2d.getFont());
        g2d.drawString(lossMessage, Frame.WIDTH / 2 - metrics.stringWidth(lossMessage), Frame.HEIGHT / 2 - metrics.getHeight());

        //TODO: only reset on button press
        reset();
    }

    public void drawScore(Graphics g) {
        //score will update every 2500ms
        String score = "" + (ticksElapsed / 100);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHint(
                RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(
                RenderingHints.KEY_FRACTIONALMETRICS,
                RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2d.setColor(new Color(0, 0, 0));
        g2d.setFont(new Font("Lato", Font.BOLD, 25));
        FontMetrics metrics = g2d.getFontMetrics(g2d.getFont());
        g2d.drawString(score, Frame.WIDTH - metrics.stringWidth(score) - 10, metrics.getHeight());
    }

    public int getPipeSpeed() {
        return pipeSpeed;
    }
}
