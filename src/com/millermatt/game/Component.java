package com.millermatt.game;

import java.awt.Graphics;

import java.io.Serializable;

import java.util.ArrayList;

import com.millermatt.draw.Animation;
import com.millermatt.draw.DrawManager;
import com.millermatt.draw.DynamicSprite;
import com.millermatt.draw.StaticBox;

public abstract class Component implements Serializable {

    Scene s;

    DrawManager dm;

    float x, y, width, height;

    int layer;

    ArrayList<Integer> rigid; // layers where this component should collide

    Component parent;
    float relX, relY;
    ArrayList<Component> children = new ArrayList<Component>(0);

    public Component(Scene ref) {
        this.s = ref;

        setX(0f);
        setY(0f);
        width = 0.0f;
        height = 0.0f;

        rigid = new ArrayList<Integer>(0);

        dm = new StaticBox(this);
    }

    public Component(Scene s, float x, float y, float width, float height) {
        this.s = s;

        setX(x);
        setY(y);
        this.width = width;
        this.height = height;

        rigid = new ArrayList<Integer>(0);

        dm = new StaticBox(this);
    }

    public Component(Scene s, float x, float y, float width, float height, DrawManager dm) {
        this.s = s;
        
        setX(x);
        setY(y);
        this.width = width;
        this.height = height;

        rigid = new ArrayList<Integer>(0);

        this.dm = dm;
    }

    public float getX() {
        return x;
    }
    public float getY() {
        return y;
    }
    public float getWidth() {
        return width;
    }
    public float getHeight() {
        return height;
    }

    public int[] getRigid() {
        int[] out = new int[rigid.size()];
        for(int i = 0; i<rigid.size(); i++) {
            out[i] = rigid.get(i);
        }
        return out;
    }
    public boolean isRigid(int i) {
        return rigid.contains(i);
    }

    public void move(float dx, float dy) {
        if((dx!=0) || (dy!=0)) {
            if(!s.checkCollision(this, 0, dy)) {
                y+=dy;
                updateChildrenPosition(x, y);
            } if(!s.checkCollision(this, dx, 0)) {
                x+=dx;
                updateChildrenPosition(x, y);
            }
            if(!s.checkCollision(this, dx, dy)) {
            } else if((dx/=2f)>0.49f || dx<-0.49f) {
                move(dx, 0);
            } else if((dy/=2f)>0.49f || dy<-0.49f) {
                move(0, dy);
            }
        }
    }

    private void updateChildrenPosition(float x, float y) {
        Component[] temp = getChildren();
        for(Component c : temp) {
            c.setTrueX(c.relX + x);
            c.setTrueY(c.relY + y);
            c.updateChildrenPosition(c.getX(), c.getY());
        }
    }

    public int getLayer() {
        return layer;
    }

    public boolean contains(float x, float y) {
        if(x > this.x && x < (this.x+this.width)) {
            if(y > this.y && y < (this.y+this.height)) {
                return true;
            }
        }

        return false;
    }

    public boolean contains(Component c) {
        if((c.getX()+c.getWidth()) > this.x && c.getX() < (this.x+this.width)) {
            if((c.getY()+c.getHeight()) > this.y && c.getY() < (this.y+this.height)) {
                return true;
            }
        }

        return false;
    }
    public boolean contains(float x, float y, float width, float height) {
        if((x+width) > this.x && x < (this.x+this.width)) {
            if((y+height) > this.y && y < (this.y+this.height)) {
                return true;
            }
        }

        return false;
    }

    public void setTrueX(float f) {
        x = f;
    }
    public void setTrueY(float f) {
        y = f;
    }
    public void setX(float f) {
        if (parent != null) {
            relX = f;
            x = parent.getX() + relX;
        } else {
            x = f;
        }
        updateChildrenPosition(x, y);
    }
    public void setY(float f) {
        if (parent != null) {
            relY = f;
            y = parent.getY() + relY;
        } else {
            y = f;
        }
        updateChildrenPosition(x, y);
    }
    public void setWidth(float f) {
        width = f;
    }
    public void setHeight(float f) {
        height = f;
    }
    
    public void setLayer(int i) {
        layer = i;
    }

    public void rigid(int[] layers) {
        for(int i : layers) {
            rigid.add(i);
        }
    }
    public void stopRigid(int[] layers) {
        for(int i : layers) {
            rigid.remove(i);
        }
    }
    public void rigid(int layer) {
        rigid.add(layer);
    }
    public void stopRigid(int layer) {
        rigid.remove(layer);
    }

    public void setParent(Component c) {
        parent = c;
        setX(x);
        setY(y);
    }
    public Component getParent() {
        return parent;
    }
    public void addChild(Component c) {
        c.setParent(this);
        children.add(c);
        s.add(c, getLayer());
    }
    public void addChild(Component c, int layer) {
        c.setParent(this);
        children.add(c);
        s.add(c, layer);
    }
    public void removeChild(Component c) {
        children.remove(c);
        c.setParent(null);
        s.remove(c, c.getLayer());
    }
    public Component getChild(int i) {
        return children.get(i);
    }
    public Component[] getChildren() {
        Component[] comp = new Component[children.size()];
        children.toArray(comp);
        return comp;
    }

    public Game getRef() {
        return s.getRef();
    }
    public Scene getScene() {
        return s;
    }

    public void setScene(Scene s) {
        this.s = s;
    }

    public void setAnimation(Animation a) {
        if(dm instanceof DynamicSprite) {
            ((DynamicSprite) dm).setAnimation(a);
        }
    }

    public abstract void update(float dt);

    protected void refresh(float dt) {
        update(dt);
        dm.update(dt);
    }

    public void init() {
        // Override this method
    }

    protected void initComponent() {
        dm.init();
    }

    public void setDrawManager(DrawManager dm) {
        this.dm = dm;
    }

    protected void paint(Graphics g) {
        draw(g);
    }
    protected void draw(Graphics g) {
        dm.draw(g);
    }
}
