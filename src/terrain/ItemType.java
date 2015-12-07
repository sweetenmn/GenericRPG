package terrain;

import javafx.scene.image.Image;

public enum ItemType{
	HEALTH("Health Potion", "Heals the Hero for 10 health.", 10){
		@Override
		public Image getAvatar(){
			return new Image("assets/health_potion.png");
		};
	},
	EXPERIENCE("Experience Potion", "Grants the Hero additional experience", 10){
		@Override
		public Image getAvatar(){
			return new Image("assets/exp_potion.png");
		};
	};
		private String name;
		private String description;
		private int value;
		
		public abstract Image getAvatar();
		public String getName(){return name;}
		public String getDescription(){return description;}
		public int getValue(){return value;}
				
		ItemType(String name, String description, int value){
			this.name = name;
			this.description = description;
			this.value = value;
		}
}