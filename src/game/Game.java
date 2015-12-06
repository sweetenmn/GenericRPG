package game;

import actors.Actor;
import actors.Hero;
import actors.Monster;
import actors.MonsterType;
import game.graphics.Camera;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import menu.StartScreen;

import java.util.ArrayList;

import util.Dice;

/**
 * Created by Joseph on 9/20/2015.
 */
public class Game {
    private Hero hero;
    private ArrayList<Monster> mobs;
    private Level currentLevel;
    private GameState prevState;
    private  GameState state;
    private Combat combat;
    private Monster inspected;

    public void moveHero(Direction dir) {
        if (state.equals(GameState.WALKING)&&  hero.isAlive()) {
            hero.moveAnimated(dir, currentLevel);
        }
    }

    public boolean heroAtk() {
    	boolean combat = false;
        if(hero.isAlive()){
            for (Monster m : mobs){
                if(m.getPosition().getDistanceTo(hero.getPosition()) < 2) {
                    startCombat(m);
                    combat = true;
                }
            }
        }
        return combat;
    }
    
    public boolean heroInspect(){
        if (hero.isAlive()) {
            for (Monster m : mobs) {
                if (m.getPosition().getDistanceTo(hero.getPosition()) < 2) {
                	inspected = m;
                    return true;
                }
            }
            
        }
        return false;
    	
    }
    
    public MonsterType getInspected(){
    	return inspected.getType();
    	
    }
    public void changeLevel(Level level) {
        this.currentLevel = level;
        this.hero = level.getHero();
        this.mobs = level.getMonsters();
    }

    public void startCombat(Actor monster) {
        combat = new Combat(hero, monster);
        setState(GameState.COMBAT);
    }
    
    public Hero getHero(){
    	return hero;
    }

    public double getHeroHealthPercent() {
    	return hero.getHealthPercent();
    }
    
    public double getHeroExpPercent() {
    	return hero.getExpPercent();
    }
    
    public int getHeroLevel() {
    	return hero.getLevel();
    }
    
    public String getHeroName(){
    	return hero.getName();
    }
    
    public void setHeroName(String name){
    	hero.setName(name);

    }
    
    public void loadHero(int health, int exp){
    	hero.loadHealth(health);
    	hero.loadExp(exp);
    }

    public void checkForDeath(Canvas canvas){
        if (!hero.isAlive()) {
            gameEnd(canvas);
        }
    }
    
    public boolean checkAtExit(){return currentLevel.atExit(hero.getPosition());}

    private void gameEnd(Canvas canvas){
        state = GameState.END;
        canvas.getGraphicsContext2D().drawImage(new Image("assets/game_over.png"), canvas.getWidth() / 2, canvas.getHeight() / 2);
    }

    public void render(Canvas canvas, Camera camera){
        if (state.equals(GameState.WALKING)) {
            currentLevel.draw(canvas, camera);
        } else if (state.equals(GameState.COMBAT)){
            combat.draw(canvas, camera);
            if (!combat.isMonsterAlive()) state = GameState.WALKING;
            if (!combat.isHeroAlive()) state = GameState.END;
        } else if (state.equals(GameState.START)) {
            StartScreen startScreen = new StartScreen(0,0);
            startScreen.draw(canvas, camera);
        }
    }
    
    public boolean heroRun(){
    	boolean ran = false;
    	Dice dice = new Dice(2);
    	int result = dice.roll();
    	System.out.println(result);
    	switch(result){
    	case 0:
    		currentLevel.removeMonster(combat.getMonster());
    		state = GameState.WALKING;
    		ran = true;
    		break;
    	case 1:
    		Alert noRun = new Alert(AlertType.INFORMATION);
    		noRun.setContentText("Failed to run away!");
    		noRun.show();
    		ran = false;
    		break;
    	}
    	return ran;
    	
    }

    public void setState(GameState state){
        prevState = state;
        this.state = state;
    }


    public GameState getState() {
        return state;
    }

    public void attack() {
        combat.heroAttack();
    }
    
    public Combat getCombat(){
    	return combat;
    }
}
