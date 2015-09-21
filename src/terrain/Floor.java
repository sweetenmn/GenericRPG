package terrain;

import game.GameObject;
import game.Position;
import javafx.scene.image.Image;

/**
 * Created by josephbenton on 9/20/15.
 */
public class Floor extends GameObject{
    public Floor(Position p) {
        this.position = p;
        this.sprite = new Image("assets/floor_cobble.png");
    }
}
