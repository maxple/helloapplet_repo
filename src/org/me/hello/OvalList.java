package org.me.hello;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author pletnyov
 */
class OvalList {

    private final int R_MIN = 50;
    private final int R_MAX = 50;

    private final int VELOCITY = 1;

    private final int ANGLE_MIN = 0;
    private final int ANGLE_MAX = 360;

    private final int FRICTION = 0;

    private final int PRECISION = 10;

    private final ArrayList<Oval> list;

    Random rnd;

    boolean saturation;

    int nextX, nextY, nextAngle, debugCounter = 0;

    private final int INIT_X = 110;
    private final int INIT_Y = 110;

    int dx, dy;

    public OvalList() {
        list = new ArrayList<>();
        rnd = new Random();
    }

    void addRndItem(Dimension memImageDim) {

        if (saturation == false) {
            Oval oval = new Oval(nextX, nextY,
                getNextRnd(R_MIN, R_MAX), Color.red, FRICTION, PRECISION);

            oval.setAngle(nextAngle);
            list.add(oval);

            nextX += INIT_X;
            //nextY += INIT_Y / 3;
            if (nextX + R_MAX > memImageDim.width) {
                nextX = INIT_X;
                nextY += INIT_Y;
                if (nextY + R_MAX > memImageDim.height) {
                    saturation = true;
                }
            }

            nextAngle += 90;
        }
    }

    void createRndItemGroup(Dimension memImageDim, int quantity) {

        nextX = INIT_X;
        nextY = INIT_Y;
        nextAngle = 0;
        saturation = false;

        list.clear();

        while (quantity-- > 0) {
            addRndItem(memImageDim);
        }

        startAll(VELOCITY << PRECISION);
    }

    void startAll(int velocity) {

        for (int i1 = 0; i1 < list.size(); i1++) {
            if (i1 % 2 == 0) {
                list.get(i1).setVelocity(velocity);
            } else {
                list.get(i1).setVelocity(0);
            }

            //list.get(i1).setVelocity(velocity);
            //list.get(i1).setAngle(getNextRnd(ANGLE_MIN, ANGLE_MAX));
        }
    }

    void moveAll(Dimension memImageDim) {

        for (Oval oval : list) {
            oval.move(memImageDim);
        }
    }

    void drawAll(Graphics memImageGraphics, Dimension memImageDim) {

        for (Oval oval : list) {
            oval.draw(memImageGraphics, memImageDim);
        }
    }

    int getNextRnd(int min, int max) {

        int rn;

        if (max == min) {
            return max;
        } else if (max < min) {
            return 0;
        } else {
            do {
                rn = rnd.nextInt(max - min) + min;
            } while (rn == 0);
            return rn;
        }
    }

    void resolveMutualCollision(Dimension memImageDim) {

        int alpha, vx1, vy1, vx2, vy2, temp;
        Oval oval1, oval2;
        //boolean oval1free;

        debugCounter++;

        for (int i1 = 0; i1 < list.size(); i1++) {

            oval1 = list.get(i1);

            //oval1free = true;

            for (int i2 = i1 + 1; i2 < list.size(); i2++) {

                oval2 = list.get(i2);

                if (checkMutualCollision(oval1, oval2)) {

                    //if ((oval1.getFree() == true) || (oval2.getFree() == true)) {

                        alpha = (int) Math.toDegrees(Math.atan2(dy, dx));

                        oval1.setAngle(oval1.getAngle() - alpha);
                        oval2.setAngle(oval2.getAngle() - alpha);

                        vx1 = oval1.getVelocityX();
                        vy1 = oval1.getVelocityY();
                        vx2 = oval2.getVelocityX();
                        vy2 = oval2.getVelocityY();

                        temp = vx1;
                        vx1 = vx2;
                        vx2 = temp;

                        //if (oval1.getFree() == true) {
                            oval1.setVelocity((int) Math.sqrt(vx1 * vx1 + vy1 * vy1));
                            if ((vx1 == 0) && (vy1 == 0)) {
                                oval1.setAngle(alpha);
                            } else {
                                oval1.setAngle((int) Math.toDegrees(Math.atan2(vy1, vx1)) + alpha);
                            }
                        //}
                        //if (oval2.getFree() == true) {
                            oval2.setVelocity((int) Math.sqrt(vx2 * vx2 + vy2 * vy2));
                            if ((vx2 == 0) && (vy2 == 0)) {
                                oval2.setAngle(alpha);
                            } else {
                                oval2.setAngle((int) Math.toDegrees(Math.atan2(vy2, vx2)) + alpha);
                            }
                        //}
                    //}

                    //oval1free = false;
                    //oval2.setFree(false);
                }
            }

            //oval1.setFree(oval1free);
        }
    }

    boolean checkMutualCollision(Oval oval1, Oval oval2) {

        int dr, sdr, sdx, sdy;

        dr = oval2.getR() + oval1.getR() + oval2.getVelocity() + oval1.getVelocity();
        sdr = dr * dr;

        dx = oval2.getX() - oval1.getX();
        sdx = dx * dx;

        dy = oval2.getY() - oval1.getY();
        sdy = dy * dy;

        return (sdx + sdy < sdr);
    }
}
