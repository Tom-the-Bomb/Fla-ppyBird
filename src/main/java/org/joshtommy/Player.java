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
    int gravityF = 1;
    int playerSize = 30;
    int bounce = -18;
    private int speed = 0;
    // keep track of the player's score

    public Player() {
        // load the assets
        loadImage();

        // initialize the state
        pos = new Point(Frame.WIDTH/2, Frame.HEIGHT/2);
    }

    private void loadImage() { //TODO: image not loading temporarily using rectangle
        try {
            // you can use just the filename if the image file is in your
            // project folder, otherwise you need to provide the file path.
            image = ImageIO.read(new File("resources/player.png"));
        } catch (IOException exc) {
            System.out.println("Error opening image file: " + exc.getMessage());
        }
    }

    public void draw(Graphics g, ImageObserver observer) {
        // with the Point class, note that pos.getX() returns a double, but
        // pos.x reliably returns an int. https://stackoverflow.com/a/30220114/4655368
        // this is also where we translate board grid position into a canvas pixel
        // position by multiplying by the tile size.
//        g.drawImage(image, pos.x, pos.y, observer);
        g.fillRect(
                pos.x,
                pos.y,
                playerSize, playerSize);
        g.setColor(new Color(200,200,20));
    }

    public void keyPressed(KeyEvent e) {
        // every keyboard get has a certain code. get the value of that code from the
        // keyboard event so that we can compare it to KeyEvent constants
        int key = e.getKeyCode();

        // depending on which arrow key was pressed, we're going to move the player by
        // one whole tile for this input
        if(key == KeyEvent.VK_SPACE) {
            speed = bounce;
       }
    }

    public void gravity() {
      speed += gravityF;
    }

    public void tick() {
        //changes speed by a constant
        gravity();
        //moves player by speed
        pos.translate(0,speed);
        // this gets called once every tick, before the repainting process happens.
        // so we can do anything needed in here to update the state of the player.

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
