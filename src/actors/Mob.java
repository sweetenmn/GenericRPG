package actors;

import game.Direction;
import game.Level;
import game.Position;

/**
 * Created by josephbenton on 9/13/15.
 */
public abstract class Mob extends Actor {

    public void stepTowards(Actor actor, Level level) {
        Direction closest = Direction.DOWN;
        Position goal = actor.getPosition();
        for(Direction dir : Direction.values()) {
            closest = (dir.getAdj(this.position).getDistanceTo(goal) < closest.getAdj(this.position).getDistanceTo(goal)) ? dir : closest;
        }
        if (level.inBounds(closest.getAdj(position)) && level.isClear(closest.getAdj(position))) {
            this.move(closest);
        }
        if (position.getDistanceTo(goal) < 2) {
            this.attack(actor);
;        }
    }
}
