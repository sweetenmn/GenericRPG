package actors;

import game.Direction;
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

    public Hero(Profession prof) {
        this.maxHealth = prof.getHealth();
        this.currentHealth = maxHealth;
        this.attack = prof.getAttack();
        this.luck = prof.getLuck();
        this.name = prof.name().toLowerCase();
    }

    @Override
    public boolean attack(Actor actor) {
        Dice dice = new Dice(20);
        int roll = dice.roll() + luck;
        if (roll < 10){
            actor.takeDamage(0);
            return false;
        } else if (roll < 20){
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
    public void die() {

    }
}
