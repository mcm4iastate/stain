package com.millermatt.draw;

import java.awt.Graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;

import com.millermatt.game.Component;

public class StaticSprite extends DrawManager {

    String image_path;

    private transient BufferedImage img;

    public StaticSprite(Component c, String path) {
        super(c);
        image_path = path;
    }

    public void setImage(String path) {
        try {
            img = ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init() {
        setImage(image_path);
    }

    @Override
    public void draw(Graphics g) {
        try {
            g.drawImage(img, getX(), getY(), getWidth(), getHeight(), null);
        } catch(NullPointerException e) {
            System.out.println("Null image");
        }
    }
}
