package menu;

public enum MenuImage{
	TITLE("assets/StartScreen/title_BG.png"),
	NEW_GAME("assets/StartScreen/new_game.png"),
	LOAD_GAME("assets/StartScreen/load_game.png"),
    GAME_OVER("assets/game_over.png");
	
	private String imageString;
	private MenuImage(String image){
		this.imageString = image;
	}
	public String image(){
		return imageString;
	}
}