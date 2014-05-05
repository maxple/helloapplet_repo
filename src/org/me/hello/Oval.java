package org.me.hello;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

class Oval {

    private Color color;
    private int x, y, velocity, angle, velocityX, velocityY;
    private final int r, friction, precision, number;
    private boolean free;

    public Oval(int x, int y, int r, Color color, int friction, int precision, int number) {

        this.precision = precision;

        this.x = x << precision;
        this.y = y << precision;
        this.r = r << precision;
        this.color = color;
        this.friction = friction;
        this.number = number;

        velocityX = 0;
        velocityY = 0;
        velocity = 0;
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

        int ydim = memImageDim.height - 1 - getY();
        //int shiftY = 0;

        g.setColor(color);

        g.fillOval(getX() - getR(), ydim - getR(), getR() * 2, getR() * 2);
        
        g.setColor(Color.white);
        
        g.drawString(Integer.toString(number), getX(), ydim);
        
        //g.drawString("x=" + Integer.toString(x), getX(), ydim + shiftY++*10);
        //g.drawString("ydim=" + Integer.toString(y), getX(), ydim + shiftY++*10);
        //g.drawString("r=" + Integer.toString(r), getX(), ydim + shiftY++*10);
        //g.drawString("v=" + Integer.toString(velocity), getX(), ydim + shiftY++ * 10);
        //g.drawString("a=" + Integer.toString(angle), getX(), ydim + shiftY++*10);
        //g.drawString("velocityX=" + Integer.toString(velocityX), getX(), ydim + shiftY++*10);
        //g.drawString("velocityY=" + Integer.toString(velocityY), getX(), ydim + shiftY++*10);
        //g.drawString("free=" + Boolean.toString(free), getX(), ydim + shiftY++*10);
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

        applyFriction();
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

    void applyFriction() {

        double k = (double) friction + (double) getR();

        k = k / 5000 + 1;

        velocity /= k;

        decompVelocity();
    }

    void moveForward(Dimension memImageDim) {
        x += velocityX;
        y += velocityY;
    }

    void moveBack(Dimension memImageDim) {
        x -= velocityX;
        y -= velocityY;
    }
}
