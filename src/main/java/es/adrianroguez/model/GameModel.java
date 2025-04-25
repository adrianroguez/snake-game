package es.adrianroguez.model;

import java.util.LinkedList;
import java.util.Random;
import javafx.geometry.Point2D;

public class GameModel {
    public static final int TILE_SIZE = 20;
    public static final int WIDTH = 30;
    public static final int HEIGHT = 20;

    private LinkedList<Point2D> snake;
    private Point2D food;
    private Direction direction;
    private boolean running = true;
    private int score;

    private Direction nextDirection = null;

    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    public GameModel() {
        resetGame();
    }

    public void resetGame() {
        snake = new LinkedList<>();
        snake.add(new Point2D(WIDTH / 2, HEIGHT / 2));
        direction = Direction.RIGHT;
        spawnFood();
        score = 0;
        running = true;
    }

    public void spawnFood() {
        Random rand = new Random();
        do {
            food = new Point2D(rand.nextInt(WIDTH), rand.nextInt(HEIGHT));
        } while (snake.contains(food));
    }

    public void update() {
        if (!running)
            return;

        if (nextDirection != null) {
            direction = nextDirection;
            nextDirection = null;
        }

        Point2D head = snake.getFirst();
        Point2D newHead = switch (direction) {
            case UP -> head.subtract(0, 1);
            case DOWN -> head.add(0, 1);
            case LEFT -> head.subtract(1, 0);
            case RIGHT -> head.add(1, 0);
        };

        if (newHead.getX() < 0 || newHead.getX() >= WIDTH ||
                newHead.getY() < 0 || newHead.getY() >= HEIGHT ||
                snake.contains(newHead)) {
            running = false;
            return;
        }

        snake.addFirst(newHead);

        if (newHead.equals(food)) {
            score++;
            spawnFood();
        } else {
            snake.removeLast();
        }
    }

    public LinkedList<Point2D> getSnake() {
        return snake;
    }

    public Point2D getFood() {
        return food;
    }

    public int getScore() {
        return score;
    }

    public void setDirection(Direction dir) {
        if ((dir == Direction.UP && direction != Direction.DOWN) ||
                (dir == Direction.DOWN && direction != Direction.UP) ||
                (dir == Direction.LEFT && direction != Direction.RIGHT) ||
                (dir == Direction.RIGHT && direction != Direction.LEFT)) {
            nextDirection = dir;
        }
    }

    public boolean isRunning() {
        return running;
    }
}
