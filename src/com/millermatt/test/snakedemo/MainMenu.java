package com.millermatt.test.snakedemo;

import com.millermatt.draw.StaticBox;
import com.millermatt.game.Component;
import com.millermatt.game.Game;
import com.millermatt.game.Scene;
import com.millermatt.game.TextComponent;

public class MainMenu extends Scene {

    Button start;

    public MainMenu(Game g) {
        super(g);

        start = new Button(this,400, 600, 400, 200);
        add(start, 1);

        TextComponent tc = new TextComponent(this, 200, 300, 800, 200, "Jameel the Eel", "bold", "white") {
            @Override
            public void update(float dt) { }
        };
        add(tc, 2);

        add(new Box(this, 0, 0, getRef().getScaleWidth(), getRef().getScaleHeight(), "black"), 0);
    }

    @Override
    public void update(double dt) {
    }

    public void init() {

    }

    public void click(int x, int y) {
        if(start.contains(x, y)) {
            ((SnakeDemo) getRef()).startGame();
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
}
