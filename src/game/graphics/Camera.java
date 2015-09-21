package game.graphics;

import game.GameObject;
import game.Position;
import javafx.scene.canvas.Canvas;

/**
 * Created by josephbenton on 9/15/15.
 */
public class Camera extends GameObject {

    public Camera(Position position) {
        this.position = position;
    }

    @Override
    public void draw(Canvas canvas, Camera camera) {

    }
}
