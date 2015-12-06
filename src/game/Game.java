package game;

import actors.Actor;
import actors.Hero;
import actors.Monster;
import game.graphics.Camera;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import menu.StartScreen;

import java.util.ArrayList;

/**
 * Created by Joseph on 9/20/2015.
 */
public class Game {
    Hero hero;
    ArrayList<Monster> mobs;
    Level currentLevel;
    GameState prevState;
    GameState state;

    Combat combat;

    public void moveHero(Direction dir){
        if(hero.isAlive()){
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
    public void changeLevel(Level level){
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

    public Combat getCombat() {
        return combat;
    }
}
