package com.millermatt.draw;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;
import java.io.Serializable;

public class Animation implements Serializable {
    
    String image_path;

    int rows;
    int cols;

    float sec;

    int current;

    Frame[] fr;

    public Animation(String path) {
        this.image_path = path;
        rows = 1;
        cols = 1;
        sec = 0f;

        current = 0;

        fr = new Frame[rows*cols];
    }
    public Animation(String path, int rows, int cols) {
        this.image_path = path;
        this.rows = rows;
        this.cols = cols;
        sec = 0f;

        fr = new Frame[rows*cols];
    }

    public void setFrame(int i) {
        current = i;
        sec = 0;
    }

    public void init() {

        if(fr[fr.length-1]!=null) {
            return;
        }

        BufferedImage spritesheet;
        try {
            spritesheet = ImageIO.read(new File(image_path));
        } catch (IOException e) {
            spritesheet = null;
            e.printStackTrace();
        }

        int width = spritesheet.getWidth();
        int height = spritesheet.getHeight();

        int i = 0;
        for(int w = 0; w<rows; w++) {
            for(int h = 0; h<cols; h++) {
                if(fr[i] == null) {
                    fr[i] = new Frame();
                }
                fr[i++].setImage(spritesheet.getSubimage((width*w)/rows, (height*h)/cols, width/rows, height/cols));
            }
        }
    }

    public void update(float dt) {
        sec+=dt;

        if(fr[fr.length-1] == null) {
            return;
        }

        if(sec>fr[current].getSwitchTime()) {
            sec = 0;
            if(current>=fr.length-1) {
                current = 0;
            } else {
                current++;
            }
        }
    }

    public void setSwitchTimes(int[] i) {
        for(int temp = 0; temp<fr.length; temp++) {
            fr[temp].setSwitchTime(i[temp]);
        }
    }

    public void setSwitchTimes(int i) {
        for(int temp = 0; temp<fr.length; temp++) {
            fr[i].setSwitchTime(i);
        }
    }

    public BufferedImage getCurrentImage() {
        return fr[current].getImage();
    }

    private class Frame {

        int switchTime = 1000;
        private transient BufferedImage img;

        public BufferedImage getImage() {
            return img;
        }

        public void setImage(BufferedImage i) {
            img = i;
        }

        public void setSwitchTime(int i) {
            switchTime = i;
        }
        public float getSwitchTime() {
            return switchTime;
        }
    }
}
