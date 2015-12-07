package actors;

import java.util.ArrayList;

import game.Direction;
import game.Level;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import terrain.Item;
import terrain.ItemType;
import util.Dice;

/**
 * Created by josephbenton on 9/13/15.
 */

public class Hero extends Actor{
    private int attack, luck, level;
    private int experience, expToNextLevel;
    private int boostCount= 0;
    private Actor attacker;
    private String name;
    private HeroType prof;
    private ArrayList<Item> inventory = new ArrayList<Item>();
    private static final int EXP_BUFF = 25;
    private static final int HEALTH_BUFF = 3;
    private static final int STAT_BUFF = 1;
    private static final int ATK_BUFF = 3;
    private static final int INIT_EXP_REQUIRED = 100;
    private static final int EXP_BOOST = 3;

    public Hero(HeroType prof, String name) {
        this.prof = prof;
        this.combatSprite = prof.getCombatAvatar();
        this.sprite = prof.getSpriteDirection(Direction.DOWN);
        this.alive = true;
        this.experience = 0;
        this.level = 1;
        this.name = name;
        adjustStats();
        currentHealth = maxHealth;
    }
    public Hero(HeroType prof, String name, int level){
        this.prof = prof;
        this.sprite = prof.getSpriteDirection(Direction.DOWN);
        this.combatSprite = prof.getCombatAvatar();
        this.alive = true;
        this.name = name;
        this.level = level;
        adjustStats();
    }
    private void adjustStats(){
    	this.expToNextLevel = offsetByLevel(INIT_EXP_REQUIRED, EXP_BUFF);
    	this.maxHealth = offsetByLevel(prof.getMaxHealth(), HEALTH_BUFF);
    	this.attack = offsetByLevel(prof.getAttack(), ATK_BUFF);
    	this.luck = offsetByLevel(prof.getLuck(), STAT_BUFF);
    }
    private int offsetByLevel(int initial, int factorOf){
    	int levelOffset = this.level - 1;
    	return initial + (levelOffset * factorOf);
    }
    
    public void loadFromSaved(int health, int exp, int healthPotions, int expPotions){
    	this.currentHealth = health;
    	this.experience = exp;
    	for (int i = 0; i < healthPotions; i++){
    		inventory.add(new Item(ItemType.HEALTH));
    	}
    	for (int j = 0; j < expPotions; j++){
    		inventory.add(new Item(ItemType.EXPERIENCE));
    	}
    
    }
    
    @Override
    public boolean attack(Actor actor){
    	actor.setAttacker(this);
        Dice dice = new Dice(20);
        int roll = dice.roll() + luck;
        if (roll < 5){
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
    public void takeDamage(int damage){
        System.out.println(this.name + " took " + damage + " damage!");
        currentHealth -= damage;
        if (currentHealth <= 0) {
            this.die();
        }
    }
    public void addIfInventorySpace(Item i) throws IllegalStateException{
    	if (inventory.size() < 14){
    		inventory.add(i);
    	} else {
    		throw new IllegalStateException();
    	}
    }
    public ArrayList<Item> getInventory(){
    	return inventory;
    }
    public void addExperience(int monsterExp){
    	experience += (monsterExp + getBoost());
    	this.levelIf();
    }
    public boolean usePotion(ItemType type){
    	boolean used = false;
    	switch(type){
    	case HEALTH:
    		used = heal();
    		break;
    	case EXPERIENCE:
    		used = boost();
    		break;
    	}
    	if (used){
    		for (Item i: inventory){
    			if (i.getType().equals(type)){
    				inventory.remove(i);
    				return used;
    			}
    		}
    	}
    	return used;
    }
    private boolean boostActive(){
    	return boostCount != 0;
    }
    private int getBoost(){
    	if (boostActive()){
    		boostCount--;
    		return 5 * ((level - 1) + EXP_BOOST);
    	}
    	return 0;
    }
    private boolean heal(){
    	if (currentHealth < maxHealth){
    		int value = ItemType.HEALTH.getValue(level);
    		if (maxHealth - currentHealth < value){
    			currentHealth = maxHealth;
    		} else {
    			currentHealth += ItemType.HEALTH.getValue(level);
    		}
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
			boostActive.setContentText("Experience boost still active!\n"
					+ "Please wait " + boostCount + " more battles.");
			boostActive.show();
		}
    	return boosted;
    }
    private void levelIf(){
    	if (expToNextLevel - experience <= 0){
    		level += 1;
    		experience = Math.abs(expToNextLevel - experience);
    		expToNextLevel += 25;
    	}
    }
    public void moveAnimated(Direction dir, Level currentLevel){
    	move(dir, currentLevel);
    	setSprite(prof.getSpriteDirection(dir));
    }
    @Override
    public void die(){
        this.sprite = new Image("assets/skull.png");
        alive = false;
    }
	@Override
	public void setAttacker(Actor actor) {
        this.attacker = actor;
    }
	public void setName(String input) {
        this.name = input;
    }
	public void setSprite(Image image) {
        sprite = image;
    }
	public int getLevel() {
        return level;
    }
	public String getName() {
        return this.name;
    }
	public int getActualHealth() {
        return currentHealth;
    }
	public int getActualExp() {
        return this.experience;
    }
	public HeroType getProfession() {
        return this.prof;
    }
	public double getExpPercent() {
        return (double)experience / (double)expToNextLevel;
    }
	@Override
    	public Image getSprite() {
        return sprite;
    }
	public Actor getAttacker(){
		return attacker;
	}
	
	public int getNumPotions(ItemType type){
		int num = 0;
		for (Item i: inventory){
			if (i.getType() == type){
				num++;
			}
		}
		return num;
	}
}

	
	
