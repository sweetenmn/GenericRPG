package gui;


import actors.Hero;
import actors.Profession;
import game.Direction;
import game.Game;
import game.Level;
import game.Position;
import game.graphics.Camera;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import javax.swing.*;

public class Controller {
    private long FRAMES_PER_SEC = 60L;
    private long NANO_INTERVAL = 1000000000L / FRAMES_PER_SEC;
    @FXML
    BorderPane pane;
    @FXML
    Canvas canvas;
    @FXML
    Button button;
    @FXML
    ImageView portrait;
    GraphicsContext gc;
    Camera camera = new Camera(new Position(0, 0));
    Position cameraDragStartPos;
    double startX;
    double startY;
    Game game;
    private AnimationTimer timer = new AnimationTimer() {
        long last = 0;

        @Override
        public void handle(long now) {
            if (now - last > NANO_INTERVAL) {
                game.render(canvas, camera);
            }
            last = now;
        }
    };


    @FXML
    public void initialize() {
        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED,
                ev -> {
                    camera.setPosition((int)(cameraDragStartPos.getX() + startX - ev.getX()), cameraDragStartPos.getY() + (int)(startY - ev.getY()));
                });

        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED,
                ev -> {
                    cameraDragStartPos = camera.getPosition();
                    startX = ev.getX();
                    startY = ev.getY();

                });
        pane.addEventHandler(KeyEvent.KEY_TYPED,
                ev -> {
                    String character = ev.getCharacter().toLowerCase();
                    System.out.println(character);
                    if (character.equals("w")) {
                        up();
                    } else if (character.equals("a")) {
                        left();
                    } else if (character.equals("s")) {
                        down();
                    } else if (character.equals("d")) {
                        right();
                    }
                });
        Level currentLevel = new Level(0, 30, 15, new Hero(Profession.ROGUE, 2, 2));
        currentLevel.addExit(5, 5);
        currentLevel.addWall(0, 0);
        currentLevel.addWall(0, 1);
        currentLevel.addWall(0, 2);
        currentLevel.addWall(0, 5);
        currentLevel.addWall(1, 0);
        currentLevel.addWall(3, 0);
        currentLevel.addWall(4, 0);
        currentLevel.addWall(4, 3);
        currentLevel.addWall(4, 2);
        currentLevel.addMonster(6,7);
        game = new Game();
        game.changeLevel(currentLevel);
        gc = canvas.getGraphicsContext2D();
        timer.start();
    }

    @FXML
    public void button1() {
        System.out.println("button 1 pressed");
    }

    @FXML
    public void button2() {
        System.out.println("button 2 pressed");
    }

    @FXML
    public void button3() {
        System.out.println("button 3 pressed");
    }

    @FXML
    public void button4() {
        System.out.println("button 4 pressed");
    }

    @FXML
    public void up() {
        game.moveHero(Direction.UP);
    }

    @FXML
    public void down() {
        game.moveHero(Direction.DOWN);
    }

    @FXML
    public void left() {
        game.moveHero(Direction.LEFT);
    }

    @FXML
    public void right() {
        game.moveHero(Direction.RIGHT);
    }

}
