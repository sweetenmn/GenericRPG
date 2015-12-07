package terrain;

import game.Drawable;
import game.Position;

public class Item extends Drawable{
	private ItemType type;
	
	public Item(ItemType type, Position pos){
		this.type = type;
		this.sprite = type.getAvatar();
		this.position = pos;
	}
	
	public ItemType getType(){
		return type;
	}

	
}