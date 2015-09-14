package terrain;

import game.GameObject;
import game.Position;

/**
 * Created by josephbenton on 9/13/15.
 */
public class Room extends GameObject {
    int height;
    int width;

    public Room(Position p, int height, int width) {
        this.position = p;
        this.height = height;
        this.width = width;
    }

    @Override
    public void draw() {

    }
}
