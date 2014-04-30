package org.me.hello;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

class Oval {

    private Color color;
    private int x, y, velocity, angle, velocityX, velocityY;
    private final int r, friction;
    private final int precision;
    private boolean free;

    public Oval(int x, int y, int r, Color color, int friction, int precision) {

        this.precision = precision;

        this.x = x << precision;
        this.y = y << precision;
        this.r = r << precision;
        this.color = color;
        this.friction = friction;

        velocityX = 1;
        velocityY = 1;
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
    
    void setFree(boolean free) {
        this.free = free;
    }
    
    boolean getFree() {
        return free;
    }

    void draw(Graphics g, Dimension memImageDim) {
        
        int y = memImageDim.height - 1 - getY();
        int shiftY = 0;
        
        g.setColor(color);
        g.fillOval(getX() - getR(), y - getR(), getR() * 2, getR() * 2);
        //g.drawString("x=" + Integer.toString(x), getX(), y + shiftY++*10);
        //g.drawString("y=" + Integer.toString(y), getX(), y + shiftY++*10);
        /*g.drawString("r=" + Integer.toString(r), getX(), y + shiftY++*10);
        g.drawString("v=" + Integer.toString(velocity), getX(), y + shiftY++*10);
        g.drawString("a=" + Integer.toString(angle), getX(), y + shiftY++*10);
        g.drawString("velocityX=" + Integer.toString(velocityX), getX(), y + shiftY++*10);
        g.drawString("velocityY=" + Integer.toString(velocityY), getX(), y + shiftY++*10);
        g.drawString("free=" + Boolean.toString(free), getX(), y + shiftY++*10);*/
    }

    void setVelocity(int velocity) {

        this.velocity = velocity;
        decompVelocity();
    }

    int getVelocity() {
        return velocity >> precision;
    }

    void setAngle(int angle) {

        this.angle = angle;
        decompVelocity();
    }

    int getAngle() {
        return angle;
    }

    void setColor(Color color) {

        this.color = color;
    }

    void decompVelocity() {
        velocityX = (int) (velocity * Math.cos(Math.toRadians(angle)));
        velocityY = (int) (velocity * Math.sin(Math.toRadians(angle)));
    }

    int getVelocityX() {
        return velocityX;
    }

    int getVelocityY() {
        return velocityY;
    }

    void move(Dimension memImageDim) {

        resolveWallCollision(memImageDim);

        x += velocityX;
        y += velocityY;

        if (friction > 0) {
            if (velocity - friction - (r >> precision) > 0) {
                velocity = velocity - friction - (r >> precision);
            } else {
                velocity = 0;
            }
        }

        decompVelocity();
    }

    void resolveWallCollision(Dimension memImageDim) {
        if ((x - r + velocityX < 0) || (x + r + velocityX > (memImageDim.width << precision))) {
            angle = 180 - angle;
            decompVelocity();
        }
        if ((y - r + velocityY < 0) || (y + r + velocityY > (memImageDim.height << precision))) {
            angle = 360 - angle;
            decompVelocity();
        }
    }
}
