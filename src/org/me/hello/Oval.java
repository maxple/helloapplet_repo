package org.me.hello;

import java.awt.Color;
import java.awt.Graphics;

public class Oval {
    
    int x, y, r; // Position and radius of the circle
    int dx, dy; // Trajectory of circle    
    Color color;
    int dest;

    public Oval(int x, int y, int dx, int dy, int r, Color color) {

        this.x = x;
        this.y = y;
        this.r = r;
        this.dx = dx;
        this.dy = dy;
        this.color = color;
        dest = 1;
    }

    public void draw(Graphics g) {

        g.setColor(color);
        g.fillOval(x - r, y - r, r * 2, r * 2);
    }
}
