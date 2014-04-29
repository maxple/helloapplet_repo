package org.me.hello;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

class Oval2 {

    private final int r;
    private int x, y, dx, dy;
    private final Color color;
    private double velocity, angle;

    public Oval2(int x, int y, int weight, Color color) {

        this.x = x;
        this.y = y;
        this.r = weight;
        this.color = color;
        dx = 1;
        dy = 1;
    }

    void draw(Graphics g) {

        g.setColor(color);
        g.fillOval(x - r, y - r, r * 2, r * 2);
    }

    void setTrajectory(double velocity, double angle) {
        
        this.velocity = velocity;
        this.angle = angle;

        dx = (int) (velocity * Math.cos(Math.toRadians(angle)));
        dy = (int) (velocity * Math.sin(Math.toRadians(angle)));
    }

    void move(Dimension memImageDim) {

        if ((x - r + dx < 0) || (x + r + dx > memImageDim.width)) {
            dx = -dx;
        }
        if ((y - r + dy < 0) || (y + r + dy > memImageDim.height)) {
            dy = -dy;
        }

        x += dx;
        y += dy;
    }
    
    double getVelocity() {
        return velocity;
    }
    
    double getAngle() {
        return angle;
    }
}
