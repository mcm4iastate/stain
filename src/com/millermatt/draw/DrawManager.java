package com.millermatt.draw;

import java.awt.Graphics;

import java.io.Serializable;

import com.millermatt.game.Camera;
import com.millermatt.game.Component;
import com.millermatt.game.Game;

public abstract class DrawManager implements Serializable {

    Component drawComponent;

    public DrawManager(Component c) {
        drawComponent = c;
        c.setDrawManager(this);
    }

    public void setComponent(Component c) {
        drawComponent = c;
    }
    
    public abstract void draw(Graphics g);

    public void init() {
        // Override this method
    }

    public void update(float dt) {
        //Override this method
    }

    public int getX() {
        Game g = drawComponent.getRef();
        Camera c = drawComponent.getScene().getCamera();
        return Math.round(g.scaleHorz(c.getScreenX(drawComponent.getX())));
    }
    public int getY() {
        Game g = drawComponent.getRef();
        Camera c = drawComponent.getScene().getCamera();
        return Math.round(g.scaleVert(c.getScreenY(drawComponent.getY())));
    }
    public int getWidth() {
        Game g = drawComponent.getRef();
        return Math.round(g.scaleHorz(drawComponent.getWidth()));
    }
    public int getHeight() {
        Game g = drawComponent.getRef();
        return Math.round(g.scaleVert(drawComponent.getHeight()));
    }
}
