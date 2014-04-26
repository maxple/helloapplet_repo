package org.me.hello;

import java.applet.*;
import java.awt.*;

public class ScrNoFlick extends Applet implements Runnable {

    private Thread m_ScrNoFlick = null;
    private String m_Text = "Scrolling String";
    private final String PARAM_Text = "Text";
    int m_StringSize;
    int m_CurrentXPosition;

    private Image m_MemImage;
    private Graphics m_MemImage_Graphics;
    Dimension m_MemImageDim = null;

    @Override
    public String getAppletInfo() {
        return "Name: ScrNoFlick\r\n"
                + "Author: Alexandr Frolov\r\n"
                + "E-mail: frolov@glas.apc.org\r\n"
                + "Web: http://www.glasnet.ru/~frolov,"
                + " http://www.dials.ccas.ru/frolov";
    }

    @Override
    public String[][] getParameterInfo() {
        String[][] info
                = {
                    {PARAM_Text, "String", "Scrolling String"},};
        return info;
    }

    @Override
    public void init() {
        String param;

        param = getParameter(PARAM_Text);
        if (param != null) {
            m_Text = param;
        }

        FontMetrics fm = getFontMetrics(getFont());
        m_StringSize = fm.stringWidth(m_Text);
        m_CurrentXPosition = size().width;
        setBackground(Color.yellow);
    }

    @Override
    public void update(Graphics g) {
        Dimension d = size();
        int nWidth = d.width;
        int nHeight = d.height;

        if ((m_MemImageDim == null)
                || (m_MemImageDim.width != nWidth)
                || (m_MemImageDim.height != nHeight)) {
            m_MemImageDim = new Dimension(nWidth, nHeight);
            m_MemImage = createImage(nWidth, nHeight);
            m_MemImage_Graphics = m_MemImage.getGraphics();
        }

        Color fg = getForeground();
        Color bg = getBackground();
        m_MemImage_Graphics.setColor(bg);

        m_MemImage_Graphics.fillRect(0, 0,
                m_MemImageDim.width, m_MemImageDim.height);

        m_MemImage_Graphics.setColor(fg);

        m_MemImage_Graphics.drawString(m_Text, m_CurrentXPosition, 20);
        m_CurrentXPosition--;

        if (m_CurrentXPosition < -m_StringSize) {
            m_CurrentXPosition = size().width;
        }

        paint(g);
    }

    @Override
    public void paint(Graphics g) {
        if (m_MemImage != null) {
            g.drawImage(m_MemImage, 0, 0, null);
        }
    }

    @Override
    public void start() {
        if (m_ScrNoFlick == null) {
            m_ScrNoFlick = new Thread(this);
            m_ScrNoFlick.start();
        }
    }

    @Override
    public void stop() {
        if (m_ScrNoFlick != null) {
            m_ScrNoFlick.stop();
            m_ScrNoFlick = null;
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                repaint();
                Thread.sleep(50);
            } catch (InterruptedException e) {
                stop();
            }
        }
    }
}
