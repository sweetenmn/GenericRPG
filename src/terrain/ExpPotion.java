package terrain;

import javafx.scene.image.Image;

public class ExpPotion extends Item{

	public ExpPotion(ExpPotion i) {
		super((Item) i);
			i.name = "Experience Potion";
			i.description = "Grants the Hero additional experience.";
			i.value = 10;
			this.sprite = new Image("assets/exp_potion.png");
	}
}