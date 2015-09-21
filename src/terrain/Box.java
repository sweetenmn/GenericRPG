package terrain;

import game.GameObject;
import game.graphics.Camera;
import javafx.scene.canvas.Canvas;

import java.util.ArrayList;

/**
 * Created by josephbenton on 9/13/15.
 */
public class Box extends GameObject {

    public Box(ArrayList<GameObject> loot) {
        contents.addAll(loot);
    }

    public ArrayList<GameObject> open() {
        return contents;
    }

    @Override
    public void draw(Canvas canvas, Camera camera) {

    }
}
