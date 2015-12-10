package actors;

import javafx.scene.image.Image;
import util.Dice;

/**
 * Created by Joseph on 9/13/2015.
 */
public class Monster extends Actor {
    private int expValue;
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
        this.name = type.name();
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
    public Image getSprite(){return null;}

    @Override
    public void die(){
    	
    	((Hero) attacker).addExperience(expValue);
        this.sprite = new Image("assets/skull.png");
        this.combatSprite = new Image("assets/skull.png");
        alive = false;
    }

	@Override
	public void setAttacker(Actor actor){attacker = (Hero) actor;}
}