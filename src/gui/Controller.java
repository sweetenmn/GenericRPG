package gui;

import java.util.ArrayList;
import persistence.CharacterBank;
import actors.Hero;
import actors.Profession;
import game.*;
import game.graphics.Camera;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Controller{
    private long FRAMES_PER_SEC = 20L;
    private long NANO_INTERVAL = 100000000L / FRAMES_PER_SEC;
    @FXML
    BorderPane pane;
    @FXML
    Canvas canvas;
    @FXML
    Pane startPane, adventurePane, loadPane;
    @FXML
    Pane combatPane;
    @FXML
    GridPane inventory;
    @FXML
    Button enterCombatButton, inspectButton, startButton, saveButton, exitButton, loadButton, clearButton;
    @FXML
    TextField nameInput;
    @FXML
    ImageView portrait, loadingView;
    @FXML
    Text name, loadingName, savedText;
    @FXML
    Rectangle mageSelect, knightSelect, rogueSelect;
    @FXML
    ProgressBar healthBar, expBar;
    @FXML
    ProgressBar combatHeroHealth;
    @FXML
    ProgressBar combatMonsterHealth;
    @FXML
    ChoiceBox<String> characterChoice;
    
    Profession profSelected;

    GraphicsContext gc;
    Camera camera = new Camera(new Position(0, 0));
    Position cameraDragStartPos;
    CharacterBank characters = new CharacterBank();
    double startX, startY;
    Game game;
    private AnimationTimer timer = new AnimationTimer(){
        long last = 0;

        @Override
        public void handle(long now){
            if(now - last > NANO_INTERVAL){
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
    public void initialize(){
    	game = new Game();
    	game.setState(GameState.START);
        startHandlingClicks();     
        gc = canvas.getGraphicsContext2D();
        game.render(canvas, camera);
		startHandlingWalk();
		startHandlingDrag(); 
    }   
    
    private void startHandlingClicks(){
        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED,
        		ev ->{
        			handleClickAt(ev.getX(), ev.getY());     			
        		}); 	
    }
    
    private void handleClickAt(double x, double y){
    	if (game.getState() == GameState.START |
    			game.getState() == GameState.CHARACTER_CREATE |
    			game.getState() == GameState.CHARACTER_LOAD){
    		checkNewClicked(x, y);
    		checkLoadClicked(x, y);	
    	}
    }
    
    private void checkNewClicked(double x, double y){
    	if (x > 180 && x < 354 && y >175 && y < 200){
    		viewCharacterCreation();
    	}
    }
    private void checkLoadClicked(double x, double y){
    	if (x > 170 && x < 365 && y > 214 && y < 236){
    		viewCharacterLoading();
    	}
    }
    
    private void viewCharacterCreation(){
    	startPane.setVisible(true);
    	loadPane.setVisible(false);
    	game.setState(GameState.CHARACTER_CREATE);
    }
    
    private void viewCharacterLoading(){
    	startPane.setVisible(false);
    	loadPane.setVisible(true);
    	setChoice();
    	game.setState(GameState.CHARACTER_LOAD);
    }
       
    @FXML
    public void startGame(){
    	switch(game.getState()){
		case CHARACTER_CREATE:
			newGame();
			break;
		case CHARACTER_LOAD:
			loadGame();
			break;
		case COMBAT: case END: case LOADING: case START: case WALKING:
			break;   	
    	}
    }
    
    @FXML
    public void loadCharacter(){
    	if (loadButton.getText().equals("VIEW")){
    		if (characterChoice.getSelectionModel().getSelectedItem() != null){
    			clearButton.setVisible(true);
    			loadButton.setText("START");
    			displayChoice();
    		}
    	} else {
    		startGame();
    		clearChoice();
    	}
    }
    
    private void newGame(){
    	if (gameReady()){
    		Hero hero = new Hero(profSelected, nameInput.getText());
    		unselectAll();
    		nameInput.clear();
    		showLevel(hero);
    		saveHero();
    		setChoice();
    		savedText.setVisible(false);
    	}    	
    }
    
    private void loadGame(){
    	String name = characterChoice.getSelectionModel().getSelectedItem();
		Hero hero = characters.getHero(name);
		showLevel(hero);
    }
    
    private void setChoice(){
    	characterChoice.setItems(characters.getSavedNames());
    }
    
    public void displayChoice(){
    	if (characterChoice.getSelectionModel().getSelectedItem() != null){
    		String name = characterChoice.getSelectionModel().getSelectedItem();
    		Hero hero = characters.getHero(name);
    		loadingName.setVisible(true);
    		loadingName.setText(name + " | Level " + hero.getLevel());
    		loadingView.setImage(hero.getProfession().getPortrait());
    		
    	}
    }
    
    @FXML
    public void clearChoice(){
    	loadingName.setVisible(false);
    	characterChoice.getSelectionModel().clearSelection();
    	loadingView.setImage(null);
    	loadButton.setText("VIEW");
    	clearButton.setVisible(false);
    }
    
    private void showLevel(Hero hero){
    	game.setState(GameState.WALKING);
		Level currentLevel = new Level(hero);
		game.changeLevel(currentLevel);
		viewWalking();  	
		timer.start();
    }
    
    private boolean gameReady(){
    	boolean ready = profSelected != null;
    	if(!nameInput.getText().matches("^[a-zA-Z]+$")){
    		ready = false;
    		illegalName();
    		
    	}else if(characters.heroExists(nameInput.getText())){
    		ready = ready & confirm(ConfirmType.OVERWRITE);
    	}
    	return ready;
    }
    
    private void illegalName(){
    	Alert badName = new Alert(AlertType.INFORMATION);
    	badName.setContentText("That is not an "
    		+ "appropriate name for a hero!");
    	badName.show();
    }
    
    private boolean confirm(ConfirmType type){
    	Alert alert = new Alert(AlertType.CONFIRMATION);
    	switch(type){
		case EXIT:
			createQuitAlert(alert);
			break;
		case OVERWRITE:
			createOverwriteAlert(alert);
			break;
    	}
    	alert.showAndWait();
    	return alert.getResult() == ButtonType.OK;
    }
    
    private void startHandlingWalk(){
    	pane.addEventHandler(KeyEvent.KEY_PRESSED,
                ev -> {
                	clearSavedMessage();
                	KeyCode code = ev.getCode();
                    if (code == KeyCode.W || code == KeyCode.UP) {
                        up();
                    } else if (code == KeyCode.A|| code == KeyCode.LEFT){
                        left();
                    } else if (code == KeyCode.S || code == KeyCode.DOWN){
                        down();
                    } else if (code == KeyCode.D || code == KeyCode.RIGHT){
                        right();
                    } else if (code == KeyCode.SHIFT){
                    	game.heroAtk();
                    }
                    checkHeroAtExit();
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
    	camera = new Camera(new Position(0, 0));
    	startPane.setVisible(false);
    	loadPane.setVisible(false);
		adventurePane.setVisible(true);
		inventory.setVisible(true);
		portrait.setImage(game.getHero().getProfession().getPortrait());
    }
    
    @FXML
    public void saveHero(){
    	characters.saveHero(game.getHero());
    	savedText.setVisible(true);
    }

    @FXML
    public void enterCombat(){
    	if(game.heroAtk()){
    		adventurePane.setVisible(false);
    		combatPane.setVisible(true);
    	}
    }
    
    private void checkHeroAtExit(){
    	if (game.checkAtExit()){
    		showLevel(game.getHero());
    	}
    }

    @FXML
    public void inspect(){

    }

    @FXML
    public void up(){
        if (game.getState().equals(GameState.WALKING)) {
            game.moveHero(Direction.UP);
        	}
    	}

    @FXML
    public void down(){
        if(game.getState().equals(GameState.WALKING)){
            game.moveHero(Direction.DOWN);
            }    
    }

    @FXML
    public void left(){
        if (game.getState().equals(GameState.WALKING)){
            game.moveHero(Direction.LEFT);
        	}    
    }

    @FXML
    public void right(){
        if (game.getState().equals(GameState.WALKING)){
            game.moveHero(Direction.RIGHT);
            }
    }
    
    @FXML
    public void selectMage(){
    	unselectOthers(mageSelect);
    	profSelected = Profession.MAGE;
    }
    
    @FXML
    public void selectKnight(){
    	unselectOthers(knightSelect);
    	profSelected = Profession.KNIGHT;
    }
    
    @FXML
    public void selectRogue(){
    	unselectOthers(rogueSelect);
    	profSelected = Profession.ROGUE;
    }
    
    private ArrayList<Rectangle> selectBoxes(){
    	ArrayList<Rectangle> boxes = new ArrayList<Rectangle>();
    	boxes.add(mageSelect);
    	boxes.add(knightSelect);
    	boxes.add(rogueSelect);
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
    
    private void unselectAll(){
    	for (Rectangle box: selectBoxes()){
    		box.setVisible(false);
    	}
    }
    
    private void clearSavedMessage(){
    	if (savedText.isVisible()){
    		savedText.setVisible(false);
    	}
    }
    
    private void createOverwriteAlert(Alert alert){
    	alert.setTitle("Overwrite Hero Confirmation");
    	alert.setHeaderText("Hero already exists!");
    	alert.setContentText("The hero " + nameInput.getText() 
    			+ " already exists.\n"
    			+ "Their progress will be overwritten.\n" 
    			+ "Do you wish to continue?");
    }
    
    private void createQuitAlert(Alert alert){
    	alert.setTitle("Exit Confirmation");
    	alert.setContentText("Exit to main screen without saving?\n"
    			+ "Unsaved progress will be lost.");
    }
        
    @FXML
    public void exitToStart(){
    	if (confirm(ConfirmType.EXIT)){
    		timer.stop();
    		game.setState(GameState.START);
    		adventurePane.setVisible(false);
    		combatPane.setVisible(false);
    		inventory.setVisible(false);
    		game.render(canvas, camera);
    	}	
    }
}