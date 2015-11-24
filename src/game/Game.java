package game;

import actors.Actor;
import actors.Hero;
import actors.Mob;
import game.graphics.Camera;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * Created by Joseph on 9/20/2015.
 */
public class Game {
    Hero hero;
    ArrayList<Mob> mobs;
    Level currentLevel;
    GameState prevState;
    GameState state;
    Combat combat;

    public void moveHero(Direction dir) {
        if (hero.isAlive()) {
            hero.move(dir, currentLevel);
            step();
        }
    }
    public void heroAtk() {
        if (hero.isAlive()) {
            for (Mob m : mobs) {
                if (m.getPosition().getDistanceTo(hero.getPosition()) < 2) {
                    startCombat(m);
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

    public void startCombat(Actor monster) {
        combat = new Combat(hero, monster);
        setState(GameState.COMBAT);
    }

    public double getHeroHealthPercent() {
        return hero.getHealthPercent();
    }

    public void checkStates(Canvas canvas) {
        if (!hero.isAlive()) {
            gameEnd(canvas);
        }
    }

    private void gameEnd(Canvas canvas) {
        state = GameState.END;
        canvas.getGraphicsContext2D().drawImage(new Image("assets/game_over.png"), canvas.getWidth() / 2, canvas.getHeight() / 2);
    }

    public void render(Canvas canvas, Camera camera) {
        if (state.equals(GameState.WALKING)) {
            currentLevel.draw(canvas, camera);
        } else if (state.equals(GameState.COMBAT)) {
            combat.draw(canvas, camera);
        }
    }

    public void setState(GameState state) {
        prevState = state;
        this.state = state;
    }

    public void step() {
        for (Mob m : mobs) {
            m.stepTowards(hero, currentLevel);
        }
    }

    public GameState getState() {
        return state;
    }
}
