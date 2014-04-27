package org.me.hello;

import java.applet.Applet;
import java.awt.Color;

public class Tape extends Applet implements Runnable {

    Thread th;

    @Override
    public void init() {
        setBackground(new Color(255, 255, 255));
        th = new Thread();
    }

    @Override
    public void start() {
        th.start();
    }

    @Override
    public void run() {
        getGraphics().drawString("hello", 50, 50);
    }
}
