
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

public class Pipe {
    // height of space between top and bottom pipes
    public static final int SPACE = 180;
    // width of pipes
    public static final int PIPE_WIDTH = 100;
    // height of the triangular tip of the bottom pipe
    public static final int BOTTOM_TRIANGLE_HEIGHT = 148;
    // height of the triangular tip of the top pipe
    public static final int TOP_TRIANGLE_HEIGHT = 171;

    private final Panel frame;
    private BufferedImage uprightPipe;
    private BufferedImage invertedPipe;

    private Point pos;
    private int pipeHeight;

    public Pipe(Panel frame, int pipeHeight) {
        this.frame = frame;
        this.pipeHeight = pipeHeight;

        loadImage();

        // position of the pipe starts right off the screen's right side
        pos = new Point(Panel.WIDTH + PIPE_WIDTH, 0);
    }

    private void loadImage() {
        try {
            uprightPipe = ImageIO.read(new File(
                getClass().getResource("/resources/darkPipe.png").getFile()
            ));
            invertedPipe = ImageIO.read(new File(
                getClass().getResource("/resources/darkPipeInverted.png").getFile()
            ));
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

    public Point getPose() {
        return pos;
    }

    public int getPipeHeight() {
        return pipeHeight;
    }

    public void tick() {
        // moves the pipe horizontally: updates its position every frame (periodically)
        pos.translate(-frame.getPipeSpeed(), 0);
    }

    // Pipe structure:
    // |   |
    // A   C
    //  * *
    //   B
    //
    //   E
    //  * *
    // D   F
    // |   |
    //
    // (A = TL, B = TM, C = TR, D = BL, E = BM, F = BR)
    //
    public Point getPointTL() {
        return new Point(pos.x + 5, pipeHeight - TOP_TRIANGLE_HEIGHT);
    }

    public Point getPointTM() {
        return new Point(pos.x + PIPE_WIDTH / 2, pipeHeight);
    }

    public Point getPointTR() {
        return new Point(pos.x + PIPE_WIDTH + 5, pipeHeight - TOP_TRIANGLE_HEIGHT);
    }

    public Point getPointBL() {
        return new Point(pos.x + 5, pipeHeight + SPACE + BOTTOM_TRIANGLE_HEIGHT);
    }

    public Point getPointBM() {
        return new Point(pos.x + PIPE_WIDTH / 2, pipeHeight + SPACE);
    }

    public Point getPointBR() {
        return new Point(pos.x + PIPE_WIDTH + 5, pipeHeight + SPACE + BOTTOM_TRIANGLE_HEIGHT);
    }
}