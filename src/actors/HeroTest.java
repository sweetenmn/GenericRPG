package actors;

import static org.junit.Assert.*;

import org.junit.Test;

public class HeroTest {
	//passes without this.sprite = prof.getAvatar();
	@Test
	public void test() {
		Hero hero = new Hero(Profession.WARRIOR, 0, 0);
		assertEquals(1, hero.getLevel());
		hero.addExperience(10);
		assertEquals(1, hero.getLevel());
		hero.addExperience(100);
		assertEquals(2, hero.getLevel());
		assertEquals(15, hero.experience);
		}

}
