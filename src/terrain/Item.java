package terrain;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import game.Drawable;
import game.Position;

public class Item extends Drawable{
	private ItemType type;
	private Position gridPosition;
	
	public Item(ItemType type){
		this.type = type;
		this.sprite = type.getAvatar();
	}
	
	
	
	public ItemType getType(){
		return type;
	}
	
	public Image getSprite(){
		return sprite;
	}
	
	public void setGridPosition(Position p){
		this.gridPosition = p;
	}
	
	public ImageView getImageView(){
		return new ImageView(getSprite());
	}

	
}