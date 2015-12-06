package game;

import actors.Actor;
import actors.Hero;
import actors.Mob;
import actors.Monster;
import game.graphics.Camera;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.paint.Color;
import terrain.Exit;
import terrain.Floor;
import terrain.Wall;
import util.Dice;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * Created by josephbenton on 9/13/15.
 */
public class Level extends Drawable {
    int height;
    int width;
    Hero hero;
    ArrayList<Mob> monsters;
    boolean[][] wallMap;

    public Level(int width, int height, Hero hero) {
        super();
        this.hero = hero;
        this.height = height;
        this.width = width;
        this.monsters = new ArrayList<>();
        wallMap = new boolean[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                addFloor(i, j);
            }
        }
    }

    public Level(Hero hero, String map) {
        monsters = new ArrayList<>();
        buildLevel(map);
        this.hero = hero;
    }
    
    public Level(Hero hero){
    	monsters = new ArrayList<>();
    	this.hero = hero;
    	buildLevel(randomLevel());
    	
    }
    
    private void buildLevel(String map){
    	Scanner scan = null;
        try {
            scan = new Scanner(new File(map));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
        width = scan.nextInt();
        height = scan.nextInt();
        wallMap = new boolean[width][height];
        scan.nextLine();
        for (int i = 0; i < height; i++) {
            String line = scan.nextLine();
            for (int j = 0; j < width; j++) {
                addFloor(j, i);
                if (line.charAt(j) == 'H') {
                    hero.setPosition(j, i);
                } else if (line.charAt(j) == 'M') {
                    addMonster(j, i);
                } else if (line.charAt(j) == 'E') {
                    addExit(j, i);
                } else if (line.charAt(j) == '#') {
                    wallMap[j][i] = true;
                    addWall(j, i);
                }
            }
        }
    	
    }
    
    public String randomLevel(){
    	Dice dice = new Dice(12);
    	int level = dice.roll();
    	return "src/assets/Levels/L" + level + ".txt";
    }

    public void addFloor(int x, int y) {
        Position p = new Position(x, y);
        contents.add(new Floor(p));
    }
    public void addMonster(int x, int y) {
        Monster monster = new Monster();
        monster.setPosition(x, y);
        contents.add(monster);
        monsters.add(monster);

    }

    public ArrayList<Mob> getMonsters() {
        return monsters;
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
        hero.draw(canvas, camera);
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
