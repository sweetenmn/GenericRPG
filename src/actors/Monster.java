package actors;

import java.util.ArrayList;

import javafx.scene.image.Image;
import util.Dice;

/**
 * Created by Joseph on 9/13/2015.
 */
public class Monster extends Actor {
    private int attack, luck, expValue;
    private String name;
    private Hero attacker;
    private MonsterType type;
    private ArrayList<String> loot;

    public Monster(int level) {
    	this.type = randomType();
        this.maxHealth = type.getMaxHealth(level);
        this.currentHealth = maxHealth;
        this.attack = type.getAttack(level);
        this.luck = type.getLuck(level);
        this.loot = type.getLoot();
        this.expValue = type.getExpValue(level);
        this.sprite = type.getAvatar();
        this.alive = true;
        //System.out.println("LEVEL: " + level + "HEALTH: " + this.maxHealth);
    
    }
    
    private MonsterType randomType(){
    	Dice dice = new Dice(3);
    	int result = dice.roll();
    	switch(result){
    	case 0:
    		return MonsterType.DEMON;
    	case 1:
    		return MonsterType.TENTACLE;
    	case 2:
    		return MonsterType.OGRE;
    	default:
    		return MonsterType.DEMON;
    		
    	
    	}
    }
    
    public MonsterType getType(){
    	return type;
    }

    @Override
    boolean attack(Actor actor) {
    	actor.setAttacker(this);
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
    	this.attacker.addExperience(expValue);
        this.sprite = new Image("assets/skull.png");
        alive = false;
    }

	@Override
	public void setAttacker(Actor actor) {
		this.attacker = (Hero) actor;
	}

}
