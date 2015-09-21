package game;

import actors.Hero;
import game.graphics.Camera;
import javafx.scene.canvas.Canvas;

/**
 * Created by Joseph on 9/20/2015.
 */
public class Game {
    Hero hero;
    Level currentLevel;

    public void moveHero(Direction dir) {
        Position p = dir.getAdj(hero.getPosition());
        if ( currentLevel.inBounds(p) && currentLevel.isClear(p)) {
                hero.setPosition(p);
            }
        }

    public void changeLevel(Level level) {
        this.currentLevel = level;
        this.hero = level.getHero();
    }

    public void render(Canvas canvas, Camera camera) {
        currentLevel.draw(canvas, camera);
    }
}
