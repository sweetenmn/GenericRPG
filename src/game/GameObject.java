package game;

import java.util.ArrayList;

/**
 * Created by josephbenton on 9/13/15.
 */
public abstract class GameObject {
    protected Position position;
    protected ArrayList<GameObject> contents;
    public abstract void draw();
}
