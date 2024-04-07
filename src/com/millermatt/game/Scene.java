package com.millermatt.game;

import java.util.ArrayList;

import java.lang.reflect.Array;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.IOException;

import java.io.Serializable;

import java.awt.Graphics;

public abstract class Scene implements Serializable {

    private Game ref;
    private Camera cam;

    public ArrayList<ArrayList<Component>> world = new ArrayList<ArrayList<Component>>();

    private boolean notInitialized = true;

    public Scene(Game g, Camera c) {
        ref = g;
        cam = c;
    }

    public Scene(Game g) {
        ref = g;
        cam = new Camera(g, 0, 0);
    }

    public Scene(Camera c) {
        ref = null;
        cam = c;
        cam.setRef(ref);
    }

    public Scene() {
        ref = null;
        cam = new Camera(ref, 0, 0);
    }

    public void setRef(Game g) {
        ref = g;
    }
    public Game getRef() {
        return ref;
    }
    public void setCamera(Camera c) {
        cam = c;
    }
    public Camera getCamera() {
        return new Camera(cam);
    }
    
    public <T> T[] filterByType(Class<T> targetType) {
        ArrayList<Component> temp = new ArrayList<Component>();
    
        for(ArrayList<Component> list : world) {
            for (Component c : list) {
                if(targetType.isInstance(c)) {
                    temp.add(c);
                }
            }
        }

        @SuppressWarnings("unchecked")
        T[] arr = (T[]) Array.newInstance(targetType, temp.size());
        return temp.toArray(arr);
    }

    public void add(Component c, int layer) {
        c.setLayer(layer);
        c.setScene(this);
        c.init();
        while(world.size() <= layer) {
            world.add(new ArrayList<Component>());
        }
        world.get(layer).add(c);
    }

    public void remove(Component c) {
        for(ArrayList<Component> layer : world) {
            layer.remove(c);
        }
        c.setLayer(-1);
    }
    public void remove(Component c, int layer) {
        world.get(layer).remove(c);
        c.setLayer(-1);
    }

    public boolean checkCollision(Component c, Component c2, float tolerance) {
        if(c2 != c) {
            if(c.contains(c2.getX()-tolerance, c2.getY()-tolerance, c2.getWidth()+(2*tolerance), c2.getHeight()+(2*tolerance))) {
                return true;
            }
        }
        return false;
    }

    public boolean checkCollision(Component c, float dx, float dy) {

        int[] layers = c.getRigid();

        for(int i = 0; i< world.size(); i++) {
            if(i>=world.size()) {
                continue;
            }
            for(Component comp : world.get(i)) {
                if(comp != c && !isDescendant(comp, c)) {
                    if(isDescendant(c, comp)) {
                        if(checkCollision(comp, dx, dy)) {
                            return true;
                        }
                    } else if(contains(layers, i) && (comp.contains(c.getX()+dx, c.getY()+dy, c.getWidth(), c.getHeight()))) {
                        return true;
                    } else if(contains(comp.getRigid(), c.getLayer()) && comp.contains(c.getX()+dx, c.getY()+dy, c.getWidth(), c.getHeight())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean isDescendant(Component parent, Component child) {
        while (child != null) {
            if (child == parent) {
                return true;
            }
            child = child.getParent();
        }
        return false;
    }

    public boolean contains(int[] list, int check) {
        for(int i : list) {
            if(i == check) {
                return true;
            }
        }
        return false;
    }

    public abstract void update(double dt);

    public void init() {
        // Override this
    }

    protected void initialize() {
        for(ArrayList<Component> list : world) {
            for(Component c : list) {
                c.initComponent();
            }
        }

        init();
    }

    protected void onLoop(float dt) {

        if(notInitialized) {
            initialize();
            notInitialized = false;
        }

        for(ArrayList<Component> list : world) {
            for(Component c : list) {
                c.refresh(dt);
            }
        }

        cam.update(dt);
    }

    protected void paint(Graphics g) {
        draw(g);
    }
    protected void draw(Graphics g) {
        for(ArrayList<Component> list : new ArrayList<ArrayList<Component>>(world)) {
            for(Component c : new ArrayList<Component>(list)) {
                c.draw(g);
            }
        }
        overlay(g);
    }

    public void overlay(Graphics g) {

    }

    @SuppressWarnings("unchecked")
    public <T extends Scene> void saveToFile(String path) {
        try {
            FileOutputStream fos = new FileOutputStream(path);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject((T) this);

            fos.close();
            oos.close();
        } catch (FileNotFoundException e) {
        } catch(IOException ioe) {
        } 
    }

    @SuppressWarnings("unchecked")
    public static <T extends Scene> T load(String path) throws Exception {
        T s;
        try {
            FileInputStream fis = new FileInputStream(path);
            ObjectInputStream ois = new ObjectInputStream(fis);

            s = (T) ois.readObject();

            s.initialize();

            fis.close();
            ois.close();
        } catch (FileNotFoundException e) {
            throw new Exception("File not found");
        } catch(IOException ioe) {
            ioe.printStackTrace();
            throw new Exception("IO error");
        } catch(ClassNotFoundException cnfe) {
            throw new Exception("class error");
        }
        return s;
    }
}
