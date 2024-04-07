package com.millermatt.test;

import com.millermatt.draw.StaticBox;
import com.millermatt.game.BoxComponent;
import com.millermatt.game.Camera;
import com.millermatt.game.Component;
import com.millermatt.game.Game;
import com.millermatt.game.Scene;

public class Test extends Game {

    boolean up, down, left, right;

    MainScene s;

    Player p;

    Box b1;
    Box b;
    Box b3;

    public Test() {

        super(1600, 900);
        setFrameSize(800, 450);

        s = new MainScene();

        b1 = new Box(s, 200, 200, 800, 1000, "blue");
        Box b2 = new Box(s, 800, 600, 1000, 1500, "red");

        s.add(b1, 0);
        s.add(b2, 2);

        p = new Player(s, -100, -100, 120, 120);

        s.setCamera(p.getCamera());

        s.add(p, 1);

        s.saveToFile("test.save");
        
        s = null;

        try {
            s = Scene.load("test.save");
        } catch (Exception e) {
            e.printStackTrace();
        }

        for(Player c :  s.filterByType(Player.class)) {
            p = c;
        }

        b1 = s.filterByType(Box.class)[0];

        p.rigid(new int[]{0, 1});

        b = new Box(s, -30, 30, 100, 100, "green");
        b3 = new Box(s, 0, -40, 40, 40, "pink");

        b.rigid(new int[]{0, 1, 2, 3, 4});
        b3.rigid(new int[]{2});
        b.addChild(b3);
        p.addChild(b, 2);

        s.add(new BoxComponent(s, 0, 0, 20, 20, "ORANGE"), 4);

        p.setX(-500);
        b3.setX(-10);

        setScene(s);

        start();
    }

    @Override
    public void loop(float dt) {
        //b1.move(-dt, -dt);
    }

    @Override
    public void onKeyPress(String s) {
        if(s.equals("w")) {
            p.setUp(true);
        } else if(s.equals("s")) {
            p.setDown(true);
        } else if(s.equals("a")) {
            p.setLeft(true);
        } else if(s.equals("d")) {
            p.setRight(true);
        }
    }
    @Override
    public void onKeyRelease(String s) {
        if(s.equals("w")) {
            p.setUp(false);
        } else if(s.equals("s")) {
            p.setDown(false);
        } else if(s.equals("a")) {
            p.setLeft(false);
        } else if(s.equals("d")) {
            p.setRight(false);
        }
    }
    @Override
    public void onMouseWheelMove(int i) {
        System.out.println(i);
    }
    @Override
    public void onMousePress(float x, float y) {
        if(p.contains(x, y)) {

        }
    }
    @Override
    public void onMouseRelease(float x, float y) {

    }

    
    public static void main(String args[]) {
        new Test();
    }

    private class MainScene extends Scene {
        public MainScene() {
            super();
        }

        @Override
        public void update(double dt) {

        }
    }

    private class Box extends Component {
        public Box(Scene s, int x, int y, int width, int height, String col) {
            super(s, x, y, width, height);
            StaticBox sb = new StaticBox(this);
            sb.setColor(col);
        }

        @Override
        public void update(float dt) {
        }
    }

    private class Player extends Component {

        boolean up, down, left, right;

        float speed = 2f;

        public Player(Scene s, int x, int y, int width, int height) {
            super(s, x, y, width, height);
            StaticBox b = new StaticBox(this, "yellow");
        }

        public void setUp(boolean b) {
            up = b;
        }
        public void setDown(boolean b) {
            down = b;
        }
        public void setLeft(boolean b) {
            left = b;
        }
        public void setRight(boolean b) {
            right = b;
        }

        @Override
        public void update(float dt) {
            if(up) {
                move(0, -dt*speed);
            } else if(down) {
                move(0, dt*speed);
            }
            if(left) {
                move(-dt*speed, 0);
            } else if(right) {
                move(dt*speed, 0);
            }
        }

        public Camera getCamera() {
            return new PlayerCam(getRef(), getX()-(getWidth()/2), getY()-(getHeight()/2), this);
        }

        private class PlayerCam extends Camera {

            Player p;

            public PlayerCam(Game g, float initialX, float initialY, Player p) {
                super(g, initialX, initialY);
                this.p = p;
            }

            @Override
            public void update(double dt) {
                setScreenX(p.getX()-(getRef().getScaleWidth()/2)+p.getWidth()/2);
                setScreenY(p.getY()-(getRef().getScaleHeight()/2)+p.getHeight()/2);
            }
        }
    }
}
