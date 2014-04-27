package org.me.hello;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Tape extends Applet implements Runnable {

    Thread th;

    Integer x;

    Image memImage;
    Graphics memImageGraphics;
    Dimension memImageDim;

    Graphics g;
    
    int updates, paints, repaints;

    @Override
    public void init() {
        
        updates = 0;
        paints = 0;
        repaints = 0;
        
        setBackground(Color.white);
        setForeground(Color.black);
        g = getGraphics();
        th = new Thread(this);
        x = 0;
    }

    @Override
    public void start() {
        th.start();
    }

    @Override
    public void run() {

        while (true) {
            g.setColor(Color.black);
            g.drawString(x.toString(), 50, 50);

            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(Tape.class.getName()).log(Level.SEVERE, null, ex);
            }

            g.setColor(Color.white);
            g.drawString(x.toString(), 50, 50);

            repaint();

            repaints++;
            
            x++;
        }
    }

    @Override
    public void update(Graphics g) {
        updates++;
        paint(g);
    }

    @Override
    public void paint(Graphics g) {
        paints++;
    }
}
