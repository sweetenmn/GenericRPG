package terrain;

import javafx.scene.image.Image;

public enum Item{
	Health("Health Potion", "Heals the Hero for 10 health.", 10){
		public Image getAvatar(){
			return new Image("assets/health_potion");
		};
	},
	Experience("Experience Potion", "Grants the Hero additional experience", 10){
		public Image getAvatar(){
			return new Image("assets/exp_potion");
		};
	};
		private String name;
		private String description;
		private int value;
		
		public abstract Image getAvatar();
		public String getName(){return name;}
		public String getDescription(){return description;}
		public int value(){return value;}
				
		Item(String name, String description, int value){
			this.name = name;
			this.description = description;
			this.value = value;
		}
	}

