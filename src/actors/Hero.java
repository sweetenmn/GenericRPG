package actors;

import game.Position;
import javafx.scene.image.Image;
import util.Dice;

/**
 * Created by josephbenton on 9/13/15.
 */
public class Hero extends Actor {
    private int maxHealth;
    private int currentHealth;
    private int attack;
    private int luck;
    private String name;

    public Hero(Profession prof, int x, int y) {
        this.setPosition(x, y);
        this.maxHealth = prof.getHealth();
        this.currentHealth = maxHealth;
        this.attack = prof.getAttack();
        this.luck = prof.getLuck();
        this.name = prof.name().toLowerCase();
        this.sprite = prof.getAvatar();
    }

    @Override
    public boolean attack(Actor actor) {
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
    public void takeDamage(int damage) {
        System.out.println(this.name + " took " + damage + " damage!");
        currentHealth -= damage;
        if (currentHealth <= 0) {
            this.die();
        }

    }

    @Override
    public Image getSprite() {
        return sprite;
    }

    @Override
    public void die() {
        this.sprite = new Image("assets/skull.png");
    }


}
