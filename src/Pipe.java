
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

public class Pipe {
    public static final int SPACE = 250;
    public static final int PIPE_WIDTH = 100;

    private static final int BUFFER_UP = 20;
    private static final int BUFFER_DOWN = 9;

    private final Frame frame;
    private BufferedImage uprightPipe;
    private BufferedImage invertedPipe;

    public Point pos;
    int pipeHeight;

    public Pipe(Frame frame, int pipeHeight) {
        this.frame = frame;
        this.pipeHeight = pipeHeight;

        loadImage();

        pos = new Point(Frame.WIDTH + PIPE_WIDTH, 0);
    }

    private void loadImage() {
        try {
            uprightPipe = ImageIO.read(new File(
                getClass().getResource("/resources/darkPipe.png").getFile()
            ));
            invertedPipe = ImageIO.read(new File(
                getClass().getResource("/resources/darkPipeInverted.png").getFile()
            ));

            Graphics2D g2d = uprightPipe.createGraphics();
            Graphics2D g2d2 = invertedPipe.createGraphics();

            g2d.drawImage(uprightPipe, 0, 0, null);
            g2d2.drawImage(invertedPipe, 0, 0, null);

            g2d.dispose();
            g2d2.dispose();
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }

    public void draw(Graphics g, ImageObserver observer) {
        // top pipe
        g.drawImage(
            uprightPipe,
            pos.x,
            pos.y - uprightPipe.getHeight() + pipeHeight,
            observer
        );
        // bottom pipe
        g.drawImage(
            invertedPipe,
            pos.x,
            pipeHeight + SPACE,
            observer
        );
    }

    public void tick() {
        // moves the pipe horizontally: updates its position every frame (periodically)
        pos.translate(-frame.getPipeSpeed(), 0);
    }

    public Point getPointTopLeft() {
        return new Point(pos.x + 30, pipeHeight - BUFFER_UP);
    }

    public Point getPointTopRight() {
        return new Point(pos.x + 30 + PIPE_WIDTH, pipeHeight - BUFFER_UP);
    }

    public Point getPointBottomLeft() {
        return new Point(pos.x + 30, pipeHeight + SPACE + BUFFER_DOWN);
    }
}