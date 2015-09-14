package actors;

import game.Direction;

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
        actor.takeDamage(this.attack);
        return true;
    }

    @Override
    public void move(Direction dir) {

    }

    @Override
    public void takeDamage(int damage) {

    }
}
