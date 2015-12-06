package game;

import actors.Actor;
import actors.Monster;
import game.graphics.Camera;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Created by josephbenton on 11/23/15.
 */
public class Combat extends Drawable{
    Actor hero;
    Actor monster;
    public Combat(Actor hero, Actor monster) {
        this.hero = hero;
        this.monster = monster;
    }

    @Override
    public void draw(Canvas canvas, Camera camera) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.DARKGRAY);
        gc.fillRect(0, 0, 1000, 1000);
        hero.drawForCombat(canvas, true);
        monster.drawForCombat(canvas, false);
    }
    
    public Monster getMonster(){
    	return (Monster) monster;
    }
}
