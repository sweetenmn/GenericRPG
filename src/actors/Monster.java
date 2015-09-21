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
    private boolean alive;

    public Monster(int maxHealth, int attack, int luck, String name) {
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        this.attack = attack;
        this.luck = luck;
        this.name = name;
        this.sprite = new Image("assets/demon.png");
        this.alive = true;
    }

    @Override
    boolean attack(Actor actor) {
        Dice dice = new Dice(20);
        int roll = dice.roll() + luck;
        System.out.println(roll);
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
        currentHealth -= damage;
        System.out.println(name + " just took " + damage + " damage!");
        if (currentHealth <= 0) {
            this.die();
        }
    }

    @Override
    public Image getSprite() {
        return null;
    }

    @Override
    public void die() {
        this.sprite = new Image("assets/skull.png");
        alive = false;
    }

    public boolean isAlive() {
        return alive;
    }
}
