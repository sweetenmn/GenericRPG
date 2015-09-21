package terrain;

import game.GameObject;
import game.Position;
import javafx.scene.image.Image;

/**
 * Created by josephbenton on 9/13/15.
 */
public class Wall extends GameObject {

    public Wall(Position p) {
        this.position = p;
        this.sprite = new Image("assets/white_wall.png");
    }
}
