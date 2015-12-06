package terrain;

import util.Dice;
import game.Drawable;

public class Item extends Drawable{
	public String name, description;
	public int value;
	
	public Item(ItemType type){
		type = randomType();
		if(type == ItemType.Health){
			this.name = ItemType.Health.getName();
			this.description = ItemType.Health.getDescription();
			this.value = ItemType.Health.getValue();
		}else if(type == ItemType.Experience){
			this.name = ItemType.Experience.getName();
			this.description = ItemType.Experience.getDescription();
			this.value = ItemType.Experience.getValue();
		}	
	}

	private ItemType randomType(){
		Dice dice = new Dice(2);
    	int result = dice.roll();
    	switch(result){
    	case 0:
    		return ItemType.Health;
    	case 1:
    		return ItemType.Experience;
    	default:
    		return ItemType.Health;
    	}
	}
}