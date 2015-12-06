package persistence;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import actors.Hero;
import actors.Profession;

public class CharacterBank {
	private String documentName = "src/assets/Characters.txt";
	private static String DELIMITER = "/~RPG~/";
	private ArrayList<Hero> savedHeroes = new ArrayList<Hero>();
	ObservableList<String> heroNames = FXCollections.observableArrayList(); 
	
	public CharacterBank(){
		updateBank();
	}	
	
	public void saveHero(Hero hero){
		try {
			if (heroExists(hero.getName())){
					clearDoc();
					overwriteHero(hero);
					for (Hero h: savedHeroes){
						writeHero(h);
					}
			} else {
				writeHero(hero);
				updateBank();
			}
		} catch (IOException e) {
			
			e.printStackTrace();
		}

	}
	
	private void writeHero(Hero hero){
		FileWriter writer;
		try {
			writer = new FileWriter(documentName, true);
			writer.write(DELIMITER + hero.getName());
			writer.write(DELIMITER + hero.getProfession());
			writer.write(DELIMITER + hero.getLevel());
			writer.write(DELIMITER + hero.getActualHealth());
			writer.write(DELIMITER + hero.getActualExp());
			writer.write("\n");
			writer.close();
		} catch (IOException e) {
			Alert badNum = new Alert(AlertType.ERROR);
			badNum.setContentText("Internal Error - Unable to save Hero.");
			badNum.show();
		}
		
	}
	
	private void overwriteHero(Hero hero){
		savedHeroes.remove(getIndexOf(hero.getName()));
		savedHeroes.add(hero);
	}
	
	private void clearDoc() throws IOException{
		FileWriter writer = new FileWriter(documentName);
		writer.close();
	}
	
	private int getIndexOf(String name){
		int where = 0;
		for (Hero h: savedHeroes){
			if (h.getName().equals(name)){
				where = savedHeroes.indexOf(h);
			}
		}
		return where;
		
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
		heroNames.clear();
    	FileReader reader;
		try {
			reader = new FileReader(documentName);
			BufferedReader bufferedReader = new BufferedReader(reader);
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				parts = line.split(DELIMITER);
				String name = parts[1];
				Profession prof = stringToProfession(parts[2]);
				int level = Integer.valueOf(parts[3]);
				int health = Integer.valueOf(parts[4]);
				int exp = Integer.valueOf(parts[5]);
				Hero hero = new Hero(prof, name, level);
				hero.loadExp(exp);
				hero.loadHealth(health);
				savedHeroes.add(hero);
				heroNames.add(name);
			}
			reader.close();
		} catch (IOException e) {
			Alert internalErr = new Alert(AlertType.ERROR);
			internalErr.setContentText("Internal error - Unable to load existing Heroes");
			internalErr.show();

		}
		
	}
	
	private Profession stringToProfession(String prof){
		if (prof.equals("MAGE")){
			return Profession.MAGE;
		} else if (prof.equals("KNIGHT")){
			return Profession.KNIGHT;
		} else {
			return Profession.ROGUE;
		}
	}
	
	public ObservableList<String> getSavedNames(){
		return heroNames;
	}
	
	
	

}
