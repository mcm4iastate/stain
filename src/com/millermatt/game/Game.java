package com.millermatt.game;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseWheelEvent;

public abstract class Game extends JPanel {

    public static int max_fps = 240;

    public int scale_width = 1600;
    public int scale_height = 900;

    public int fr_width = 800;
    public int fr_height = 450;

    private JFrame frame;

    private Scene scene;

    public long lastTime;
    public long lastFrame;
    public double fps = 0;

    private boolean showPerformace = true;

    private boolean resizing = false;

    private boolean paused = false;

    public Game() {
        initFrame(fr_width, fr_height);
    }

    public Game(Scene s) {
        this();

        scene = s;
        scene.setRef(this);
    }

    public Game(int width, int height) {

        scale_width = width;
        scale_height = height;
        fr_width = width;
        fr_height = height;

        initFrame(width, height);
    }

    private void initFrame(int width, int height) {
        frame = new JFrame();

        setDoubleBuffered(true);

        frame.addComponentListener(new FrameListener());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        frame.addKeyListener(new InputListener());
        frame.addMouseWheelListener(new InputListener());
        frame.addMouseListener(new InputListener());

        setFrameSize(fr_width, fr_height);

        frame.add(this);

        frame.setVisible(true);
        setVisible(true);
    }

    public void onResize(ComponentEvent e) {
        fr_width = getWidth();
        fr_height = getHeight();

        this.setPreferredSize(new Dimension(fr_width, fr_height));

        frame.pack();
    }

    public void setFrameSize(int width, int height) {
        fr_width = width;
        fr_height = height;

        this.setPreferredSize(new Dimension(fr_width, fr_height));

        frame.pack();

        frame.setLocationRelativeTo(null);

        resizing = true;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if(scene!=null) {
            scene.draw(g);
        }

        if(showPerformace) {
            g.setColor(Color.BLACK);
            g.drawString("FPS: " + Math.round(fps), 0, 20);
        }

        g.dispose();
    }

    public int scaleHorz(float relX) {
        return Math.round(((relX)/scale_width)*fr_width);
    }
    public int scaleVert(float relY) {
        return Math.round(((relY)/scale_height)*fr_height);
    }
    public float revHorz(float relX) {
        return (float) (((relX)/fr_width)*scale_width);
    }
    public float revVert(float relY) {
        return (float) (((relY)/fr_height)*scale_height);
    }

    public int getScaleWidth() {
        return scale_width;
    }
    public int getScaleHeight() {
        return scale_height;
    }

    public void setScene(Scene s) {
        scene = s;
        scene.setRef(this);
    }
    public Scene getScene() {
        return scene;
    }

    public abstract void loop(float dt);

    public abstract void onKeyPress(String s);
    public abstract void onKeyRelease(String s);
    public abstract void onMouseWheelMove(int i);
    public abstract void onMousePress(float x, float y);
    public abstract void onMouseRelease(float x, float y);

    public void setPaused(boolean b) {
        paused = b;
    }
    public void start() {
        while(true) {
            long time = System.nanoTime();
            float dt = (float) (time-lastTime);
            dt/=1000000;
            if(dt>10f) {
                lastTime = time;
                continue;
            }

            if(dt > (1000.0/max_fps)) {
                if(!paused) {
                    loop(dt);
                    scene.onLoop(dt);
                    scene.update(dt);
                }
                repaint();
                fps = (1000 / (dt));
                lastTime = time;
            }
        }
    }

    private class FrameListener extends ComponentAdapter {
        @Override
        public void componentResized(ComponentEvent e) {
            if(!resizing) {
                onResize(e);
                resizing = false;
            }
            resizing = false;
        }
    }

    private class InputListener extends KeyAdapter implements MouseListener, MouseWheelListener {
        @Override
        public void keyPressed(KeyEvent e) {
            onKeyPress(KeyEvent.getKeyText(e.getKeyCode()).toLowerCase());
        }

        @Override
        public void keyReleased(KeyEvent e) {
            onKeyRelease(KeyEvent.getKeyText(e.getKeyCode()).toLowerCase());
        }
        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            if(e.getWheelRotation()!=0) {
                onMouseWheelMove(e.getWheelRotation());
            }
        }
        @Override
        public void mousePressed(MouseEvent e) {
            Camera c = scene.getCamera();
            onMousePress(c.revScreenX(revHorz(e.getX()))-revHorz(4), c.revScreenY(revVert(e.getY()))-revVert(28));
        }
        @Override
        public void mouseReleased(MouseEvent e) {
            Camera c = scene.getCamera();
            onMouseRelease(c.revScreenX(revHorz(e.getX()))-revHorz(4), c.revScreenY(revVert(e.getY()))-revVert(28));
        }

        @Override
        public void mouseClicked(MouseEvent e) {}
        @Override
        public void mouseEntered(MouseEvent e) { }
        @Override
        public void mouseExited(MouseEvent e) { }
    }
}
