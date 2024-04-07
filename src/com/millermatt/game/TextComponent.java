package com.millermatt.game;

import java.awt.Graphics;
import java.awt.Font;
import java.awt.Color;
import java.awt.FontFormatException;

import java.io.File;
import java.io.IOException;

import java.awt.image.BufferedImage;

import com.millermatt.draw.DrawManager;

public class TextComponent extends Component {

    int FONT_SIZE = 48;

    Font f;
    String text;
    Color c;
    BufferedImage img;

    public TextComponent(Scene s, float x, float y, float width, float height, String text) {
        super(s, x, y, width, height);

        f = new Font("Arial", getWt("bold"), FONT_SIZE);

        this.text = text;
        this.c = Color.WHITE;

        img = createGraphic();

        setDrawManager(new TextDrawer(this));
    }
    public TextComponent(Scene s, float x, float y, float width, float height, String text, String fontWeight) {
        super(s, x, y, width, height);

        f = new Font("Arial", getWt(fontWeight), FONT_SIZE);

        this.text = text;
        this.c = Color.WHITE;

        img = createGraphic();

        setDrawManager(new TextDrawer(this));
    }
    public TextComponent(Scene s, float x, float y, float width, float height, String text, String font, String weight) {
        super(s, x, y, width, height);


        f = new Font(font, getWt(weight), FONT_SIZE);

        this.text = text;
        this.c = Color.WHITE;

        img = createGraphic();

        setDrawManager(new TextDrawer(this));
    }
    public TextComponent(Scene s, float x, float y, float width, float height, String text, String font, String weight, String color) {
        super(s, x, y, width, height);

        f = new Font(font, Font.PLAIN, FONT_SIZE);

        this.text = text;

        setColor(color);

        img = createGraphic();

        setDrawManager(new TextDrawer(this));
    }


    public void setFont(String pathToFont, int fontSize) {
        try {
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File(pathToFont)).deriveFont(FONT_SIZE);
            f = customFont;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FontFormatException e) {
            e.printStackTrace();
        }

        img = createGraphic();
    }

    public void setText(String s) {
        text = s;

        img = createGraphic();
    }

    private int getWt(String s) {
        if(s.equals("bold")) {
            return Font.BOLD;
        } else if(s.equals("italic")) {
            return Font.ITALIC;
        }
        return Font.PLAIN;
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

    public BufferedImage createGraphic() {
        BufferedImage temp = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics g = temp.createGraphics();

        g.setFont(f);

        int width = g.getFontMetrics().stringWidth(text);

        BufferedImage out = new BufferedImage(width, 35, BufferedImage.TYPE_INT_ARGB);
        g = out.createGraphics();

        g.setColor(c);

        g.setFont(f);

        g.drawString(text, 0, 35);

        g.dispose();

        return out;
    }

    private class TextDrawer extends DrawManager {

        public TextDrawer(Component c) {
            super(c);
        }

        @Override
        public void draw(Graphics g) {
            g.drawImage(img, getX(), getY(), getWidth(), getHeight(), null);
        }
    }

    @Override
    public void update(float dt) {
        //Override This
    }
}
