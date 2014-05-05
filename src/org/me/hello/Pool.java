package org.me.hello;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author maxple
 */
public class Pool extends Applet implements Runnable {

    Thread th;

    Integer x, y;

    Image memImage;
    Graphics memImageGraphics;
    Dimension memImageDim;

    Set<Integer> keysPressed = new HashSet<>();

    OvalList ovalList;
    Rect rect, forceRect, quantityRect;

    int force, quantity;

    private final int INIT_OVALS_QTY = 3;

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

                if (e.getKeyCode() == KeyEvent.VK_SPACE) {

                    ovalList.startAll(force);
                    force = 0;
                    forceRect.setWidth(1);
                }

                if (e.getKeyCode() == KeyEvent.VK_ENTER) {

                    memImageDim = null;
                    quantityRect.setWidth(1);
                }
            }
        });

        setBackground(new Color(200, 255, 200));
        setForeground(Color.black);

        ovalList = new OvalList();

        x = 0;
        y = 40;

        force = 0;

        quantity = INIT_OVALS_QTY;

        forceRect = new Rect(0, 0, 1, 15, Color.red, 0);

        quantityRect = new Rect(0, 20, 1, 15, Color.yellow, 0);

        rect = new Rect(x, y, 15, 15, Color.blue, 5);

        th = new Thread(this);
    }

    @Override
    public void start() {
        try {
            th.start();
        } catch (Exception e) {

        }
    }

    @Override
    public void run() {

        while (true) {
            try {
                keysEventHandler();
                repaint();
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(Pool.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void update(Graphics g) {

        int w, h;

        w = getBounds().width;
        h = getBounds().height;

        if ((w > 0) && (h > 0)) {

            if ((memImageDim == null) || (memImageDim.width != w) || (memImageDim.height != h)) {
                memImageDim = new Dimension(w, h);
                memImage = createImage(w, h);
                memImageGraphics = memImage.getGraphics();

                ovalList.createRndItemGroup(memImageDim, quantity);
                quantity = 0;
            }

            Color cbg = getBackground();
            Color cfg = getForeground();

            memImageGraphics.setColor(cbg);

            memImageGraphics.fillRect(0, 0, w, h);

            memImageGraphics.setColor(cfg);

            ovalList.moveAll(memImageDim);

            ovalList.resolveMutualCollision(memImageDim);

            ovalList.drawAll(memImageGraphics, memImageDim);

            forceRect.draw(memImageGraphics);

            quantityRect.draw(memImageGraphics);

            memImageGraphics.setColor(cfg);

            memImageGraphics.drawString(Integer.toString(quantity), 0, 30);

            rect.draw(memImageGraphics);

            paint(g);
        }
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
        if (keysPressed.contains(KeyEvent.VK_SPACE)) {
            if (force < 255 * 100) {
                force += 100;
            }
            forceRect.enlarge(1);
        }
        if (keysPressed.contains(KeyEvent.VK_ENTER)) {
            /*if (quantity < 7)*/ quantity++;
            quantityRect.enlarge(1);
        }
    }
}
