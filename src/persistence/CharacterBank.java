package persistence;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import actors.Hero;
import actors.Profession;

public class CharacterBank {
	private String documentName = "src/assets/Characters.txt";
	private static String DELIMITER = "/~RPG~/";
	private ArrayList<Hero> savedHeroes = new ArrayList<Hero>();
	
	public CharacterBank(){
		updateBank();
	}
	
	
	
	public void saveHero(Hero hero){
		FileWriter writer;
		try {
			writer = new FileWriter(documentName, true);
			writer.write("\n");
			writer.write(DELIMITER + hero.getName());
			writer.write(DELIMITER + hero.getProfession());
			writer.write(DELIMITER + hero.getLevel());
			writer.write(DELIMITER + hero.getActualHealth());
			writer.write(DELIMITER + hero.getActualExp());
			writer.close();
		} catch (IOException e) {
			Alert badNum = new Alert(AlertType.ERROR);
			badNum.setContentText("Internal Error - Unable to save Hero.");
			badNum.show();
		}
	}
	
	public boolean heroExists(String name){
		for (Hero h: savedHeroes){
			if (h.getName().equals(name)){
				return true;
			}
		}
		return false;
	}
	
	public Hero getHero(String name){
		for (Hero h: savedHeroes){
			if (h.getName().equals(name)){
				return h;
			}
		}
		return null;
	}
	
	public void updateBank(){
		String[] parts;
		savedHeroes.clear();
    	FileReader reader;
		try {
			reader = new FileReader(documentName);
			BufferedReader bufferedReader = new BufferedReader(reader);
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				parts = line.split(DELIMITER);
				String name = parts[0];
				Profession prof = stringToProfession(parts[1]);
				int level = Integer.valueOf(parts[2]);
				int health = Integer.valueOf(parts[3]);
				int exp = Integer.valueOf(parts[4]);
				Hero hero = new Hero(prof, 0, 0, level);
				hero.setName(name);
				hero.loadExp(exp);
				hero.loadHealth(health);
				savedHeroes.add(hero);
			}
			reader.close();
		} catch (IOException e) {
			Alert badNum = new Alert(AlertType.ERROR);
			badNum.setContentText("Internal error - Unable to load existing Heroes");
			badNum.show();
		}
		
	}
	
	private Profession stringToProfession(String prof){
		if (prof.equals("Profession.MAGE")){
			return Profession.MAGE;
		} else if (prof.equals("Profession.WARRIOR")){
			return Profession.WARRIOR;
		} else {
			return Profession.ROGUE;
		}
	}
	
	
	

}
