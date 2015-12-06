package terrain;

import game.Drawable;

public class Item extends Drawable{
	public String name;
	public int value;
	public String description;
	
	public Item(Item i){
		this.name = i.name;
		this.description = i.description;
		this.value = i.value;
	}
}
