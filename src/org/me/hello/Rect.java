package org.me.hello;

import java.awt.Color;
import java.awt.Graphics;

class Rect {

    int x, y, width, height;
    Color color;

    public Rect(int x, int y, int width, int height, Color color) {

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    void draw(Graphics g) {

        g.setColor(color);
        g.fillRect(x, y, width, height);
    }

    void moveDown(int d, int lim) {

        if ((y + height + d) < lim) {
            y += d;
        }
    }

    void moveUp(int d, int lim) {

        if ((y - d) > lim) {
            y -= d;
        }
    }

    void moveRight(int d, int lim) {

        if ((x + width + d) < lim) {
            x += d;
        }
    }

    void moveLeft(int d, int lim) {

        if ((x - d) > lim) {
            x -= d;
        }
    }
}
