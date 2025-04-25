package es.adrianroguez.controller;

import es.adrianroguez.model.GameModel;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class GameController {
    @FXML
    private Canvas gameCanvas;

    private GameModel model;
    private Timeline timeline;
    private Image background;
    private Image foodImage;
    private boolean gameStarted = false;

    /**
     * Metodo para inicializar el juego, configurar el lienzo y el temporizador
     * que actualiza el estado del juego.
     */
    @FXML
    public void initialize() {
        model = new GameModel();

        GraphicsContext gc = gameCanvas.getGraphicsContext2D();

        gameCanvas.setFocusTraversable(true);
        gameCanvas.setOnKeyPressed(this::handleKeyPress);

        background = new Image(getClass().getResourceAsStream("/images/gameBackground.png"));
        foodImage = new Image(getClass().getResourceAsStream("/images/food.png"));

        timeline = new Timeline(new KeyFrame(Duration.millis(175), e -> {
            model.update(); // Actualiza el estado del modelo del juego
            draw(gc); // Dibuja el estado del juego en el lienzo
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);

        draw(gc);
    }

    /**
     * Metodo que maneja la presion de teclas para controlar el juego,
     * incluyendo el inicio del juego y la direccion de la serpiente.
     */
    private void handleKeyPress(KeyEvent event) {
        switch (event.getCode()) {
            case R -> {
                if (!gameStarted) {
                    gameStarted = true;
                    model.resetGame(); // Reinicia el juego si no ha comenzado
                    timeline.play();
                } else if (!model.isRunning()) {
                    model.resetGame(); // Reinicia el juego si está detenido
                }
            }
            case UP -> model.setDirection(GameModel.Direction.UP); // Cambia la direccion hacia arriba
            case DOWN -> model.setDirection(GameModel.Direction.DOWN); // Cambia la direccion hacia abajo
            case LEFT -> model.setDirection(GameModel.Direction.LEFT); // Cambia la direccion hacia la izquierda
            case RIGHT -> model.setDirection(GameModel.Direction.RIGHT); // Cambia la direccion hacia la derecha
        }
    }

    /**
     * Metodo para dibujar el estado del juego en el lienzo,
     * incluyendo el fondo, la serpiente y la comida.
     */
    private void draw(GraphicsContext gc) {
        gc.drawImage(background, 0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());

        Font font = Font.loadFont(getClass().getResourceAsStream("/fonts/PressStart2P-Regular.ttf"), 25);
        Font fontScore = Font.loadFont(getClass().getResourceAsStream("/fonts/PressStart2P-Regular.ttf"), 15);
        if (font == null) {
            System.out.println("No se pudo cargar la fuente. Usando Arial por defecto.");
            font = new Font("Arial", 25);
        }

        if (!gameStarted) {
            String message = "Press R\nto start";
            drawCenteredText(gc, message, font, Color.WHITE);
            return;
        }

        drawScore(gc, fontScore, Color.WHITE);

        var snake = model.getSnake();
        for (int i = 0; i < snake.size(); i++) {
            var point = snake.get(i);
            double x = point.getX() * GameModel.TILE_SIZE;
            double y = point.getY() * GameModel.TILE_SIZE;

            double darkFactor = 1.0 - i * 0.05;
            darkFactor = Math.max(darkFactor, 0.4);
            Color darkerPink = Color.color(1.0, 0.75 * darkFactor, 0.8 * darkFactor);
            gc.setFill(darkerPink);

            if (i == 0 && snake.size() > 1) {
                // Solo la cabeza tendra esquinas redondeadas segun la direccion de conexion
                int dx = (int) (snake.get(1).getX() - point.getX());
                int dy = (int) (snake.get(1).getY() - point.getY());

                double arcTopLeft = 0, arcTopRight = 0, arcBottomLeft = 0, arcBottomRight = 0;

                if (dx == 1) { // conectada a la derecha
                    arcTopLeft = arcBottomLeft = 15;
                } else if (dx == -1) { // conectada a la izquierda
                    arcTopRight = arcBottomRight = 15;
                } else if (dy == 1) { // conectada abajo
                    arcTopLeft = arcTopRight = 15;
                } else if (dy == -1) { // conectada arriba
                    arcBottomLeft = arcBottomRight = 15;
                }

                gc.beginPath();
                gc.moveTo(x + arcTopLeft, y);
                gc.lineTo(x + GameModel.TILE_SIZE - arcTopRight, y);
                gc.quadraticCurveTo(x + GameModel.TILE_SIZE, y, x + GameModel.TILE_SIZE, y + arcTopRight);
                gc.lineTo(x + GameModel.TILE_SIZE, y + GameModel.TILE_SIZE - arcBottomRight);
                gc.quadraticCurveTo(x + GameModel.TILE_SIZE, y + GameModel.TILE_SIZE,
                        x + GameModel.TILE_SIZE - arcBottomRight, y + GameModel.TILE_SIZE);
                gc.lineTo(x + arcBottomLeft, y + GameModel.TILE_SIZE);
                gc.quadraticCurveTo(x, y + GameModel.TILE_SIZE, x, y + GameModel.TILE_SIZE - arcBottomLeft);
                gc.lineTo(x, y + arcTopLeft);
                gc.quadraticCurveTo(x, y, x + arcTopLeft, y);
                gc.closePath();
                gc.fill();
            } else {
                gc.fillRect(x, y, GameModel.TILE_SIZE, GameModel.TILE_SIZE);
            }
        }

        var food = model.getFood();
        double foodSize = GameModel.TILE_SIZE;

        gc.drawImage(foodImage,
                food.getX() * GameModel.TILE_SIZE,
                food.getY() * GameModel.TILE_SIZE,
                foodSize, foodSize);

        if (!model.isRunning()) {
            String message = "Game Over!\nPress R to restart";
            drawCenteredText(gc, message, font, Color.WHITE);
        }
    }

    /**
     * Metodo para dibujar texto centrado en el lienzo.
     * Se usa para mostrar mensajes como "Game Over" o instrucciones.
     */
    private void drawCenteredText(GraphicsContext gc, String message, Font font, Color defaultColor) {
        gc.setFont(font);

        String[] lines = message.split("\n");
        double lineHeight = font.getSize() * 1.2;
        double totalHeight = lines.length * lineHeight;
        double startY = (gameCanvas.getHeight() - totalHeight) / 2 + lineHeight * 0.75;

        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            double y = startY + i * lineHeight;

            if (line.contains("Press R")) {
                int rIndex = line.indexOf("R");
                String beforeR = line.substring(0, rIndex);
                String rLetter = "R";
                String afterR = line.substring(rIndex + 1);

                double x = (gameCanvas.getWidth() - getTextWidth(line, font)) / 2;

                gc.setFill(defaultColor);
                gc.fillText(beforeR, x, y);
                x += getTextWidth(beforeR, font);

                gc.setFill(Color.BLACK);
                gc.fillText(rLetter, x, y);
                x += getTextWidth(rLetter, font);

                gc.setFill(defaultColor);
                gc.fillText(afterR, x, y);
            } else {
                double textWidth = getTextWidth(line, font);
                double x = (gameCanvas.getWidth() - textWidth) / 2;
                gc.setFill(defaultColor);
                gc.fillText(line, x, y);
            }
        }
    }

    /**
     * Metodo para dibujar el puntaje en la esquina superior izquierda.
     */
    private void drawScore(GraphicsContext gc, Font font, Color color) {
        String scoreText = "Score: " + model.getScore();
        gc.setFont(font);
        gc.setFill(color);
        gc.fillText(scoreText, 0, 15);
    }

    /**
     * Metodo para obtener el ancho del texto con una fuente especifica.
     */
    private double getTextWidth(String text, Font font) {
        Text helper = new Text(text);
        helper.setFont(font);
        return helper.getLayoutBounds().getWidth();
    }
}
