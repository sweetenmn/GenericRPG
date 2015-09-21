package actors;

import game.graphics.Camera;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import util.Dice;

/**
 * Created by Joseph on 9/13/2015.
 */
public class Monster extends Mob {
    private int maxHealth;
    private int currentHealth;
    private int attack;
    private int luck;
    private String name;

    public Monster(int maxHealth, int attack, int luck, String name) {
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        this.attack = attack;
        this.luck = luck;
        this.name = name;
    }

    @Override
    boolean attack(Actor actor) {
        Dice dice = new Dice(20);
        int roll = dice.roll() + luck;
        if (roll < 10) {
            actor.takeDamage(0);
            return false;
        } else if (roll < 20) {
            actor.takeDamage(attack);
            return true;
        } else {
            actor.takeDamage(attack * 2);
            return true;
        }
    }

    @Override
    void takeDamage(int damage) {

    }

    @Override
    public Image getSprite() {
        return null;
    }

    @Override
    public void die() {

    }

    @Override
    public void draw(Canvas canvas, Camera camera) {

    }
}
