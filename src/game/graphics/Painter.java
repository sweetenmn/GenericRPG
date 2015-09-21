package game.graphics;

import game.Drawable;
import javafx.scene.canvas.Canvas;

/**
 * Created by josephbenton on 9/19/15.
 */
public class Painter {
    Drawable object;
    Camera camera;

    public Painter(Drawable object, Camera camera) {
        this.object = object;
        this.object = camera;
    }

    public void paint(Canvas canvas) {

    }


}
