package game;

import actors.Actor;
import actors.Hero;
import actors.Mob;
import game.graphics.Camera;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;

import java.util.ArrayList;

/**
 * Created by Joseph on 9/20/2015.
 */
public class Game {
    Hero hero;
    ArrayList<Mob> mobs;
    Level currentLevel;

    public void moveHero(Direction dir) {
        if (hero.isAlive()) {
            hero.moveAnimated(dir, currentLevel);
            step();
        }
    }
    public void heroAtk() {
        if (hero.isAlive()) {
            for (Mob m : mobs) {
                if (m.getPosition().getDistanceTo(hero.getPosition()) < 2) {
                    hero.attack(m);
                }
            }
        }
        step();
    }
    public void changeLevel(Level level) {
        this.currentLevel = level;
        this.hero = level.getHero();
        this.mobs = level.getMonsters();
    }

    public double getHeroHealthPercent() {
        return hero.getHealthPercent();
    }

    public void checkStates(AnimationTimer timer, Canvas canvas) {
        if (!hero.isAlive()) {
            gameEnd(timer, canvas);
        }
    }

    private void gameEnd(AnimationTimer timer, Canvas canvas) {
        timer.stop();
        canvas.getGraphicsContext2D().drawImage(new Image("assets/game_over.png"), canvas.getWidth() / 2, canvas.getHeight() / 2);
    }

    public void render(Canvas canvas, Camera camera) {
        currentLevel.draw(canvas, camera);
    }

    public void step() {
        for (Mob m : mobs) {
            m.stepTowards(hero, currentLevel);
        }
    }
}
