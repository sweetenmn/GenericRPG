package actors;

import game.Direction;
import javafx.scene.image.Image;


public enum HeroType {
    ROGUE(30, 9, 7, 3) {



		@Override
		public Image getSpriteDirection(Direction dir){
			String img = "assets/sprites/rogue_" + getEnd(dir);
			return new Image(img);
		}

		@Override
		public Image getCombatAvatar(){
			return new Image("assets/combat/rogue_combat.png");
		}

		@Override
		public Image getPortrait(){
			return new Image("assets/sprites/rogue_portrait.png");
		}
    },
    MAGE(25, 8, 10, 5){
		@Override
		public Image getSpriteDirection(Direction dir){
			String img = "assets/sprites/mage_" + getEnd(dir);
			return new Image(img);
		}

		@Override
		public Image getCombatAvatar(){
			return new Image("assets/combat/mage_combat.png");
		}

		@Override
		public Image getPortrait(){
			return new Image("assets/sprites/mage_portrait.png");
		}
    },
    KNIGHT(35, 10, 1, 0){
		@Override
		public Image getSpriteDirection(Direction dir){
			String img = "assets/sprites/knight_" + getEnd(dir);
			return new Image(img);
		}

		@Override
		public Image getCombatAvatar(){
			return new Image("assets/combat/knight_combat.png");
		}

		@Override
		public Image getPortrait(){
			return new Image("assets/sprites/knight_portrait.png");
		}
    };
    private int maxHealth, attack, luck, intellect;
    
   

    public abstract Image getSpriteDirection(Direction dir);
    public abstract Image getCombatAvatar();
    public abstract Image getPortrait();

    public int getMaxHealth(){return maxHealth;}
    public int getAttack(){return attack;}
    public int getLuck(){return luck;}
    public int getIntel(){return intellect;}
    
    private static String getEnd(Direction dir){
    	String end = "";
		switch(dir){
		case DOWN:
			end = "down.png";
			break;
		case LEFT:
			end = "left.png";
			break;
		case RIGHT:
			end = "right.png";
			break;
		case UP:
			end = "up.png";
			break;
		}
		return end;    	
    }

    HeroType(int health, int attack,  int luck, int intellect) {
        this.maxHealth = health;
        this.attack = attack;
        this.luck = luck;
        this.intellect = intellect;
    }
}