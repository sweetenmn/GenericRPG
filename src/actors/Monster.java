package actors;

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

    @Override
    boolean attack(Actor actor) {
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
    void takeDamage(int damage) {

    }

    @Override
    public void die() {

    }
}
