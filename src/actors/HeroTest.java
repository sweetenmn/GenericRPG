package actors;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;


public class HeroTest {
	Monster monster;
	ArrayList<Integer> numKills = new ArrayList<Integer>();
	ArrayList<Integer> numBoostedKills = new ArrayList<Integer>();
	
	@Test
	public void basicTest() {
		Hero hero = new Hero(HeroType.ROGUE, "Test");
		assertEquals(1, hero.getLevel());
		hero.addExperience(100);
		assertEquals(2, hero.getLevel());
		assertEquals(0 + hero.getType().getIntel(), hero.getActualExp());
		hero.addExperience(10);
		assertEquals(10 + hero.getType().getIntel() *2, hero.getActualExp());
		assertTrue(hero.getExpPercent() == 0.128);
	}
	
	@Test
	public void killCountTest(){
		System.out.println("Test to show the average number of monsters " 
				+"it takes a hero to level up (without boost)");
		//shows when monsters are scaled up to heroes w/ each of their levels
		Hero rogue = new Hero(HeroType.ROGUE, "Test");
		assertEquals(1, rogue.getLevel());
		while (rogue.getLevel() < 20){
			numKills.add(killToLevel(rogue, rogue.getLevel(), 0));
			rogue.incMapLevel();
		}
		System.out.println("Rogue 1-20: " + numKills);
		numKills.clear();
		Hero mage = new Hero(HeroType.MAGE, "MageTest");
		while(mage.getLevel() < 20){
			numKills.add(killToLevel(mage, mage.getLevel(), 0));
			mage.incMapLevel();
		}
		System.out.println("Mage 1-20: " + numKills);
		numKills.clear();
		Hero knight = new Hero(HeroType.KNIGHT, "KnightTest");
		while (knight.getLevel() < 20){
			numKills.add(killToLevel(knight, knight.getLevel(), 0));
			mage.incMapLevel();
		}
		System.out.println("Knight 1-20: " + numKills);
		
	}
	
	
	private int killToLevel(Hero hero, int level, int boost){
		int count = 0;
		while(hero.getLevel() == level){
			Monster monster = new Monster(0);
			hero.addExperience(boost + monster.getType().getExpValue(hero.getMapLevel()));
			count++;
			
		}
		return count;
	}
	
	
	
	
	
	

}
