package actors;

import javafx.scene.image.Image;

/**
 * Created by josephbenton on 9/13/15.
 */
public enum Profession {
    ROGUE(20, 3, 5, 7) {
        @Override
        public Image getAvatar() {
            return new Image("assets/warrior.png");
        }
    },
    WIZARD(15, 6, 10, 10) {
        @Override
        public Image getAvatar() {
            return new Image("assets/warrior.png");
        }
    },
    WARRIOR(30, 10, 1, 2) {
        @Override
        public Image getAvatar() {
            return new Image("assets/warrior.png");
        }
    };

    private int health;
    private int attack;
    private int intel;
    private int luck;


    public abstract Image getAvatar();


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

    Profession(int health, int attack, int intel, int luck) {
        this.health = health;
        this.attack = attack;
        this.intel = intel;
        this.luck = luck;
    }
}
