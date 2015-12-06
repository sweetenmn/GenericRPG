package terrain;

import javafx.scene.image.Image;

public class HealthPotion extends Item{

	public HealthPotion(HealthPotion i) {
		super((Item) i);
			i.name = "Health Potion";
			i.description = "Heals the Hero.";
			i.value = 10;
			this.sprite = new Image("assets/health_potion.png");
	}
	
}