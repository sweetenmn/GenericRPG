package actors;

import game.Direction;
import game.GameObject;

/**
 * Created by josephbenton on 9/13/15.
 */
public abstract class Actor extends GameObject {
    abstract boolean attack(Actor actor); // returns true if attack is successful, else returns false
    abstract void takeDamage(int damage);
    public void move(Direction dir) {
        position = dir.move(position);
    }
}
