
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Pipe {
    public static final int SPACE = 250;
    public static final int PIPE_WIDTH = 100;

    private final Frame frame;
    private BufferedImage image;
    private BufferedImage inverted;

    Point pos;
    int pipeHeight;

    Pipe(Frame frame, int pipeHeight) {
        loadImage();
        this.frame = frame;
        pos = new Point(Frame.WIDTH + PIPE_WIDTH, 0);
        this.pipeHeight = pipeHeight;
    }

    private void loadImage() {
        try {
            URL fileURL = getClass().getResource("/darkPipe.png");
            assert fileURL != null;
            image = ImageIO.read(new File(fileURL.getFile()));
            URL fileURL2 = getClass().getResource("/darkPipeInverted.png");
            assert fileURL2 != null;
            inverted = ImageIO.read(new File(fileURL2.getFile()));
            //TODO: make image svg and scalable
//          Image tmp = image.getScaledInstance(PIPE_WIDTH, pipeHeight, Image.SCALE_SMOOTH)
//          BufferedImage img = new BufferedImage(PIPE_WIDTH, pipeHeight, BufferedImage.TYPE_INT_ARGB)

            assert image != null;
            Graphics2D g2d = image.createGraphics();
            assert inverted != null;
            Graphics2D g2d2 = inverted.createGraphics();
            g2d.drawImage(image, 0, 0, null);
            g2d2.drawImage(inverted, 0, 0, null);
            g2d.dispose();
            g2d2.dispose();
        } catch (IOException exc) {
            Logger.log("Error opening image file: " + exc.getMessage());
        }
    }

    public void draw(Graphics g, ImageObserver observer) {
        //top pipe
        g.drawImage(image, pos.x, pos.y - image.getHeight() + pipeHeight, observer);
        //bottom pipe
        g.drawImage(inverted, pos.x, pipeHeight + SPACE, observer);
    }

    public void tick() {
        //moves pipe
        pos.translate(-frame.getPipeSpeed(), 0);
    }

    private static final int BUFFER_UP = 20;
    private static final int BUFFER_DOWN = 9;

    //T = top, B = bottom, L = left, r = right
    public Point getPointTL() {
        return new Point(pos.x + 30, pipeHeight - BUFFER_UP);
    }

    public Point getPointTR() {
        return new Point(pos.x + 30 + PIPE_WIDTH, pipeHeight - BUFFER_UP);
    }

    public Point getPointBL() {
        return new Point(pos.x + 30, pipeHeight + SPACE + BUFFER_DOWN);
    }
}
