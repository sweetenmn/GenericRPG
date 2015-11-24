package menu;

import game.Drawable;
import game.Position;
import game.graphics.Camera;

import java.util.ArrayList;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class StartScreen extends Drawable {
	int height, width;
	ArrayList<Image> clickables;
	public StartScreen(int width, int height){
		super();
		this.height = height;
		this.width = width;
		this.clickables = new ArrayList<>();
		addMenu();
	}
	
	private void addMenu(){
		contents.add(new MenuObject(MenuImage.TITLE));
		contents.add(new MenuObject(MenuImage.NEW_GAME));
		contents.add(new MenuObject(MenuImage.LOAD_GAME));
	}
	
	public void render(Canvas canvas, Camera camera){
		this.draw(canvas, camera);
	}
	
	
	@Override
	public void draw(Canvas canvas, Camera camera){
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setFill(Color.DARKGRAY);
		gc.fillRect(0, 0, 1000, 1000);
		drawGrid(canvas,camera);
		for (Drawable obj : contents) {
			obj.draw(canvas, camera);
		}
	}
    private void drawGrid(Canvas canvas, Camera camera) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.LIGHTGRAY);
        for (int j = 0; j < canvas.getWidth(); j += 32) {
            int offsetX = camera.getPosition().getX() % 32;
            int offsetY = camera.getPosition().getY() % 32;
            gc.strokeLine(j - offsetX, 0, j - offsetX, canvas.getHeight());
            gc.strokeLine(0, j - offsetY, canvas.getWidth(), j - offsetY);
        }
    }

	
	
	
	
}
