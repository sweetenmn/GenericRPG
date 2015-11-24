package gui;


import actors.Hero;
import actors.Profession;
import game.Direction;
import game.Game;
import game.GameState;
import game.Level;
import game.Position;
import game.graphics.Camera;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

import java.io.File;

import menu.StartScreen;


public class Controller {
    private long FRAMES_PER_SEC = 60L;
    private long NANO_INTERVAL = 1000000000L / FRAMES_PER_SEC;
    @FXML
    BorderPane pane;
    @FXML
    Canvas canvas;
    @FXML
    Button button;
    @FXML
    ImageView portrait;
    @FXML
    Text name;
    @FXML
    ProgressBar healthBar;

    GraphicsContext gc;
    Camera camera = new Camera(new Position(0, 0));
    Position cameraDragStartPos;
    double startX;
    double startY;
    Game game;
    GameState state = GameState.START;
    StartScreen startScreen;
    private AnimationTimer timer = new AnimationTimer() {
        long last = 0;

        @Override
       
        public void handle(long now) {
            if (now - last > NANO_INTERVAL) {
            	switch(state){
				case ADVENTURE:
					healthBar.setProgress(game.getHeroHealthPercent());
					game.render(canvas, camera);
					game.checkStates(timer, canvas);
					break;
				case COMBAT:
					break;
				case START:
					startScreen.render(canvas, camera);
					break;
				default:
					break;
            	
            	}
            }
            last = now;
        }
    };


    @FXML
    public void initialize() {
        healthBar.setProgress(1.0);
        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED,
                ev -> {
                    camera.setPosition((int)(cameraDragStartPos.getX() + startX - ev.getX()), cameraDragStartPos.getY() + (int)(startY - ev.getY()));
                });

        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED,
                ev -> {
                    cameraDragStartPos = camera.getPosition();
                    startX = ev.getX();
                    startY = ev.getY();

                });
        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED,
        		ev ->{
        			handleClickAt(ev.getX(), ev.getY());
        			
        		});
        pane.addEventHandler(KeyEvent.KEY_PRESSED,
                ev -> {
                	KeyCode code = ev.getCode();
                    if (code == KeyCode.W || code == KeyCode.UP) {
                        up();
                    } else if (code == KeyCode.A|| code == KeyCode.LEFT)  {
                        left();
                    } else if (code == KeyCode.S || code == KeyCode.DOWN) {
                        down();
                    } else if (code == KeyCode.D || code == KeyCode.RIGHT) {
                        right();
                    } else if (code == KeyCode.SHIFT){
                    	game.heroAtk();
                    }
                });
        Level currentLevel = new Level(new Hero(Profession.WIZARD, 2, 2), "src/assets/Levels/L1.txt");
        game = new Game();
        game.changeLevel(currentLevel);
        startScreen = new StartScreen(0,0);
        gc = canvas.getGraphicsContext2D();
        timer.start();
    }
    
    private void handleClickAt(double x, double y){
    	if (state == GameState.START){
    		checkNewClicked(x, y);
    		checkLoadClicked(x, y);
    		
    	}
    }
    
    private void checkNewClicked(double x, double y){
    	if (x > 180 && x < 354 && y >175 && y < 200){
    		state = GameState.ADVENTURE;
    		portrait.setImage(new Image("/assets/mage_portrait.png"));
    		System.out.println("new game");
    	}
    }
    private void checkLoadClicked(double x, double y){
    	if (x > 170 && x < 365 && y > 214 && y < 236){
    		System.out.println("load game");
    	}
    }
    

    
    

    private void buildLevel(Level currentLevel) {
        currentLevel.addExit(5, 5);
        currentLevel.addWall(0, 0);
        currentLevel.addWall(0, 1);
        currentLevel.addWall(0, 2);
        currentLevel.addWall(0, 3);
        currentLevel.addWall(0, 4);
        currentLevel.addWall(0, 5);
        currentLevel.addWall(1, 5);
        currentLevel.addWall(3, 5);
        currentLevel.addWall(4, 5);
        currentLevel.addWall(4, 4);
        currentLevel.addWall(4, 3);
        currentLevel.addWall(4, 2);
        currentLevel.addWall(4, 1);
        currentLevel.addWall(4, 0);
        currentLevel.addWall(1, 6);
        currentLevel.addWall(1, 7);
        currentLevel.addWall(1, 8);
        currentLevel.addWall(1, 9);
        currentLevel.addWall(3, 6);
        currentLevel.addWall(3, 7);
        currentLevel.addWall(2, 9);
        currentLevel.addMonster(6,7);
        currentLevel.addMonster(10,10);
        currentLevel.addMonster(5,5);
    }

    @FXML
    public void button1() {
        game.heroAtk();

    }

    @FXML
    public void button2() {
        game.step();

    }

    @FXML
    public void button3() {

    }

    @FXML
    public void button4() {

    }

    @FXML
    public void up() {
        game.moveHero(Direction.UP);
    }

    @FXML
    public void down() {
        game.moveHero(Direction.DOWN);
    }

    @FXML
    public void left() {
        game.moveHero(Direction.LEFT);
    }

    @FXML
    public void right() {
        game.moveHero(Direction.RIGHT);
    }

}
