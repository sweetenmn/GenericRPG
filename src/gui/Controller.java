package gui;


import game.Level;
import game.Position;
import game.graphics.Camera;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

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
    Level currentLevel = new Level(0, 30, 15);
    Camera camera = new Camera(new Position(0, 0));
    Position cameraDragStartPos;
    double startX;
    double startY;

    private AnimationTimer timer = new AnimationTimer() {
        long last = 0;

        @Override
        public void handle(long now) {
            if (now - last > NANO_INTERVAL) {
                currentLevel.draw(canvas, camera);
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
        gc = canvas.getGraphicsContext2D();
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
        currentLevel.addHero(2, 3);
        for (int i = 0; i < 15; i++) {
            currentLevel.addWall(7,i);
        }
        timer.start();
    }

    @FXML
    public void button1() {
        System.out.println("button 1 pressed");
        gc.setFill(Color.BLACK);
    }

    @FXML
    public void button2() {
        System.out.println("button 2 pressed");
        gc.setFill(Color.BLACK);
    }

    @FXML
    public void button3() {
        System.out.println("button 3 pressed");
        gc.setFill(Color.BLACK);
    }

    @FXML
    public void button4() {
        System.out.println("button 4 pressed");
        gc.setFill(Color.BLACK);
    }

    @FXML
    public void up() {

    }

    @FXML
    public void down() {

    }

    @FXML
    public void left() {

    }

    @FXML
    public void right() {

    }

}
