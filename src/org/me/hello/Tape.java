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

public class Tape extends Applet implements Runnable {

    Thread th;

    Integer x, y;

    Image memImage;
    Graphics memImageGraphics;
    Dimension memImageDim;

    int updates, paints, repaints;

    Set<Integer> keysPressed = new HashSet<>();

    Rect rect;

    @Override
    public void init() {

        updates = 0;
        paints = 0;
        repaints = 0;

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

        rect = new Rect(x, y, 5, 5, Color.red);
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
                repaints++;
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(Tape.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void update(Graphics g) {

        updates++;

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

        memImageGraphics.drawString(x.toString(), 50, 50);

        memImageGraphics.drawString(y.toString(), 50, 100);

        memImageGraphics.drawString(keysPressed.toString(), 50, 150);

        paint(g);
    }

    @Override
    public void paint(Graphics g) {

        paints++;

        if (memImage != null) {
            g.drawImage(memImage, 0, 0, null);
        }
    }

    void keysEventHandler() {
        if (keysPressed.contains(KeyEvent.VK_UP)) {
            y++;
        }
        if (keysPressed.contains(KeyEvent.VK_DOWN)) {
            y--;
        }
        if (keysPressed.contains(KeyEvent.VK_LEFT)) {
            x--;
        }
        if (keysPressed.contains(KeyEvent.VK_RIGHT)) {
            x++;
        }
    }
}
