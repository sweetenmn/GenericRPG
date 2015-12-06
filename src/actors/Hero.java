package actors;

import game.Direction;
import game.Level;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import util.Dice;

/**
 * Created by josephbenton on 9/13/15.
 */
public class Hero extends Actor {
    private int attack, luck, level;
    private int experience, expToNextLevel;
    private int expBoost = 0;
    private int boostCount= 0;
    private Actor attacker;
    private String name;
    private Profession prof;
    private static final int EXP_BUFF = 25;
    private static final int HEALTH_BUFF = 3;
    private static final int STAT_BUFF = 1;
    private static final int INIT_EXP_REQUIRED = 100;

    public Hero(Profession prof, String name) {
        this.prof = prof;
        this.sprite = prof.getSpriteDirection(Direction.DOWN);
        this.alive = true;
        this.experience = 0;
        this.expBoost = 0;
        this.level = 1;
        this.name = name;
        adjustStats();
        currentHealth = maxHealth;
    }
    
    public Hero(Profession prof, String name, int level){
        this.prof = prof;
        this.sprite = prof.getSpriteDirection(Direction.DOWN);
        this.alive = true;
        this.name = name;
        this.level = level;
        this.expBoost = 0;
        adjustStats();
    }
    
    
    private void adjustStats(){
    	this.expToNextLevel = offsetByLevel(INIT_EXP_REQUIRED, EXP_BUFF);
    	this.maxHealth = offsetByLevel(prof.getMaxHealth(), HEALTH_BUFF);
    	this.attack = offsetByLevel(prof.getAttack(), STAT_BUFF);
    	this.luck = offsetByLevel(prof.getLuck(), STAT_BUFF);
    	
    }
    
    private int offsetByLevel(int initial, int factorOf){
    	int levelOffset = this.level - 1;
    	return initial + (levelOffset * factorOf);
    }
    
    public void loadHealth(int health){
    	this.currentHealth = health;
    }
    
    public void loadExp(int exp){
    	this.experience = exp;
    }
    

    @Override
    public boolean attack(Actor actor) {
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
    public void takeDamage(int damage) {
        System.out.println(this.name + " took " + damage + " damage!");
        currentHealth -= damage;
        if (currentHealth <= 0) {
            this.die();
        }

    }
    
    public double getExpPercent() {
    	return (double)experience / (double)expToNextLevel;
    }
    
    public void addExperience(int monsterExp){
    	experience += (monsterExp + getBoost());
    	this.levelIf();
    }
    
    public boolean usePotion(int PotionType){
    	boolean used = false;
    	switch(PotionType){
    	case 0:
    		used = heal();
    		break;
    	case 1://exp
    		used = boost();
    		break;
    	}
    	return used;
    }
    
    private boolean boostActive(){
    	return boostCount != 0;
    }
    
    private int getBoost(){
    	if (boostActive()){
    		return 5 * ((level - 1) + 3);
    	}
    	return 0;
    }
    
    private boolean heal(){
    	if (currentHealth < maxHealth){
    		currentHealth += 10;
    		return true;
    	}
    	return false;
    	
    }
    
    private boolean boost(){
    	boolean boosted = false;
    	if (!boostActive()){
    		boostCount = 4;
			boosted  = true;
		} else {
			Alert boostActive = new Alert(AlertType.INFORMATION);
			boostActive.setContentText("Boost still active!\n"
					+ "Please wait " + boostCount + " more battle.");
		}
    	return boosted;
    }
    
    
    
    private void levelIf(){
    	if (expToNextLevel - experience <= 0){
    		level += 1;
    		experience = Math.abs(expToNextLevel - experience);
    		expToNextLevel += 25;
    		System.out.println("exp to next level: " + this.expToNextLevel);
    		System.out.println("hero level: " + this.level);
    	}
    }
    
    public int getLevel(){
    	return level;
    }

    @Override
    public Image getSprite() {
        return sprite;
    }
    
    public void setSprite(Image image){
    	sprite = image;
    }
    
    public void moveAnimated(Direction dir, Level currentLevel){
    	move(dir, currentLevel);
    	setSprite(prof.getSpriteDirection(dir));
    	
    }

    @Override
    public void die() {
        this.sprite = new Image("assets/skull.png");
        alive = false;
    }

	@Override
	public void setAttacker(Actor actor) {
		this.attacker = actor;
	}
	
	public void setName(String input){
		this.name = input;
	}
	public String getName(){
		return this.name;
	}
	
	public int getActualHealth(){
		return currentHealth;
	}
	public int getActualExp(){
		return this.experience;
	}
	public Profession getProfession(){
		return this.prof;
	}
	
	public Actor getAttacker(){
		return attacker;
	}


}
