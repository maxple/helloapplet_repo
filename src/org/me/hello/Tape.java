package org.me.hello;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Tape extends Applet implements Runnable {

    Thread th;
    
    Integer x;

    @Override
    public void init() {
        setBackground(Color.white);
        setForeground(Color.black);
        th = new Thread(this);
        x = 0;
    }

    @Override
    public void start() {
        th.start();
        /*while(true) {
            x++;
        }*/
    }

    @Override
    public void run() {

        Graphics g = getGraphics();

        while (true) {
            g.setColor(Color.black);
            g.drawString(x.toString(), 50, 50);
            
            /*try {
                Thread.sleep(1);
            } catch (InterruptedException ex) {
                Logger.getLogger(Tape.class.getName()).log(Level.SEVERE, null, ex);
            }*/
            
            g.setColor(Color.white);
            g.drawString(x.toString(), 50, 50);
            
            x++;
        }
    }
}
