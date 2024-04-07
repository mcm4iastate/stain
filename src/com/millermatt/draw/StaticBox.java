package com.millermatt.draw;

import java.awt.Color;
import java.awt.Graphics;

import com.millermatt.game.Component;

public class StaticBox extends DrawManager {

    Color c;
    
    public StaticBox(Component c) {
        super(c);
        this.c = Color.BLACK;
    }

    public StaticBox(Component c, String s) {
        super(c);
        setColor(s);
    }

    public void setColor(String s) {
        if(s.toLowerCase().equals("blue")) {
            c = Color.BLUE;
        } else if(s.toLowerCase().equals("red")) {
            c = Color.RED;
        } else if(s.toLowerCase().equals("green")) {
            c = Color.GREEN;
        } else if(s.toLowerCase().equals("yellow")) {
            c = Color.YELLOW;
        } else if(s.toLowerCase().equals("pink")) {
            c = Color.PINK;
        } else if(s.toLowerCase().equals("cyan")) {
            c = Color.CYAN;
        } else if(s.toLowerCase().equals("magenta")) {
            c = Color.MAGENTA;
        } else if(s.toLowerCase().equals("orange")) {
            c = Color.ORANGE;
        } else if(s.toLowerCase().equals("white")) {
            c = Color.WHITE;
        } else if(s.toLowerCase().equals("black")) {
            c = Color.BLACK;
        } else if(s.toLowerCase().equals("gray")) {
            c = Color.GRAY;
        } else {
            c = Color.BLACK;
        }
    }
    public void setColor(int r, int g, int b) {
        c = new Color(r, g, b);
    }
    public void setColor(int r, int g, int b, int a) {
        c = new Color(r, g, b, a);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(c);
        g.fillRect(getX(), getY(), getWidth(), getHeight());
    }
}
