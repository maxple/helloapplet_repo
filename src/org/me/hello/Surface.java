package org.me.hello;

import java.awt.*;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import javax.swing.*;

/**
 *
 * @author pletnyov
 */
class Surface extends JPanel {

    Integer x, y;

    Image memImage;
    Graphics memImageGraphics;

    Set<Integer> keysPressed = new HashSet<>();

    OvalList ovalList;
    Rect rect, forceRect, quantityRect;

    int force, quantity;

    private final int INIT_OVALS_QTY = 3;

    public void prepare() {
        
        Dimension memImageDim = getSize();
        
        int w, h;

        w = getBounds().width;
        h = getBounds().height;
        
        quantity = INIT_OVALS_QTY;
        
        setBackground(new Color(200, 255, 200));
        setForeground(Color.black);
        ovalList = new OvalList();
        ovalList.createRndItemGroup(memImageDim, quantity);
    }

    private void doDrawing(Graphics g) {

        /*Graphics2D g2d = (Graphics2D) g;

         g2d.setColor(Color.blue);

         Dimension size = getSize();
         Insets insets = getInsets();

         int w = size.width - insets.left - insets.right;
         int h = size.height - insets.top - insets.bottom;

         Random r = new Random();

         for (int i = 0; i < 1000; i++) {

         int x = Math.abs(r.nextInt()) % w;
         int y = Math.abs(r.nextInt()) % h;
         g2d.drawLine(x, y, x, y);
         }*/
        //int w, h;

        //w = getBounds().width;
        //h = getBounds().height;
        
        Dimension memImageDim = getSize();

        ovalList.moveAll(memImageDim);

        ovalList.resolveMutualCollision(memImageDim);

        ovalList.drawAll(g, memImageDim);

        /*Graphics2D g2d = (Graphics2D) g;

         g.setColor(Color.blue);

         Random r = new Random();

         for (int i = 0; i < 1000; i++) {

         int x = Math.abs(r.nextInt()) % w;
         int y = Math.abs(r.nextInt()) % h;
         g.drawLine(x, y, x, y);
         }*/

        /*if ((w > 0) && (h > 0)) {

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

         if (memImage != null) {
         g.drawImage(memImage, 0, 0, null);
         }
         }*/
    }

    @Override
    public void paintComponent(Graphics g
    ) {

        super.paintComponent(g);
        doDrawing(g);
    }
}
