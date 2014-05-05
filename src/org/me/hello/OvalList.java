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

    private final int R_MIN = 5;
    private final int R_MAX = 30;

    private final int VELOCITY = 1;

    private final int ANGLE_MIN = 0;
    private final int ANGLE_MAX = 360;

    private final int FRICTION = 10;

    private final int PRECISION = 10;

    private final ArrayList<Oval> list;

    Random rnd;

    boolean saturation;

    int nextX, nextY, nextAngle;

    private final int INIT_X = 100;
    private final int INIT_Y = 100;

    int dx, dy;

    public OvalList() {
        list = new ArrayList<>();
        rnd = new Random();
    }

    void addRndItem(Dimension memImageDim) {

        if (saturation == false) {
            Oval oval = new Oval(nextX, nextY,
                getNextRnd(R_MIN, R_MAX), new Color(rnd.nextInt()), FRICTION, PRECISION);

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
            /*if (i1 % 2 == 0) {
             list.get(i1).setVelocity(velocity);
             } else {
             list.get(i1).setVelocity(0);
             }*/

            list.get(i1).setVelocity(velocity);
            list.get(i1).setAngle(getNextRnd(ANGLE_MIN, ANGLE_MAX));
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

        int alpha, vx1, vy1, vx2, vy2, temp, v1, v2, a1, a2, m1, m2, vx1res, vx2res;
        Oval oval1, oval2;

        for (int i1 = 0; i1 < list.size(); i1++) {

            oval1 = list.get(i1);

            for (int i2 = i1 + 1; i2 < list.size(); i2++) {

                oval2 = list.get(i2);

                if (checkMutualCollision(memImageDim, oval1, oval2)) {

                    //System.out.print("i1=" + Integer.toString(i1) + " i2=" + Integer.toString(i2));
                    alpha = (int) Math.toDegrees(Math.atan2(dy, dx));

                    a1 = oval1.getAngle() - alpha;
                    a2 = oval2.getAngle() - alpha;

                    oval1.setAngle(a1);
                    oval2.setAngle(a2);

                    vx1 = oval1.getVelocityX();
                    vy1 = oval1.getVelocityY();
                    vx2 = oval2.getVelocityX();
                    vy2 = oval2.getVelocityY();

                    m1 = oval1.getR();
                    m2 = oval2.getR();

                    vx1res = ((vx1 * (m1 - m2)) + (2 * m2 * vx2)) / (m2 + m1);
                    vx2res = vx1 + vx1res - vx2;
                    
                    vx1 = vx1res;
                    vx2 = vx2res;

                    v1 = (int) Math.sqrt(vx1 * vx1 + vy1 * vy1);
                    v2 = (int) Math.sqrt(vx2 * vx2 + vy2 * vy2);

                    //System.out.print(" v1=" + Integer.toString(v1) + " v2=" + Integer.toString(v2));
                    if ((vx1 == 0) && (vy1 == 0)) {
                        a1 = 0;
                    } else {
                        a1 = (int) Math.toDegrees(Math.atan2(vy1, vx1));
                    }

                    if ((vx2 == 0) && (vy2 == 0)) {
                        a2 = 0;
                    } else {
                        a2 = (int) Math.toDegrees(Math.atan2(vy2, vx2));
                    }

                    a1 += alpha;
                    a2 += alpha;

                    //System.out.println(" a1=" + Integer.toString(a1) + " a2=" + Integer.toString(a2));
                    oval1.setVelocity(v1);
                    oval2.setVelocity(v2);
                    oval1.setAngle(a1);
                    oval2.setAngle(a2);
                }
            }
        }
    }

    boolean checkMutualCollision(Dimension memImageDim, Oval oval1, Oval oval2) {

        int dr, sdr, sdx, sdy;
        boolean before, now, after;

        dr = oval2.getR() + oval1.getR();
        sdr = dr * dr;

        oval1.moveBack(memImageDim);
        oval2.moveBack(memImageDim);

        dx = oval2.getX() - oval1.getX();
        sdx = dx * dx;

        dy = oval2.getY() - oval1.getY();
        sdy = dy * dy;

        before = sdx + sdy < sdr;

        oval1.moveForward(memImageDim);
        oval2.moveForward(memImageDim);

        dx = oval2.getX() - oval1.getX();
        sdx = dx * dx;

        dy = oval2.getY() - oval1.getY();
        sdy = dy * dy;

        now = sdx + sdy < sdr;

        oval1.moveForward(memImageDim);
        oval2.moveForward(memImageDim);

        dx = oval2.getX() - oval1.getX();
        sdx = dx * dx;

        dy = oval2.getY() - oval1.getY();
        sdy = dy * dy;

        after = sdx + sdy < sdr;

        oval1.moveBack(memImageDim);
        oval2.moveBack(memImageDim);

        return (before == false && now == true && after == true);
    }
}
