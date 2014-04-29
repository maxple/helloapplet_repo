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

    private final int VELOCITY_MAX = 5;

    final int OVALS_QTY = 5;

    private final ArrayList<Oval2> list;

    Random rnd;

    public OvalList() {
        list = new ArrayList<>();
        rnd = new Random();
    }

    void addRndItem(Dimension memImageDim) {

        Oval2 oval = new Oval2(memImageDim.width / 2, memImageDim.height / 2,
                getNextRnd(R_MIN, R_MAX), new Color(rnd.nextInt()));

        oval.setVelocity(rnd.nextDouble() * VELOCITY_MAX + 1);
        oval.setAngle(rnd.nextDouble() * 360);

        list.add(oval);
    }

    void addRndItemGroup(Dimension memImageDim) {

        if (list.isEmpty()) {
            int n = OVALS_QTY;
            while (n-- > 0) {
                addRndItem(memImageDim);
            }
        }
    }

    Oval2 getItem(int n) {
        return list.get(n);
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
