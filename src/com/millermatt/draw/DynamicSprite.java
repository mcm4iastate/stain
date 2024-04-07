package com.millermatt.draw;

import java.awt.Graphics;

import com.millermatt.game.Component;

public class DynamicSprite extends DrawManager {

    Animation a;

    String path;

    public DynamicSprite(Component c, String path) {
        super(c);
        this.path = path;

        a = new Animation(path);
    }
    public DynamicSprite(Component c, String path, int rows, int cols) {
        super(c);
        this.path = path;

        a = new Animation(path, rows, cols);
    }
    public DynamicSprite(Component c, Animation a) {
        super(c);
        this.a = a;
    }

    @Override
    public void init() {
        a.init();
    }

    @Override
    public void update(float dt) {
        a.update(dt);
    }

    public void setAnimation(Animation a) {
        a.init();
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(a.getCurrentImage(), getX(), getY(), getWidth(), getHeight(), null);
    }
}
