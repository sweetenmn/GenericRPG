package terrain;

import game.Drawable;
import game.graphics.Camera;
import javafx.scene.canvas.Canvas;

import java.util.ArrayList;

/**
 * Created by josephbenton on 9/13/15.
 */
public class Box extends Drawable {

    public Box(ArrayList<Drawable> loot) {
        contents.addAll(loot);
    }

    public ArrayList<Drawable> open() {
        return contents;
    }

    @Override
    public void draw(Canvas canvas, Camera camera) {

    }
}
