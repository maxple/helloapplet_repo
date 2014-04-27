package org.me.hello;

import java.awt.Color;
import java.awt.Graphics;

class Rect2 {

    int x, y, width, height;
    Color color;

    public Rect2(int x, int y, int width, int height, Color color) {

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

    void clear(Graphics g) {

        g.setColor(Color.WHITE);
        g.fillRect(x, y, width, height);
    }

    void moveDown(Graphics g, int d, int lim) {
        
        clear(g);

        if ((y + height + d) < lim) {
            y += d;
        }
        
        draw(g);
    }

    void moveUp(Graphics g, int d, int lim) {
        
        clear(g);

        if ((y - d) > lim) {
            y -= d;
        }
        
        draw(g);
    }

    void moveRight(Graphics g, int d, int lim) {
        
        clear(g);

        if ((x + width + d) < lim) {
            x += d;
        }
        
        draw(g);
    }

    void moveLeft(Graphics g, int d, int lim) {
        
        clear(g);

        if ((x - d) > lim) {
            x -= d;
        }
        
        draw(g);
    }
}
