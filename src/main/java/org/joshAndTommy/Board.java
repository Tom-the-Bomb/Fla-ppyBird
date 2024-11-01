package org.joshAndTommy;

import java.awt.*;
import java.awt.event.*;
import java.security.PublicKey;
import java.util.ArrayDeque;
import javax.swing.*;

public class Board extends JPanel implements ActionListener, KeyListener {

    // controls the delay between each tick in ms
    private final int DELAY = 25;
    private final int PIPE_DELAY = 200;
    // controls the size of the board
    public static final int TILE_SIZE = 50;
    public static final int HEIGHT = 700;
    public static final int WIDTH = 1000;
    public static final int MAX_PIPE_JUMP = 150;
    // controls how many coins appear on the board

    private Timer timer;
    private Timer pipeTimer;
    // objects that appear on the game board
    private final Player player;
    private final ArrayDeque<Pipe> pipes;
    private int timeElapsed;

    public Board() {
        // set the game board size
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        // set the game board background color
        setBackground(new  Color(32, 168, 244));

        // initialize the game state
        player = new Player();
        pipes = new ArrayDeque<>();
        timeElapsed = 0;

        // this timer will call the actionPerformed() method every DELAY ms
        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // this method is called by the timer every DELAY ms.
        // use this space to update the state of your game or animation
        // before the graphics are redrawn.
        if(timeElapsed % 100 == 0) {
            int min;
            int max;
            if (!pipes.isEmpty()) {
                min = Math.max(pipes.getLast().pipeHeight - MAX_PIPE_JUMP, MAX_PIPE_JUMP);
                max = Math.min(pipes.getLast().pipeHeight + MAX_PIPE_JUMP, Board.HEIGHT - MAX_PIPE_JUMP);
            } else {
                min = MAX_PIPE_JUMP;
                max = Board.HEIGHT - MAX_PIPE_JUMP;
            }
            int random = (int) (Math.random() * (max - min + 1)) + min;
            pipes.add(new Pipe(random));
            if (pipes.getFirst().pos.x <= 0) {
                pipes.removeFirst();
            }

        }
        // prevent the player from disappearing off the board
        player.tick();
        for (Pipe pipe: pipes) {
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
        for (Pipe pipe: pipes) {
            pipe.draw(g, this);
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

    private void drawScore(Graphics g) {
        // set the text to be displayed
        String text = "$" + player.getScore();
        // we need to cast the Graphics to Graphics2D to draw nicer text
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
