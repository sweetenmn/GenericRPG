package actors;

import static org.junit.Assert.*;

import org.junit.Test;

public class HeroTest {
	//passes without this.sprite = prof.getAvatar();
	@Test
	public void basicTest() {
		Hero hero = new Hero(Profession.WARRIOR, 0, 0);
		assertEquals(1, hero.getLevel());
		hero.addExperience(10);
		assertEquals(1, hero.getLevel());
		System.out.println(hero.getExpPercent());
		hero.addExperience(100);
		assertEquals(2, hero.getLevel());
		assertEquals(10, hero.experience);
		System.out.println(hero.getExpPercent());
		}

}
