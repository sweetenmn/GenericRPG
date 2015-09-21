package actors;

import game.Direction;
import game.Drawable;
import game.Level;
import game.Position;
import javafx.scene.image.Image;

/**
 * Created by josephbenton on 9/13/15.
 */
public abstract class Actor extends Drawable {
    protected boolean alive;
    abstract boolean attack(Actor actor); // returns true if attack is successful, else returns false

    abstract void takeDamage(int damage);

    public void move(Direction dir, Level currentLevel) {
        Position p = dir.getAdj(this.getPosition());
        if (currentLevel.inBounds(p) && currentLevel.isClear(p)) {
            this.setPosition(p);
        }
    }
    public boolean isAlive() {
        return alive;
    }
    public abstract Image getSprite();

    public abstract void die();
}
