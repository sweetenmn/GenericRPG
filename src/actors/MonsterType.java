package actors;

import java.util.ArrayList;

import terrain.Item;
import terrain.ItemType;
import javafx.scene.image.Image;

public enum MonsterType{
	OGRE(20, 1, 5, 30){
		@Override
		public Image getAvatar() {return new Image("assets/ogre_sprite.png");}
		@Override
		public Image getCombatAvatar(){return new Image("assets/ogre_combat.png");}
		@Override
		public ArrayList<Item> getLoot(){return setLoot(0, 1);}
		
	}, DEMON(15, 2, 3, 15){
		@Override
		public Image getAvatar(){return new Image("assets/demon_sprite.png");}
		@Override
		public Image getCombatAvatar(){return new Image("assets/demon_combat.png");}
		@Override
		public ArrayList<Item> getLoot(){return setLoot(1, 0);}
		
	}, TENTACLE(25, 4, 4, 25){
		@Override
		public Image getAvatar(){return new Image("assets/tentacle_sprite.png");}
		@Override
		public Image getCombatAvatar(){return new Image("assets/tentacle_combat.png");}
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
    
    public int getAttack(int level){return attack + ((level-1) * 2);}
    public int getLuck(int level){return luck + ((level-1) * 1);}
    public int getExpValue(int level){return expValue + ((level-1) * 3);}
    public int getMaxHealth(int level){return maxHealth + ((level-1) * 2);}
    
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