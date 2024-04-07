package com.millermatt.game;

import com.millermatt.draw.StaticBox;
import com.millermatt.draw.StaticSprite;

public class BoxComponent extends Component {
    public BoxComponent(Scene s, float x, float y, float width, float height, String col) {
        super(s, x, y, width, height);
        StaticBox sb = new StaticBox(this);
        sb.setColor(col);
    }
    public BoxComponent(Scene s, String path, float x, float y, float width, float height) {
        super(s, x, y, width, height);
        @SuppressWarnings("unused")
        StaticSprite sb = new StaticSprite(this, path);
    }

    @Override
    public void update(float dt) {
    }
}
