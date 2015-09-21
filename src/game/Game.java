package game;

import actors.Actor;
import actors.Hero;
import actors.Mob;
import game.graphics.Camera;
import javafx.scene.canvas.Canvas;

import java.util.ArrayList;

/**
 * Created by Joseph on 9/20/2015.
 */
public class Game {
    Hero hero;
    ArrayList<Mob> mobs;
    Level currentLevel;

    public void moveHero(Direction dir) {
        Position p = dir.getAdj(hero.getPosition());
        if ( currentLevel.inBounds(p) && currentLevel.isClear(p)) {
                hero.setPosition(p);
            }
        for (Mob m : mobs) {
            m.stepTowards(hero, currentLevel);
        }
        }
    public void changeLevel(Level level) {
        this.currentLevel = level;
        this.hero = level.getHero();
        this.mobs = level.getMonsters();
    }

    public void render(Canvas canvas, Camera camera) {
        currentLevel.draw(canvas, camera);
    }
}
