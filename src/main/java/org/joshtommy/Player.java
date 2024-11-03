package org.joshtommy;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Player {

    // image that represents the player's position on the board
    BufferedImage image;
    // current position of the player on the board grid
    Point pos;
    //gravity speed; positive to move down
    public static final int GRAVITY_SPEED = 1;
    //side length of the player square
    public static final int PLAYER_SIZE = 30;
    //speed change on jump
    private static final int JUMP_HEIGHT = -16;
    //keeps track of the players speed which gets subtracted each tick
    private int speed = 0;
    // keep track of the player's score

    public Player() {
        // load the assets
        loadImage();

        // initialize the state
        pos = new Point(Frame.WIDTH / 2, Frame.HEIGHT / 2);
    }

    //TODO: image not loading temporarily using rectangle
    private void loadImage() {
        try {
            image = ImageIO.read(new File("resources/player.png"));
        } catch (IOException exc) {
            Logger.log("Error opening image file: " + exc.getMessage());
        }
    }

    public void draw(Graphics g) {
        //TODO: image for bird
        g.fillRect(
                pos.x,
                pos.y,
                PLAYER_SIZE, PLAYER_SIZE);
        g.setColor(new Color(200, 200, 20));
    }

    public void keyPressed(KeyEvent e) {
        //get keyboard input
        int key = e.getKeyCode();

        //space to jump
        if (key == KeyEvent.VK_SPACE) {
            speed = JUMP_HEIGHT;
        }
    }

    //changes the players speed by a constant for a parabolic movement
    public void gravity() {
        speed += GRAVITY_SPEED;
    }

    // called once every tick, before the repainting process happens
    public void tick() {
        gravity();
        //moves player by speed
        pos.translate(0, speed);

        // prevent the player from moving off the edge of the board vertically
        if (pos.y < 0) {
            pos.y = 0;
        } else if (pos.y >= Frame.HEIGHT - PLAYER_SIZE) {
            pos.y = Frame.HEIGHT - PLAYER_SIZE;
        }
    }

    public Point getPos() {
        return pos;
    }

    public void reset() {
        pos = new Point(Frame.WIDTH / 2, Frame.HEIGHT / 2);
        speed = 0;
    }
}
