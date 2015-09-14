package terrain;

import game.GameObject;
import game.Position;

/**
 * Created by josephbenton on 9/13/15.
 */
public class Level extends GameObject {
    int Depth;
    int height;
    int width;
    boolean[][] wallMap;

    public Level(int depth, int height, int width) {
        Depth = depth;
        this.height = height;
        this.width = width;
        wallMap = new boolean[width][height];
    }

    public void addRoom(Position p, int height, int width) {

    }

    private void addWall(Position p) {

    }

    private void removeWall(Position p) {

    }

    public void addExit(Position p) {

    }

    public boolean isClear(Position p) {
        return wallMap[p.getX()][p.getY()];
    }

    @Override
    public void draw() {

    }
}
