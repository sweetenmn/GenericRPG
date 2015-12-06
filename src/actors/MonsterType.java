package actors;

import java.util.ArrayList;

import javafx.scene.image.Image;

public enum MonsterType {
	OGRE(15, 1, 5, 30) {
		@Override
		public Image getAvatar() {
			return new Image("assets/ogre_sprite.png");
		}

		@Override
		public Image getCombatAvatar() {
			return new Image("assets/ogre_combat.png");
		}

		@Override
		public ArrayList<String> getLoot() {
			return setLoot(0, 1);
		}
	}, DEMON(10, 2, 3, 15) {
		@Override
		public Image getAvatar() {
			return new Image("assets/demon_sprite.png");
		}

		@Override
		public Image getCombatAvatar() {
			return new Image("assets/demon_combat.png");
		}

		@Override
		public ArrayList<String> getLoot() {
			return setLoot(1, 0);
		}
	}, TENTACLE(20, 4, 4, 25) {
		@Override
		public Image getAvatar() {
			return new Image("assets/tentacle_sprite.png");
		}

		@Override
		public Image getCombatAvatar() {
			return new Image("assets/tentacle_combat.png");
		}

		@Override
		public ArrayList<String> getLoot() {
			return setLoot(1,1);
		}
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
    public abstract ArrayList<String> getLoot();
    
    public int getAttack(int level){
    	return attack + ((level-1) * 2);
    }
    public int getLuck(int level){
    	return luck + ((level-1) * 1);
    }
    public int getExpValue(int level){
    	return expValue + ((level-1) * 3);
    }
    public int getMaxHealth(int level){
    	return maxHealth +((level-1) * 2);
    }
    

    private static ArrayList<String> setLoot(int healthPotions, int expPotions){
    	ArrayList<String> loot = new ArrayList<String>();
    	for (int i = 0; i <= healthPotions; i++){
    		loot.add("HEALTH");
    	}
    	for (int j = 0; j <= expPotions; j++){
    		loot.add("EXP");
    	}
    	return loot;
    }
    
    

}
