package com.millermatt.game;

import java.io.Serializable;

public class Camera implements Serializable {

    private float x, y;

    private Game ref;

    public Camera(Camera c) {
        ref = c.ref;

        x = c.x;
        y = c.y;
    }

    public Camera(Game g) {
        ref = g;
        this.x = 0;
        this.y = 0;
    }
    public Camera(Game g, float initialX, float initialY) {
        ref = g;
        this.x = initialX;
        this.y = initialY;
    }

    public void setRef(Game g) {
        ref = g;
    }

    public void update(double dt) {
        // override this method
    }

    public float getScreenX(float worldX) {
        return worldX - x;
    }
    public float getScreenY(float worldY) {
        return worldY - y;
    }
    public int revScreenX(float worldX) {
        return (int) (worldX + x);
    }
    public int revScreenY(float worldY) {
        return (int) (worldY + y);
    }

    public void setScreenX(float x) {
        this.x = x;
    }
    public void setScreenY(float y) {
        this.y = y;
    }

    public String toString() {
        return "Camera: " + x + ", " + y;
    }
}