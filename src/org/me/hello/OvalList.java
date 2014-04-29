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
    private final int R_MAX = 50;

    private final int VELOCITY = 15;

    private final int ANGLE_MIN = 0;
    private final int ANGLE_MAX = 360;

    private final int FRICTION = 10;

    private final int PRECISION = 10;    

    private final ArrayList<Oval> list;

    Random rnd;

    public OvalList() {
        list = new ArrayList<>();
        rnd = new Random();
    }

    void addRndItem(Dimension memImageDim) {

        Oval oval = new Oval(memImageDim.width / 2, memImageDim.height / 2,
                getNextRnd(R_MIN, R_MAX), new Color(rnd.nextInt()), FRICTION, PRECISION);

        list.add(oval);
    }

    void createRndItemGroup(Dimension memImageDim, int quantity) {

        list.clear();

        while (quantity-- > 0) {
            addRndItem(memImageDim);
        }

        startAll(VELOCITY << PRECISION);
    }

    void startAll(int v) {

        for (int i = 0; i < list.size(); i++) {
            list.get(i).setVelocity(v);
            list.get(i).setAngle(getNextRnd(ANGLE_MIN, ANGLE_MAX));
        }
    }

    void moveAll(Dimension memImageDim) {

        for (int i = 0; i < list.size(); i++) {
            list.get(i).move(memImageDim);
        }
    }

    void drawAll(Graphics memImageGraphics) {

        for (int i = 0; i < list.size(); i++) {
            list.get(i).draw(memImageGraphics);
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
}
