package com.millermatt.test.snakedemo;

import com.millermatt.game.Game;

public class SnakeDemo extends Game {

    MainMenu m;

    SnakeScene ss;

    public SnakeDemo() {
        super(1200, 1200);
        setFrameSize(600, 600);

        m = new MainMenu(this);

        setScene(m);

        start();
    }

    public void backToMenu() {
        setScene(new MainMenu(this));
    }

    @Override
    public void loop(float dt) {
    }

    public void startGame() {
        setScene(new SnakeScene(this));
    }

    @Override
    public void onKeyPress(String s) {
        if(getScene() instanceof SnakeScene) {
            ((SnakeScene) getScene()).keyPress(s);
        }
    }

    @Override
    public void onKeyRelease(String s) { }
    @Override
    public void onMouseWheelMove(int i) { }
    @Override
    public void onMousePress(float x, float y) {
        if(getScene() instanceof MainMenu) {
            m.click((int) x, (int) y);
        }
    }
    @Override
    public void onMouseRelease(float x, float y) { }

    public static void main(String args[]) {
        new SnakeDemo();
    }
}