package org.joshAndTommy;

import java.awt.*;
import java.awt.image.ImageObserver;

public class Pipe {
    public static final int PIPE_WIDTH = 60;
    Point pos;
    int pipeHeight;
    int space = 120;
    public static final int SPEED = 3;//positive number for left movement

    Pipe(int pipeHeight) {
        pos = new Point(Frame.WIDTH + PIPE_WIDTH ,0);
        this.pipeHeight = pipeHeight;
    }
    public void draw(Graphics g, ImageObserver observer) {
        g.fillRect(
                pos.x,
                pos.y,
                PIPE_WIDTH,
                pipeHeight);
        g.fillRect(
                pos.x,
                pipeHeight + space,
                PIPE_WIDTH,
                Frame.HEIGHT);
        g.setColor(new Color(23, 227, 29));
    }

    public void tick() {
        //moves pipe
        pos.translate(-SPEED, 0);
    }

    public Point getPointTL() {
        return new Point(pos.x, pipeHeight);
    }
    public Point getPointTR() {
        return new Point(pos.x + PIPE_WIDTH, pipeHeight);
    }
    public Point getPointBL() {
        return new Point(pos.x, pipeHeight + space);
    }
    public Point getPointBR() {
        return new Point(pos.x + PIPE_WIDTH, pipeHeight + space);
    }
}
