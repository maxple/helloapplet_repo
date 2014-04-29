package org.me.hello;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

class Oval {

    private final Color color;
    private int x, y, velocity, angle, dx, dy;
    private final int r, friction;
    private final int precision;

    public Oval(int x, int y, int r, Color color, int friction, int precision) {
        
        this.precision = precision;

        this.x = x << precision;
        this.y = y << precision;
        this.r = r << precision;
        this.color = color;
        this.friction = friction;

        dx = 1;
        dy = 1;
        velocity = 1;
        angle = 0;
    }

    int getX() {
        return x >> precision;
    }

    int getY() {
        return y >> precision;
    }

    int getR() {
        return r >> precision;
    }
    
    void draw(Graphics g) {

        g.setColor(color);
        g.fillOval(getX() - getR(), getY() - getR(), getR() * 2, getR() * 2);
        /*g.drawString("x=" + Integer.toString(x), getX(), getY());
        g.drawString("y=" + Integer.toString(y), getX(), getY() + 10);
        g.drawString("r=" + Integer.toString(r), getX(), getY() + 20);
        g.drawString("v=" + Integer.toString(velocity), getX(), getY() + 30);
        g.drawString("a=" + Integer.toString(angle), getX(), getY() + 40);
        g.drawString("dx=" + Integer.toString(dx), getX(), getY() + 50);
        g.drawString("dy=" + Integer.toString(dy), getX(), getY() + 60);*/
    }

    void setVelocity(int velocity) {

        this.velocity = velocity;
        setTrajectory();
    }

    double getVelocity() {
        return velocity;
    }

    void setAngle(int angle) {

        this.angle = angle;
        setTrajectory();
    }

    double getAngle() {
        return angle;
    }

    void setTrajectory() {
        dx = (int) (velocity * Math.cos(Math.toRadians(angle)));
        dy = (int) (velocity * Math.sin(Math.toRadians(-angle)));
    }

    void move(Dimension memImageDim) {

        resolveWallCollision(memImageDim);

        x += dx;
        y += dy;
        
        if (velocity - friction > 0) velocity -= friction;
        else velocity = 0;
        
        setTrajectory();
    }

    void resolveWallCollision(Dimension memImageDim) {
        if ((x - r + dx < 0) || (x + r + dx > (memImageDim.width << precision))) {
            angle = 180 - angle;
            setTrajectory();
        }
        if ((y - r + dy < 0) || (y + r + dy > (memImageDim.height << precision))) {
            angle = 360 - angle;
            setTrajectory();
        }
    }
}
