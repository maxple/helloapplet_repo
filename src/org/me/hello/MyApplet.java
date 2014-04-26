package org.me.hello;

import java.applet.*;
import java.awt.*;
import java.util.Random;

public class MyApplet extends Applet implements Runnable {

    final int OVALS_QTY = 20;

    final int D_LIM = 15;

    final int R_MAX = 50;
    final int R_MIN = 5;

    final int RECT_SIZE = 20;

    final int RECT_D = 5;

    Oval[] oval;
    Rect rect;

    Thread animator; // The thread that performs the animation

    Random rnd;

    volatile boolean pleaseStop; // A flag to ask the thread to stop

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

    //Обработка событий нажатия клавиши
    @Override
    public boolean keyDown(Event evtObj, int key) {
        switch (key) {
            case ' ':
                pleaseStop = !pleaseStop;
                break;
            case Event.ENTER:
                setBackground(new Color(rnd.nextInt()));
                break;
            case Event.DOWN:
                rect.moveDown(RECT_D, getBounds().height);
                break;
            case Event.UP:
                rect.moveUp(RECT_D, 0);
                break;
            case Event.RIGHT:
                rect.moveRight(RECT_D, getBounds().width);
                break;
            case Event.LEFT:
                rect.moveLeft(RECT_D, 0);
                break;
        }
        
        repaint();

        return true;
    }

    // This method simply draws the circle at its current position 
    @Override
    public void paint(Graphics g) {
        for (int i = 0; i < OVALS_QTY; i++) {
            oval[i].draw(g);
        }
        rect.draw(g);
    }

    // This method moves (and bounces) the circle and then requests a redraw.
    // The animator thread calls this method periodically.
    public void animate() {

        for (int i = 0; i < OVALS_QTY; i++) {

            if ((oval[i].x - oval[i].r + oval[i].dx < 0) || (oval[i].x + oval[i].r + oval[i].dx > getBounds().width)) {
                oval[i].dx = -oval[i].dx;
            }
            if ((oval[i].y - oval[i].r + oval[i].dy < 0) || (oval[i].y + oval[i].r + oval[i].dy > getBounds().height)) {
                oval[i].dy = -oval[i].dy;
            }

            // Move the circle.
            oval[i].x += oval[i].dx;
            oval[i].y += oval[i].dy;

            /*if (oval[i].dest > 0) {
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

             if (rnd.nextInt(1000) > 990) {
             oval[i].dx = -oval[i].dx;
             }
             if (rnd.nextInt(1000) > 990) {
             oval[i].dy = -oval[i].dy;
             }
             if (rnd.nextInt(1000) > 990) {
             oval[i].dest = 1 - oval[i].dest;
             }*/
        }

        // Ask the browser to call our paint() method to draw the circle
        // at its new position.
        repaint();
    }

    // This method is from the Runnable interface. It is the body of the thread
    // that performs the animation. The thread itself is created and started in
    // the start() method.
    @Override
    public void run() {
        while (true) { // Loop until we're asked to stop
            if (!pleaseStop) {
                animate(); // Update and request redraw
            }
            try {
                Thread.sleep(10);
            } // Wait 100 milliseconds
            catch (InterruptedException e) {
            } // Ignore interruptions
        }
    }

    // Start animating when the browser starts the applet 
    @Override
    public void start() {

        setBackground(new Color(255, 255, 255));

        rnd = new Random();

        oval = new Oval[OVALS_QTY];

        for (int i = 0; i < OVALS_QTY; i++) {

            oval[i] = new Oval(getBounds().width / 2, getBounds().height / 2,
                    getNextRnd(-D_LIM, D_LIM), getNextRnd(-D_LIM, D_LIM), getNextRnd(R_MIN, R_MAX), new Color(rnd.nextInt()));
        }

        rect = new Rect(getBounds().width / 2 - RECT_SIZE / 2, getBounds().height / 2 + RECT_SIZE / 2, RECT_SIZE, RECT_SIZE, new Color(55, 55, 55));

        animator = new Thread(this); // Create a thread
        pleaseStop = false; // Don't ask it to stop now
        animator.start(); // Start the thread.
        // The thread that called start now returns to its caller.
        // Meanwhile, the new animator thread has called the run() method
    }

    /*@Override
     public void stop() {}*/
}
