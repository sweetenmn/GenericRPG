package actors;

import game.Direction;
import game.Drawable;
import game.Level;
import game.Position;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Created by josephbenton on 9/13/15.
 */
public abstract class Actor extends Drawable {
    protected boolean alive;
	protected int currentHealth, maxHealth;
   // protected int currentHealth, maxHealth;
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
    public  Image getSprite() {
        return sprite;
    }
    public double getHealthPercent() {
        return (double)currentHealth / (double)maxHealth;
    }
    
    public void drawForCombat(Canvas canvas, boolean isHero) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        double height = canvas.getHeight() / 2;
        double width = canvas.getWidth() / 3;
        if (isHero) {
            gc.drawImage(sprite, width, height, 50, 50);
        } else {
            gc.drawImage(sprite, width * 2, height, 50, 50);
        }
    }
    public abstract void die();
    
    public abstract void setAttacker(Actor actor);
}
