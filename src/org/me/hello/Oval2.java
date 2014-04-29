package org.me.hello;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

class Oval2 {

    private final Color color;
    private int x, y, r, velocity, angle, dx, dy;

    public Oval2(int x, int y, int r, Color color) {

        this.x = x * 10;
        this.y = y * 10;
        this.r = r * 10;
        this.color = color;

        dx = 1;
        dy = 1;
        velocity = 1;
        angle = 0;
    }

    int getX() {
        return x / 10;
    }

    int getY() {
        return y / 10;
    }

    int getR() {
        return r / 10;
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
        dx = (int) (10 * velocity * Math.cos(Math.toRadians(angle)));
        dy = (int) (10 * velocity * Math.sin(Math.toRadians(-angle)));
    }

    void move(Dimension memImageDim) {

        resolveWallCollision(memImageDim);

        x += dx;
        y += dy;
    }

    void resolveWallCollision(Dimension memImageDim) {
        if ((x - r + dx < 0) || (x + r + dx > memImageDim.width * 10)) {
            angle = 180 - angle;
            setTrajectory();
        }
        if ((y - r + dy < 0) || (y + r + dy > memImageDim.height * 10)) {
            angle = 360 - angle;
            setTrajectory();
        }
    }
}
