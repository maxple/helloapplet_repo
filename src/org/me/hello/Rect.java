package org.me.hello;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

class Rect {

    private int xLeftUpCorner, yLeftUpCorner, width, height, delta;
    private Color color;

    public Rect(int xLeftUpCorner, int yLeftUpCorner, int width, int height, Color color, int delta) {

        this.xLeftUpCorner = xLeftUpCorner;
        this.yLeftUpCorner = yLeftUpCorner;
        this.width = width;
        this.height = height;
        this.color = color;
        this.delta = delta;
    }

    void setSize(int width, int height) {

        this.width = width;
        this.height = height;
    }

    void enlarge(int d) {
        
        width += d;
    }
    
    void setWidth(int width) {
        
        this.width = width;
    }

    void draw(Graphics g) {

        g.setColor(color);
        g.fillRect(xLeftUpCorner, yLeftUpCorner, width, height);
    }

    void moveDown(Dimension dim) {

        if ((yLeftUpCorner + height + delta) < dim.height) {
            yLeftUpCorner += delta;
        }
    }

    void moveUp() {

        if ((yLeftUpCorner - delta) > 0) {
            yLeftUpCorner -= delta;
        }
    }

    void moveRight(Dimension dim) {

        if ((xLeftUpCorner + width + delta) < dim.width) {
            xLeftUpCorner += delta;
        }
    }

    void moveLeft() {

        if ((xLeftUpCorner - delta) > 0) {
            xLeftUpCorner -= delta;
        }
    }
}
