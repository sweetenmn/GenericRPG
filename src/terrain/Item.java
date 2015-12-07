package terrain;

import game.Drawable;

public class Item extends Drawable{
	private ItemType type;
	
	public Item(ItemType type){
		this.type = type;
		this.sprite = type.getAvatar();
	}
	
	
	
	public ItemType getType(){
		return type;
	}

	
}