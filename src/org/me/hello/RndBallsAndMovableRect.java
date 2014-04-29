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
public class RndBallsAndMovableRect extends Applet implements Runnable {

    Thread th;

    Integer x, y;

    Image memImage;
    Graphics memImageGraphics;
    Dimension memImageDim;

    Set<Integer> keysPressed = new HashSet<>();

    OvalList ovalList;
    Rect rect, forceRect;

    int velocity;

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

                    ovalList.startAll(velocity);
                    velocity = 0;
                    forceRect.setWidth(1);
                }
            }
        });

        setBackground(new Color(101, 207, 106));
        setForeground(Color.black);

        ovalList = new OvalList();

        x = 0;
        y = 20;

        velocity = 0;

        rect = new Rect(x, y, 15, 15, Color.blue, 5);

        forceRect = new Rect(0, 0, 1, 15, Color.blue, 0);

        th = new Thread(this);
    }

    @Override
    public void start() {
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

            ovalList.addRndItemGroup(memImageDim);
        }

        Color cbg = getBackground();
        Color cfg = getForeground();

        memImageGraphics.setColor(cbg);

        memImageGraphics.fillRect(0, 0, w, h);

        memImageGraphics.setColor(cfg);

        ovalList.moveAll(memImageDim);

        ovalList.drawAll(memImageGraphics);

        forceRect.draw(memImageGraphics);

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
        if (keysPressed.contains(KeyEvent.VK_SPACE)) {
            velocity+=100;
            forceRect.enlarge(1);
        }
    }
}
