package actors;

import java.util.ArrayList;
import javafx.scene.image.Image;
import terrain.Item;
import util.Dice;

/**
 * Created by Joseph on 9/13/2015.
 */
public class Monster extends Actor {
    private int attack, luck, expValue;
    private Hero attacker;
    private MonsterType type;

    public Monster(int level){
    	this.type = randomType();
        this.maxHealth = type.getMaxHealth(level);
        this.currentHealth = maxHealth;
        this.attack = type.getAttack(level);
        this.luck = type.getLuck(level);
        this.expValue = type.getExpValue(level);
        this.sprite = type.getAvatar();
        this.combatSprite = type.getCombatAvatar();
        this.alive = true;
    }
    
    private MonsterType randomType(){
    	Dice dice = new Dice(3);
    	int result = dice.roll();
        return MonsterType.values()[result];
    }
    
    public MonsterType getType(){
    	return type;
    }
    

    @Override
    public boolean attack(Actor actor) {
    	actor.setAttacker(this);
        Dice dice = new Dice(20);
        int roll = dice.roll() + luck;
        if (roll < 10){
            actor.takeDamage(0);
            return false;
        } else if (roll < 20){
            actor.takeDamage(attack);
            return true;
        } else{
            actor.takeDamage(attack * 2);
            return true;
        }
    }

    @Override
    void takeDamage(int damage){
        currentHealth -= damage;
        System.out.println(type.name() + " just took " + damage + " damage!");
        if (currentHealth <= 0){
            this.die();
        }
    }

    @Override
    public Image getSprite(){return null;}

    @Override
    public void die(){
    	this.attacker.addExperience(expValue);
        this.sprite = new Image("assets/skull.png");
        this.combatSprite = new Image("assets/skull.png");
        alive = false;
    }

	@Override
	public void setAttacker(Actor actor){this.attacker = (Hero) actor;}
}