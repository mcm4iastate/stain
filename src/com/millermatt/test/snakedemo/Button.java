package com.millermatt.test.snakedemo;

import com.millermatt.draw.StaticSprite;
import com.millermatt.game.Component;
import com.millermatt.game.Scene;

public class Button extends Component {

    public Button(Scene s, int x, int y, int width, int height) {
        super(s, x, y, width, height);

        StaticSprite ss = new StaticSprite(this, "src/com/millermatt/test/snakedemo/assets/start.png");
    }

    @Override
    public void update(float dt) {
        
    }
}
