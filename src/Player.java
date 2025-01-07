
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

public class Player {
    // image that represents the player's position on the board
    private BufferedImage image;
    // current position of the player on the board grid
    private Point pos;
    // gravity speed; positive to move down
    public static final int GRAVITY_SPEED = 1;
    // side length of the player square
    public static final int PLAYER_SIZE = 45;
    // speed change on jump
    private static final int JUMP_HEIGHT = -16;
    // keeps track of the players speed which gets subtracted each tick
    private int speed = 0;
    // image gets rotated around this point
    private int rotationPointX;
    private int rotationPointY;
    // keep track of the player's score

    public Player() {
        // load the assets
        loadImage();
        // initialize the state
        pos = new Point(Panel.WIDTH / 2, Panel.HEIGHT / 2);
    }

    private void loadImage() {
        try {
            // Load image:
            //
            // <https://stackoverflow.com/questions/299495/how-to-add-an-image-to-a-jpanel>
            //
            image = ImageIO.read(new File(
                getClass().getResource("/resources/darkBird.png").getFile()
            ));
            Image tmp = image.getScaledInstance(PLAYER_SIZE, PLAYER_SIZE, Image.SCALE_SMOOTH);
            BufferedImage img = new BufferedImage(PLAYER_SIZE, PLAYER_SIZE, BufferedImage.TYPE_INT_ARGB);

            Graphics2D g2d = img.createGraphics();
            g2d.drawImage(tmp, 0, 0, null);
            g2d.dispose();
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }

    public void draw(Graphics g, ImageObserver observer) {
        // rotate player as it flies up and down
        //
        // <https://stackoverflow.com/questions/20275424/rotating-image-with-affinetransform>
        //
        double rotationInRad = Math.toRadians(Math.min(speed * 3, 20));
        rotationPointX = PLAYER_SIZE / 2;
        rotationPointY = PLAYER_SIZE / 2;
        AffineTransform tx = AffineTransform.getRotateInstance(rotationInRad, rotationPointX, rotationPointY);

        Image tmp = image.getScaledInstance(PLAYER_SIZE, PLAYER_SIZE, Image.SCALE_SMOOTH);
        BufferedImage img = new BufferedImage(PLAYER_SIZE, PLAYER_SIZE, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = img.createGraphics();
        g2d.drawImage(tmp, tx, observer);
        g2d.dispose();

        g.drawImage(img, pos.x, pos.y, observer);
    }

    public void keyPressed(KeyEvent e) {
        // press space to jump
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            speed = JUMP_HEIGHT;
        }
    }

    // called once every tick, before the repainting process happens
    public void tick() {
        // changes the players speed by a constant for a parabolic movement
        speed += GRAVITY_SPEED;
        // moves player by speed
        pos.translate(0, speed);

        // prevent the player from moving off the edge of the board vertically
        if (pos.y < 0) {
            pos.y = 0;
        } else if (pos.y >= Panel.HEIGHT - PLAYER_SIZE) {
            pos.y = Panel.HEIGHT - PLAYER_SIZE;
        }
    }

    public Point getPose() {
        return pos;
    }

    public void reset() {
        pos = new Point(Panel.WIDTH / 2, Panel.HEIGHT / 2);
        speed = 0;
    }
}
