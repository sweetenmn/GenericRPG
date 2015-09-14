package actors;

/**
 * Created by josephbenton on 9/13/15.
 */
public enum Profession {
    ROGUE(20, 3, 5, 7), WIZARD(15, 6, 10, 10), WARRIOR(30, 10, 1, 2);

    public int getHealth() {
        return health;
    }

    public int getAttack() {
        return attack;
    }

    public int getIntel() {
        return intel;
    }

    public int getLuck() {
        return luck;
    }

    private int health;
    private int attack;
    private int intel;
    private int luck;


    Profession(int health, int attack, int intel, int luck) {
        this.health = health;
        this.attack = attack;
        this.intel = intel;
        this.luck = luck;
    }
}
