package org.joshtommy;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Player {

    // image that represents the player's position on the board
    BufferedImage image;
    // current position of the player on the board grid
    Point pos;
    //gravity speed; positive to move down
    int gravityF = 1;
    //side length of the player square
    int playerSize = 30;
    //speed change on jump
    int bounce = -18;
    //keeps track of the players speed which gets subtracted each tick
    private int speed = 0;
    // keep track of the player's score

    public Player() {
        // load the assets
        loadImage();

        // initialize the state
        pos = new Point(Frame.WIDTH / 2, Frame.HEIGHT / 2);
    }

    private void loadImage() { //TODO: image not loading temporarily using rectangle
        try {
            image = ImageIO.read(new File("resources/player.png"));
        } catch (IOException exc) {
            System.out.println("Error opening image file: " + exc.getMessage());
        }
    }

    public void draw(Graphics g, ImageObserver observer) {
//        g.drawImage(image, pos.x, pos.y, observer);//TODO: image for bird
        g.fillRect(
                pos.x,
                pos.y,
                playerSize, playerSize);
        g.setColor(new Color(200, 200, 20));
    }

    public void keyPressed(KeyEvent e) {
        //get keyboard input
        int key = e.getKeyCode();

        //space to jump;
        if (key == KeyEvent.VK_SPACE) {
            speed = bounce;
        }
    }

    //changes the players speed by a constant for a parabolic movement
    public void gravity() {
        speed += gravityF;
    }

    // called once every tick, before the repainting process happens
    public void tick() {
        gravity();
        //moves player by speed
        pos.translate(0, speed);

        // prevent the player from moving off the edge of the board vertically
        if (pos.y < 0) {
            pos.y = 0;
        } else if (pos.y >= Frame.HEIGHT - playerSize) {
            pos.y = Frame.HEIGHT - playerSize;
        }
    }

    public String getScore() {
        return String.valueOf(0);
    }

    public Point getPos() {
        return pos;
    }

}
