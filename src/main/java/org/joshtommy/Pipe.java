package org.joshtommy;

import java.awt.*;
import java.awt.image.ImageObserver;

public class Pipe {
    public static final int SPACE = 200;
    public static final int PIPE_WIDTH = 60;
    public static int SPEED = 5;//positive number for left movement

    Point pos;
    int pipeHeight;

    Pipe(int pipeHeight) {
        pos = new Point(Frame.WIDTH + PIPE_WIDTH ,0);
        this.pipeHeight = pipeHeight;
    }
    public void draw(Graphics g, ImageObserver observer) {
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
        pos.translate(-SPEED, 0);
    }

    //T = top; B = bottom; L = left; r = right;
    public Point getPointTL() {
        return new Point(pos.x, pipeHeight);
    }
    public Point getPointTR() {
        return new Point(pos.x + PIPE_WIDTH, pipeHeight);
    }
    public Point getPointBL() {
        return new Point(pos.x, pipeHeight + SPACE);
    }
    public Point getPointBR() {
        return new Point(pos.x + PIPE_WIDTH, pipeHeight + SPACE);
    }
}
