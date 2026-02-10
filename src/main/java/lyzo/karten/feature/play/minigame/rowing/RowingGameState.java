package lyzo.karten.feature.play.minigame.rowing;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import lyzo.karten.feature.play.MinigameResultViewBuilder;
import lyzo.karten.utility.Bounds2D;
import lyzo.karten.io.resources.GraphicLoader;
import lyzo.karten.utility.structures.minigame.GameState;

import java.util.*;

public class RowingGameState extends GameState {

    public final int courseLength;
    public final int playerCount;

    public final List<GameObject> details = new ArrayList<>();

    public final Player user;
    public final List<Player> players = new ArrayList<>();

    public int correctAnswers = 0;

    public RowingGameState(String username, int courseLength, int playerCount) {
        // save key data at initialization
        this.courseLength = courseLength;
        this.playerCount = playerCount;

        // init game objects
        GameObject finishLine = new GameObject(GraphicLoader.FINISH_LINE_GRAPHIC, 1480, 1184);
        details.add(finishLine);

        // init players
        user = new Player(0, username, GraphicLoader.RED_BOAT_GRAPHIC, 55, 110);
        players.add(user);

        Random r = new Random();
        for (int i = 1; i < playerCount; i++) {
            players.add(new Player(i, "Player " + i, GraphicLoader.GREEN_BOAT_GRAPHIC, r.nextInt(75, 86), 0));
        }

        // set up overlay system listeners
        overlayOn.addListener((_, _, newValue) -> {
            if (!newValue) {
                overlayCountdown.set(FLASHCARD_COUNTDOWN);
            }
        });
        overlayCountdown.addListener((_, _, newValue) -> {
            if (newValue.doubleValue() <= 0) {
                overlayOn.set(true);
            }
        });
    }

    public void initPositions(List<? extends GameObject> objs, Point2D... pos) {
        if (pos.length != objs.size()) {
            throw new RuntimeException("incorrect number of initial positions (" + pos.length + ") given for player count (" + playerCount + ").");
        }

        for (int i = 0; i < pos.length; i++) {
            objs.get(i).bounds.setX(pos[i].getX());
            objs.get(i).bounds.setY(pos[i].getY());
        }
    }

    public MinigameResultViewBuilder.MinigameResult getGameResult() {
        List<Map.Entry<String, Integer>> leaderboard = new ArrayList<>();

        for (Player player : players) {
            leaderboard.add(new AbstractMap.SimpleEntry<>(player.name, (int) Math.floor(player.metersRowed)));
        }

        Comparator<Map.Entry<String, Integer>> comparator = Map.Entry.comparingByValue();
        leaderboard.sort(comparator.reversed());

        return new MinigameResultViewBuilder.MinigameResult(leaderboard, user,
                correctAnswers, correctAnswers * 10);
    }

    public static class GameObject {

        public final Image graphic;
        public Bounds2D bounds;

        public GameObject(Image graphic, int width, int height) {
            this.graphic = graphic;
            bounds = new Bounds2D(0, 0, width, height);
        }

        protected void move(double dy) {
            bounds.setY(bounds.getY() + dy);
        }

        public void draw(GraphicsContext gc) {
            gc.drawImage(graphic, bounds.getX(), bounds.getY(), bounds.getW(), bounds.getH());
        }
    }

    public static class Player extends GameObject {

        public static final int WIDTH = 100;
        public static final int HEIGHT = 200;

        public final int id;
        public final String name;

        public final int speed;
        public final double boostStrength;

        public double metersRowed = 0;

        public double boostEffect = 0;
        public double timeSinceBoost = 0;

        public Player(int id, String name, Image graphic, int speed, int boostStrength) {
            super(graphic, WIDTH, HEIGHT);

            this.id = id;
            this.name = name;

            this.speed = speed;
            this.boostStrength = boostStrength;
        }

        public void move(double dy, double playerOffset) {
            super.move(dy - playerOffset);
            metersRowed += Math.abs(dy);
        }

        public void activateBoost() {
            boostEffect = boostStrength;
            timeSinceBoost = 0;
        }
    }
}
