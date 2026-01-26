package lyzo.karten.feature.play.minigame.rowing;

import javafx.geometry.Point2D;
import lyzo.karten.utility.Bounds2D;
import lyzo.karten.utility.structures.minigame.GameState;

import java.util.*;

public class RowingGameState extends GameState {

    public final int courseLength;
    public final int playerCount;

    public final Player user;
    public final List<Player> players = new ArrayList<>();

    public RowingGameState(int courseLength, int playerCount) {
        // save key data at initialization
        this.courseLength = courseLength;
        this.playerCount = playerCount;

        // init user player
        user = new Player(0, "User player", 55, 110);
        players.add(user);

        initPlayerData();

        overlayOn.addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                overlayCountdown.set(FLASHCARD_COUNTDOWN);
            }
        });

        overlayCountdown.addListener((observable, oldValue, newValue) -> {
            if (newValue.doubleValue() <= 0) {
                overlayOn.set(true);
            }
        });
    }

    private void initPlayerData() {
        // init players
        for (int i = 1; i < playerCount; i++) {
            players.add(new Player(i, "Player " + i, 80, 0));
        }
    }

    public void initPlayerPositions(Point2D... pos) {
        if (pos.length != playerCount) {
            throw new RuntimeException("incorrect number of initial positions (" + pos.length + ") given for player count (" + playerCount + ").");
        }

        for (int i = 0; i < pos.length; i++) {
            players.get(i).bounds.setX(pos[i].getX());
            players.get(i).bounds.setY(pos[i].getY());
        }
    }

    public static class Player {
        // player constants
        public static final double WIDTH = 100f;
        public static final double HEIGHT = 200f;

        public final int id;
        public final String name;

        public final int speed;
        public final double boostStrength;

        public double metersRowed = 0;

        public double boostEffect = 0;
        public double timeSinceBoost = 0;

        public Bounds2D bounds = new Bounds2D(0, 0, WIDTH, HEIGHT);

        public Player(int id, String name, int speed, int boostStrength) {
            this.id = id;
            this.name = name;

            this.speed = speed;
            this.boostStrength = boostStrength;
        }

        public void move(double dy) {
            bounds.setY(bounds.getY() + dy);
            metersRowed += Math.abs(dy);
        }
    }
}
