package org.me.hello;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

public class Rect {

    int xLeftUpCorner, yLeftUpCorner, width, height, delta;
    Color color;

    public Rect(int xLeftUpCorner, int yLeftUpCorner, int width, int height, Color color, int delta) {

        this.xLeftUpCorner = xLeftUpCorner;
        this.yLeftUpCorner = yLeftUpCorner;
        this.width = width;
        this.height = height;
        this.color = color;
        this.delta = delta;
    }

    public void draw(Graphics g) {

        g.setColor(color);
        g.fillRect(xLeftUpCorner, yLeftUpCorner, width, height);
    }

    public void moveDown(Dimension dim) {

        if ((yLeftUpCorner + height + delta) < dim.height) {
            yLeftUpCorner += delta;
        }
    }

    public void moveUp() {

        if ((yLeftUpCorner - delta) > 0) {
            yLeftUpCorner -= delta;
        }
    }

    public void moveRight(Dimension dim) {

        if ((xLeftUpCorner + width + delta) < dim.width) {
            xLeftUpCorner += delta;
        }
    }

    public void moveLeft() {

        if ((xLeftUpCorner - delta) > 0) {
            xLeftUpCorner -= delta;
        }
    }
}
