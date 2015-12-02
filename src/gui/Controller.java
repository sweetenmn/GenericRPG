package gui;


import java.util.ArrayList;

import actors.Hero;
import actors.Profession;
import game.*;
import game.graphics.Camera;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Controller {
    private long FRAMES_PER_SEC = 60L;
    private long NANO_INTERVAL = 1000000000L / FRAMES_PER_SEC;
    @FXML
    BorderPane pane;
    @FXML
    Canvas canvas;
    @FXML
    Pane startPane, adventurePane;
    @FXML
    GridPane inventory;
    @FXML
    Button attackButton, inspectButton, startButton;
    @FXML
    TextField nameInput;
    @FXML
    ImageView portrait;
    @FXML
    Text name;
    @FXML
    Rectangle mageSelect, warriorSelect;
    @FXML
    ProgressBar healthBar, expBar;
    
    Profession profSelected;

    GraphicsContext gc;
    Camera camera = new Camera(new Position(0, 0));
    Position cameraDragStartPos;
    double startX, startY;
    Game game;
    private AnimationTimer timer = new AnimationTimer() {
        long last = 0;

        @Override
        public void handle(long now) {
            if (now - last > NANO_INTERVAL) {
                healthBar.setProgress(game.getHeroHealthPercent());
                expBar.setProgress(game.getHeroExpPercent());
                name.setText(game.getHeroName() + " | Level " + game.getHeroLevel());
                game.render(canvas, camera);
                game.checkForDeath(canvas);
            }
            last = now;
        }
    };


    @FXML
    public void initialize() {
        startHandlingClicks();     
        game = new Game();
        game.setState(GameState.START);
        gc = canvas.getGraphicsContext2D();
        timer.start();
    }
    
   
    
    private void startHandlingClicks(){
        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED,
        		ev ->{
        			handleClickAt(ev.getX(), ev.getY());     			
        		}); 	
    }
    
    
    private void handleClickAt(double x, double y){
    	if (game.getState() == GameState.START){
    		checkNewClicked(x, y);
    		checkLoadClicked(x, y);	
    	}
    }
    
    private void checkNewClicked(double x, double y){
    	if (x > 180 && x < 354 && y >175 && y < 200){
    		characterCreation();
    	}
    }
    private void checkLoadClicked(double x, double y){
    	if (x > 170 && x < 365 && y > 214 && y < 236){
    		System.out.println("load game");
    		loadCharacter();
    	}
    }
    
    private void characterCreation(){
    	startPane.setVisible(true);
    	game.setState(GameState.CHARACTER_CREATE);
    }
    
    private void loadCharacter(){
    	//loadPane.setVisible(true);
    	//game.setState(GameState.CHARACTER_LOAD);
    }
    
    @FXML
    public void startGame(){
    	if (gameReady()){
    		Hero hero = new Hero(Profession.MAGE, 2, 2);
            Level currentLevel = new Level(hero, "src/assets/Levels/L1.txt");
            game.changeLevel(currentLevel);
            //here-- call method to either load or create new char based on state
            game.setHeroName(nameInput.getText());
    		game.setState(GameState.WALKING);
    		viewWalking();
    		startHandlingWalk();
    		startHandlingDrag();
    	}
    }
    
    private boolean gameReady(){
    	return profSelected != null && !nameInput.getText().equals("");
    }
    private void startHandlingWalk(){
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
    }
    
    private void startHandlingDrag(){
        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED,
                ev -> {
                    camera.setPosition((int)(cameraDragStartPos.getX() + startX - ev.getX()),
                    		cameraDragStartPos.getY() + (int)(startY - ev.getY()));
                });
        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED,
                ev -> {
                    cameraDragStartPos = camera.getPosition();
                    startX = ev.getX();
                    startY = ev.getY();
                });
    }
    
    private void viewWalking(){
    	startPane.setVisible(false);
		adventurePane.setVisible(true);
		inventory.setVisible(true);
		portrait.setImage(new Image("/assets/mage_portrait.png"));
    }

    @FXML
    public void attack() {
        game.heroAtk();
    }

    @FXML
    public void pass() {
        game.step();
    }

    @FXML
    public void inspect() {

    }

    @FXML
    public void button4() {

    }

    @FXML
    public void up() {
        if (game.getState().equals(GameState.WALKING)) {
            game.moveHero(Direction.UP);
        	}
    	}

    @FXML
    public void down() {
        if (game.getState().equals(GameState.WALKING)) {
            game.moveHero(Direction.DOWN);
            }    
    }

    @FXML
    public void left() {
        if (game.getState().equals(GameState.WALKING)) {
            game.moveHero(Direction.LEFT);
        	}    
    }

    @FXML
    public void right() {
        if (game.getState().equals(GameState.WALKING)) {
            game.moveHero(Direction.RIGHT);
            }
    }
    @FXML
    public void selectMage(){
    	unselectOthers(mageSelect);
    	profSelected = Profession.MAGE;
    }
    
    @FXML
    public void selectWarrior(){
    	unselectOthers(warriorSelect);
    }
    
    private ArrayList<Rectangle> selectBoxes(){
    	ArrayList<Rectangle> boxes = new ArrayList<Rectangle>();
    	boxes.add(mageSelect);
    	boxes.add(warriorSelect);
    	return boxes;
    }
    
    private void unselectOthers(Rectangle selected){
    	selected.setVisible(true);
    	for (Rectangle box: selectBoxes()){
    		if (!box.equals(selected)){
    			box.setVisible(false);
    		}
    	}
    }

}
