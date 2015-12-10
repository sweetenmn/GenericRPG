package actors;

import java.util.ArrayList;

import terrain.Item;
import terrain.ItemType;
import javafx.scene.image.Image;

public enum MonsterType{
	OGRE(20, 3, 6, 30){
		@Override
		public Image getAvatar() {return new Image("assets/sprites/ogre_sprite.png");}
		@Override
		public Image getCombatAvatar(){return new Image("assets/combat/ogre_combat.png");}
		@Override
		public ArrayList<Item> getLoot(){return setLoot(0, 1);}
		
	}, DEMON(15, 5, 4, 15){
		@Override
		public Image getAvatar(){return new Image("assets/sprites/demon_sprite.png");}
		@Override
		public Image getCombatAvatar(){return new Image("assets/combat/demon_combat.png");}
		@Override
		public ArrayList<Item> getLoot(){return setLoot(1, 0);}
		
	}, DOOMFACE(25, 6, 5, 25){
		@Override
		public Image getAvatar(){return new Image("assets/sprites/tentacle_sprite.png");}
		@Override
		public Image getCombatAvatar(){return new Image("assets/combat/tentacle_combat.png");}
		@Override
		public ArrayList<Item> getLoot(){return setLoot(1,1);}
	};
	
    private int maxHealth, luck, attack, expValue;
        
    private MonsterType(int maxHealth, int luck, int attack, int expValue){
    	this.maxHealth = maxHealth;
    	this.luck = luck;
    	this.attack = attack;
    	this.expValue = expValue;
    }
    
    public abstract Image getAvatar();
    public abstract Image getCombatAvatar();
    public abstract ArrayList<Item> getLoot();
    
    public int getAttack(int level){return attack + (level * 2);}
    public int getLuck(){return luck;}
    public int getExpValue(int level){return expValue + (level * 3);}
    public int getMaxHealth(int level){return maxHealth + (level * 2);}
    
    private static ArrayList<Item> setLoot(int healthPotions, int expPotions){
    	ArrayList<Item> loot = new ArrayList<Item>();
    	for (int j = 0; j < expPotions ; j++){
    		loot.add(new Item(ItemType.EXPERIENCE));
    	}
    	for (int i = 0; i < healthPotions; i++){
    		loot.add(new Item(ItemType.HEALTH));
    	}
    	return loot;
    }
}