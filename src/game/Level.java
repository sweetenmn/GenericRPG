package game;

import actors.Hero;
import actors.Profession;
import game.graphics.Camera;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import terrain.Exit;
import terrain.Floor;
import terrain.Wall;


/**
 * Created by josephbenton on 9/13/15.
 */
public class Level extends Drawable {
    int Depth;
    int height;
    int width;
    Hero hero;
    boolean[][] wallMap;

    public Level(int depth, int width, int height) {
        super();
        Depth = depth;
        this.height = height;
        this.width = width;
        wallMap = new boolean[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                addFloor(i, j);
            }
        }
    }
    public void addFloor(int x, int y) {
        Position p = new Position(x, y);
        contents.add(new Floor(p));
    }

    public void addWall(int x, int y) {
        Position p = new Position(x, y);
        contents.add(new Wall(p));
        wallMap[p.getX()][p.getY()] = true;
    }

    public void addExit(int x, int y) {
        Position p = new Position(x, y);
        contents.add(new Exit(p));
    }

    public void addHero(int x, int y) {
        hero = new Hero(Profession.ROGUE);
        hero.setPosition(x, y);
        contents.add(hero);
    }

    public boolean isClear(Position p) {
        return !wallMap[p.getX()][p.getY()];
    }

    @Override
    public void draw(Canvas canvas, Camera camera) {
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

    public Hero getHero() {

        return hero;
    }

    public boolean inBounds(Position p) {
        boolean inBoundsX = (p.getX() < width && p.getX() >= 0);
        boolean inBoundsY = (p.getY() < height && p.getY() >= 0);
        return inBoundsX && inBoundsY;
    }
}
