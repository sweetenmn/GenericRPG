package game;

import game.graphics.Camera;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;

/**
 * Created by josephbenton on 9/13/15.
 */
public abstract class GameObject {
    protected Image sprite;
    protected Position position;
    protected ArrayList<GameObject> contents;

    public GameObject() {
        this.contents = new ArrayList<GameObject>();
    }

    public void draw(Canvas canvas, Camera camera) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        double x = position.getX() * 32 - camera.getPosition().getX();
        double y = position.getY() * 32 - camera.getPosition().getY();
        gc.drawImage(sprite, x, y, 32, 32);
    }

    public Position getPosition() {
        return position;
    }


    public void setPosition(int x, int y) {
        position = new Position(x, y);
    }
}
