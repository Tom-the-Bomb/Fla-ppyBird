package org.joshtommy;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayDeque;
import javax.swing.*;

public class Frame extends JPanel implements ActionListener, KeyListener {
    // controls the size of the board
    public static final int TILE_SIZE = 50;
    public static final int HEIGHT = 700;
    public static final int WIDTH = 1000;
    public static final int MAX_PIPE_JUMP = 150;
    public static final int DIST_NEXT_PIPE = 100;
    public static final int PIPES_ON_SCREEN = 6;

    // controls how many coins appear on the board

    private Timer pipeTimer;
    // objects that appear on the game board
    private final Player player;
    private final ArrayDeque<Pipe> pipes;
    private int timeElapsed;

    public Frame() {
        // set the game board size
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        // set the game board background color
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
    public void actionPerformed(ActionEvent e) {
        // this method is called by the timer every DELAY ms.
        // use this space to update the state of your game or animation
        // before the graphics are redrawn.
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
        // calling repaint() will trigger paintComponent() to run again,
        // which will refresh/redraw the graphics.
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // when calling g.drawImage() we can use "this" for the ImageObserver
        // because Component implements the ImageObserver interface, and JPanel
        // extends from Component. So "this" Board instance, as a Component, can
        // react to imageUpdate() events triggered by g.drawImage()

        // draw our graphics.
        drawBackground(g);
        timeElapsed++;
        player.draw(g, this);
        for (Pipe pipe : pipes) {
            pipe.draw(g, this);
        }
        //if (timeElapsed > PIPES_ON_SCREEN * DIST_NEXT_PIPE) {//TODO: deque not efficient (not traversable)
            int i = 1;
            Pipe middlePipe = new Pipe(0);
            for (Pipe pipe : pipes) {
                if (pipe.getPointTL().x < player.getPos().x || pipe.getPointTR().x > player.getPos().x) {
                    //under pipe in x pos
                    if (pipe.getPointTL().y > player.getPos().y || pipe.getPointBL().y < player.getPos().y) {
                        //not between gap in y
                        drawDefeatScreen(g);
                    }
                }
//                if (pipes.size() / i <= 2) {
//                    middlePipe = pipe;
//                    break;
//                }
            //}
//            if (middlePipe.getPointTL().x > player.getPos().x || middlePipe.getPointTR().x < player.getPos().x) {
//                //under pipe in x pos
//                if (middlePipe.getPointTL().y < player.getPos().y || middlePipe.getPointBL().y > player.getPos().y) {
//                    //not between gap in y
//                    drawDefeatScreen(g);
//                }
//            }
        }

        // this smooths out animations on some systems
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
        // draw a checkered background
        g.setColor(new Color(70, 190, 230));
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

    public void drawDefeatScreen(Graphics g) {
        g.setColor(new Color(70, 190, 230));
        g.drawString("Defeat", Frame.WIDTH, Frame.HEIGHT);
    }

    private void drawScore(Graphics g) {
        // set the text to be displayed
        String text = "$" + player.getScore();
        // we need to cast the Graphics to Graphics2D to draw nicer text
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        // set the text color and font
        g2d.setColor(new Color(180, 40, 150));
        g2d.setFont(new Font("Lato", Font.BOLD, 25));
        // draw the score in the bottom center of the screen
        // https://stackoverflow.com/a/27740330/4655368
        FontMetrics metrics = g2d.getFontMetrics(g2d.getFont());
        // the text will be contained within this rectangle.
        // here I've sized it to be the entire bottom row of board tiles
        Rectangle rect = new Rectangle(0, TILE_SIZE * (20 - 1), TILE_SIZE * 18, TILE_SIZE);
        // determine the x coordinate for the text
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        // determine the y coordinate for the text
        // (note we add the ascent, as in java 2d 0 is top of the screen)
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        // draw the string
        g2d.drawString(text, x, y);
    }
}
