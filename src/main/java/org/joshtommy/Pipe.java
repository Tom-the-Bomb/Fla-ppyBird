package org.joshtommy;

import java.awt.*;

public class Pipe {
    public static final int SPACE = 200;
    public static final int PIPE_WIDTH = 60;

    private final Frame frame;

    Point pos;
    int pipeHeight;

    Pipe(Frame frame, int pipeHeight) {
        this.frame = frame;
        pos = new Point(Frame.WIDTH + PIPE_WIDTH ,0);
        this.pipeHeight = pipeHeight;
    }
    public void draw(Graphics g) {
        //top pipe
        g.fillRect(
                pos.x,
                pos.y,
                PIPE_WIDTH,
                pipeHeight);
        //bottom pipe
        g.fillRect(
                pos.x,
                pipeHeight + SPACE,
                PIPE_WIDTH,
                Frame.HEIGHT);
        g.setColor(new Color(23, 227, 29));
    }

    public void tick() {
        //moves pipe
        pos.translate(-frame.getPipeSpeed(), 0);
    }

    //T = top, B = bottom, L = left, r = right
    public Point getPointTL() {
        return new Point(pos.x, pipeHeight);
    }
    public Point getPointTR() {
        return new Point(pos.x + PIPE_WIDTH, pipeHeight);
    }
    public Point getPointBL() {
        return new Point(pos.x, pipeHeight + SPACE);
    }
}
