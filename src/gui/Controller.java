package gui;


import actors.Hero;
import actors.Monster;
import actors.Profession;
import game.*;
import game.graphics.Camera;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import java.io.File;


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
    @FXML
    ProgressBar healthBar;
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
                healthBar.setProgress(game.getHeroHealthPercent());
                game.render(canvas, camera);
                game.checkStates(canvas);
            }
            last = now;
        }
    };


    @FXML
    public void initialize() {
        healthBar.setProgress(1.0);
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
        pane.addEventHandler(KeyEvent.KEY_RELEASED,
                ev -> {
                    String code = ev.getCode().getName();
                    System.out.println(code);
                    if (code.equals("W") || code.equals("Up")) {
                        up();
                    } else if (code.equals("A") || code.equals("Left")) {
                        left();
                    } else if (code.equals("S") || code.equals("Down")) {
                        down();
                    } else if (code.equals("D") || code.equals("Right")) {
                        right();
                    }
                });
        Level currentLevel = new Level(new Hero(Profession.ROGUE, 2, 2), "src/assets/Levels/L1.txt");
        game = new Game();
        game.changeLevel(currentLevel);
        game.setState(GameState.WALKING);
        gc = canvas.getGraphicsContext2D();
        timer.start();
    }


    @FXML
    public void button1() {
        game.heroAtk();

    }

    @FXML
    public void button2() {
        game.step();

    }

    @FXML
    public void button3() {

    }

    @FXML
    public void button4() {

    }

    @FXML
    public void up() {
        if (game.getState().equals(GameState.WALKING)) {
            game.moveHero(Direction.UP);
        }
    }

    @FXML
    public void down() {
        if (game.getState().equals(GameState.WALKING)) {
            game.moveHero(Direction.DOWN);
        }    }

    @FXML
    public void left() {
        if (game.getState().equals(GameState.WALKING)) {
            game.moveHero(Direction.LEFT);
        }    }

    @FXML
    public void right() {
        if (
                game.getState().equals(GameState.WALKING)) {
            game.moveHero(Direction.RIGHT);
        }    }

}
