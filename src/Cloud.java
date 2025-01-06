
import java.awt.*;

public class Cloud {
    public static final int CLOUD_HEIGHT = 20;
    public static final int CLOUD_WIDTH = 50;

    private final int scaleFactor;
    private final int speed;
    private final Point pos;

    Cloud(int speed, int scaleFactor, Point pos) {
        this.pos = pos;
        this.speed = speed;
        this.scaleFactor = scaleFactor;
    }

    public void draw(Graphics g) {
        // draw the cloud (rectangle shape + white color)
        g.fillRect(
            pos.x,
            pos.y,
            CLOUD_WIDTH * scaleFactor,
            CLOUD_HEIGHT * scaleFactor
        );
        g.setColor(
            new Color(255, 255, 255)
        );
    }

    public void tick() {
        // moves the cloud horizontally: updates its position every frame (periodically)
        pos.translate(-speed, 0);
    }

    public Point getPos() {
        return pos;
    }
}