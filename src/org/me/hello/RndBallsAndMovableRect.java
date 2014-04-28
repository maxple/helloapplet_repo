package org.me.hello;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author maxple
 */
public class RndBallsAndMovableRect extends Applet implements Runnable {

    final int OVALS_QTY = 50;

    final int D_LIM = 1;

    final int R_MIN = 5;
    final int R_MAX = 50;

    Thread th;

    Integer x, y;

    Image memImage;
    Graphics memImageGraphics;
    Dimension memImageDim;

    Set<Integer> keysPressed = new HashSet<>();

    Oval[] oval;
    Rect rect;

    Random rnd;

    @Override
    public void init() {

        setFocusable(true);

        addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {

                keysPressed.add(e.getKeyCode());
            }

            @Override
            public void keyReleased(KeyEvent e) {

                keysPressed.remove(e.getKeyCode());
            }
        });

        setBackground(Color.white);
        setForeground(Color.black);

        x = 0;
        y = 0;

        rect = new Rect(x, y, 15, 15, Color.red, 5);

        rnd = new Random();

        th = new Thread(this);
    }

    @Override
    public void start() {

        oval = new Oval[OVALS_QTY];

        for (int i = 0; i < OVALS_QTY; i++) {

            oval[i] = new Oval(getBounds().width / 2, getBounds().height / 2,
                    getNextRnd(-D_LIM, D_LIM), getNextRnd(-D_LIM, D_LIM), getNextRnd(R_MIN, R_MAX), new Color(rnd.nextInt()));
        }

        th.start();
    }

    @Override
    public void run() {

        while (true) {
            try {
                keysEventHandler();
                repaint();
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(RndBallsAndMovableRect.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void update(Graphics g) {

        int w, h;

        w = getBounds().width;
        h = getBounds().height;

        if ((memImageDim == null) || (memImageDim.width != w) || (memImageDim.height != h)) {
            memImageDim = new Dimension(w, h);
            memImage = createImage(w, h);
            memImageGraphics = memImage.getGraphics();
        }

        Color cbg = getBackground();
        Color cfg = getForeground();

        memImageGraphics.setColor(cbg);

        memImageGraphics.fillRect(0, 0, w, h);

        memImageGraphics.setColor(cfg);

        for (int i = 0; i < OVALS_QTY; i++) {

            if ((oval[i].x - oval[i].r + oval[i].dx < 0) || (oval[i].x + oval[i].r + oval[i].dx > memImageDim.width)) {
                oval[i].dx = -oval[i].dx;
            }
            if ((oval[i].y - oval[i].r + oval[i].dy < 0) || (oval[i].y + oval[i].r + oval[i].dy > memImageDim.height)) {
                oval[i].dy = -oval[i].dy;
            }

            oval[i].x += oval[i].dx;
            oval[i].y += oval[i].dy;

            if (oval[i].dest > 0) {
                if (oval[i].r < R_MAX) {
                    oval[i].r++;
                } else {
                    oval[i].dest = -1;
                }
            } else if (oval[i].r > R_MIN) {
                oval[i].r--;
            } else {
                oval[i].dest = 1;
            }
            if (rnd.nextInt(1000) > 995) {
                oval[i].dest = 1 - oval[i].dest;
            }       

            if (rnd.nextInt(1000) > 995) {
                oval[i].dx = -oval[i].dx;
            }
            if (rnd.nextInt(1000) > 995) {
                oval[i].dy = -oval[i].dy;
            }
        }

        for (int i = 0; i < OVALS_QTY; i++) {
            oval[i].draw(memImageGraphics);
        }

        rect.draw(memImageGraphics);

        paint(g);
    }

    @Override
    public void paint(Graphics g) {

        if (memImage != null) {
            g.drawImage(memImage, 0, 0, null);
        }
    }

    void keysEventHandler() {
        if (keysPressed.contains(KeyEvent.VK_UP)) {
            rect.moveUp();
        }
        if (keysPressed.contains(KeyEvent.VK_DOWN)) {
            rect.moveDown(memImageDim);
        }
        if (keysPressed.contains(KeyEvent.VK_LEFT)) {
            rect.moveLeft();
        }
        if (keysPressed.contains(KeyEvent.VK_RIGHT)) {
            rect.moveRight(memImageDim);
        }
    }

    int getNextRnd(int min, int max) {

        int rn;

        if (max >= min) {
            do {
                rn = rnd.nextInt(max - min) + min;
            } while (rn == 0);
            return rn;
        } else {
            return 0;
        }
    }
}
