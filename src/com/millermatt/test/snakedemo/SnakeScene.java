package com.millermatt.test.snakedemo;

import com.millermatt.draw.StaticBox;
import com.millermatt.game.Component;
import com.millermatt.game.Game;
import com.millermatt.game.Scene;
import com.millermatt.game.TextComponent;

import java.util.Random;

public class SnakeScene extends Scene {

    public static final float WIDTH = 24;
    public static final float HEIGHT = 24;

    boolean left, right = true, up, down;

    int score = 0;

    int headx;
    int heady;

    int applex, appley;

    TextComponent scoreLabel;

    Random rand;

    boolean paused = true;

    boolean changeDir = false;

    TextComponent tc;

    public SnakeScene(Game g) {
        super(g);

        Background b = new Background(this, 0, 0, getRef().getScaleWidth(), getRef().getScaleHeight());

        add(b, 0);

        for(int i = 0; i<5; i++) {
            add(new SnakeCell(this, i, 3), 1);
            headx = i;
        }
        heady = 3;

        rand = new Random(System.nanoTime());

        applex = rand.nextInt((int) WIDTH);
        appley = rand.nextInt((int) HEIGHT);

        Apple a = new Apple(this, applex, appley);

        tc = new TextComponent(this, 10, 1160, 140, 30, "Score: 0", "bold", "white") {
            @Override
            public void update(float dt) { }
        };
        add(tc, 2);

        add(a, 1);
    }

    float sec = 0f;
    @Override
    public void update(double dt) {
        if(!paused) {
            sec+=dt;
        }
        if(sec>=100) {
            changeDir = false;

            SnakeCell[] cell = filterByType(SnakeCell.class);

            remove(cell[0]);
            sec = 0f;

            addSnake();

            if(headx == applex && heady == appley) {
                applex = rand.nextInt((int) WIDTH);
                appley = rand.nextInt((int) HEIGHT);

                score+=(cell.length/5);

                tc.setText("Score: " + score);

                remove(filterByType(Apple.class)[0]);

                add(new Apple(this, applex, appley), 1);

                addSnake();
            }
            if(headx < 0 || headx>=WIDTH || heady<0 || heady>=HEIGHT) {
                lose();
            }
            for(SnakeCell c : cell) {
                if(c.is(headx, heady)) {
                    lose();
                }
            }
            if(cell.length == WIDTH*HEIGHT) {
                win();
            }
        }
    }

    public void addSnake() {
        if(left) {
            add(new SnakeCell(this, headx-=1, heady), 1);
        }
        if(right) {
            add(new SnakeCell(this, headx+=1, heady), 1);
        }
        if(down) {
            add(new SnakeCell(this, headx, heady+=1), 1);
        }
        if(up) {
            add(new SnakeCell(this, headx, heady-=1), 1);
        }
    }

    public void lose() {
        ((SnakeDemo) getRef()).backToMenu();
    }

    public void win() {
        ((SnakeDemo) getRef()).backToMenu();  
    }

    public void keyPress(String s) {
        if(s.equals("space")) {
            paused = true;
        } else {
            paused = false;
        }
        if(changeDir) {
            return;
        }
        if(s.equals("w")) {
            if(!down) {
                up = true;
                left = false;
                right = false;
                changeDir = true;
            }
        } else if(s.equals("s")) {
            if(!up) {
                right = false;
                left = false;
                down = true;
                changeDir = true;
            }
        } else if(s.equals("a")) {
            if(!right) {
                up = false;
                left = true;
                down = false;
                changeDir = true;
            }
        } else if(s.equals("d")) {
            if(!left) {
                up = false;
                right = true;
                down = false;
                changeDir = true;
            }
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

    private class Apple extends Component {
        public Apple(Scene s, int x, int y) {
            super(s, 0, 0, 0, 0);

            setX(x*(getRef().getScaleWidth()/WIDTH));
            setY(y*(getRef().getScaleHeight()/HEIGHT));

            setWidth((getRef().getScaleWidth()/WIDTH)+1);
            setHeight((getRef().getScaleHeight()/HEIGHT)+1);

            StaticBox sb = new StaticBox(this);
            sb.setColor("red");
        }

        @Override
        public void update(float dt) {
        }
    }

    private class SnakeCell extends Component {
        int x, y;

        public SnakeCell(Scene s, int x, int y) {
            super(s, 0, 0, 0, 0);
            this.x = x;
            this.y = y;

            setX(x*(getRef().getScaleWidth()/WIDTH));
            setY(y*(getRef().getScaleHeight()/HEIGHT));

            setWidth((getRef().getScaleWidth()/WIDTH)+1);
            setHeight((getRef().getScaleHeight()/HEIGHT)+1);

            StaticBox sb = new StaticBox(this, "green");

            Random rand = new Random();

            sb.setColor(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255));
        }

        public boolean is(int x, int y) {
            return (this.x==x && this.y==y);
        }

        @Override
        public void update(float dt) {
            
        }
    }

    private class Background extends Component {

        public Background(Scene s, int x, int y, int width, int height) {
            super(s, x, y, width, height);

            StaticBox sb = new StaticBox(this, "black");
        }

        @Override
        public void update(float dt) {
            //NOthing
        }
        
    }
}