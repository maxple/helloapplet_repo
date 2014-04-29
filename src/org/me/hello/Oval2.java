package org.me.hello;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

class Oval2 {

    private final Color color;
    private Double x, y, r, velocity, angle, dx, dy;
    private Integer xi, yi, ri;

    public Oval2(int xi, int yi, int ri, Color color) {

        this.x = (double) xi;
        this.y = (double) yi;
        this.r = (double) ri;
        
        this.xi = xi;
        this.yi = yi;
        this.ri = ri;
        
        this.color = color;
        
        dx = 1.0;
        dy = 1.0;
        velocity = 1.0;
        angle = 0.0;
    }

    void setIntCoords() {
        xi = x.intValue();
        yi = y.intValue();
        ri = r.intValue();
    }

    void draw(Graphics g) {
        
        setIntCoords();

        g.setColor(color);
        g.fillOval(xi - ri, yi - ri, ri * 2, ri * 2);
        /*g.drawString("v=" + velocity.toString(), xi, yi);
        g.drawString("a=" + angle.toString(), xi, yi + 10);
        g.drawString("dx=" + dx.toString(), xi, yi + 20);
        g.drawString("dy=" + dy.toString(), xi, yi + 30);*/
    }

    void setVelocity(double velocity) {

        this.velocity = velocity;
        setTrajectory();
    }

    double getVelocity() {
        return velocity;
    }

    void setAngle(double angle) {

        this.angle = angle;
        setTrajectory();
    }

    double getAngle() {
        return angle;
    }

    void setTrajectory() {
        dx = velocity * Math.cos(Math.toRadians(angle));
        dy = velocity * Math.sin(Math.toRadians(-angle));
    }

    void move(Dimension memImageDim) {

        /*if ((x - r + dx < 0) || (x + r + dx > memImageDim.width)) {
         dx = -dx;
         }
         if ((y - r + dy < 0) || (y + r + dy > memImageDim.height)) {
         dy = -dy;
         }*/
        resolveWallCollision(memImageDim);

        x += dx;
        y += dy;
    }

    void resolveWallCollision(Dimension memImageDim) {
        if ((x - r + dx < 0) || (x + r + dx > memImageDim.width)) {
            angle = 180 - angle;
            setTrajectory();
        }
        if ((y - r + dy < 0) || (y + r + dy > memImageDim.height)) {
            angle = 360 - angle;
            setTrajectory();
        }
    }
}
