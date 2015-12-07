package actors;

import game.Direction;
import javafx.scene.image.Image;


public enum HeroType {
    ROGUE(25, 5, 7) {



		@Override
		public Image getSpriteDirection(Direction dir){
			String img = "assets/rogue_" + getEnd(dir);
			return new Image(img);
		}

		@Override
		public Image getCombatAvatar(){
			return new Image("assets/rogue_combat.png");
		}

		@Override
		public Image getPortrait(){
			return new Image("assets/rogue_portrait.png");
		}
    },
    MAGE(20, 8, 10){
		@Override
		public Image getSpriteDirection(Direction dir){
			String img = "assets/mage_" + getEnd(dir);
			return new Image(img);
		}

		@Override
		public Image getCombatAvatar(){
			return new Image("assets/mage_combat.png");
		}

		@Override
		public Image getPortrait(){
			return new Image("assets/mage_portrait.png");
		}
    },
    KNIGHT(35, 10, 0){
		@Override
		public Image getSpriteDirection(Direction dir){
			String img = "assets/knight_" + getEnd(dir);
			return new Image(img);
		}

		@Override
		public Image getCombatAvatar(){
			return new Image("assets/knight_combat.png");
		}

		@Override
		public Image getPortrait(){
			return new Image("assets/knight_portrait.png");
		}
    };
    private int maxHealth, attack, luck;
    
   

    public abstract Image getSpriteDirection(Direction dir);
    public abstract Image getCombatAvatar();
    public abstract Image getPortrait();

    public int getMaxHealth(){return maxHealth;}
    public int getAttack(){return attack;}
    public int getLuck(){return luck;}
    
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

    HeroType(int health, int attack,  int luck) {
        this.maxHealth = health;
        this.attack = attack;
        this.luck = luck;
    }
}