package lyzo.karten.feature.play.minigame.rowing;

import javafx.geometry.Point2D;
import lyzo.karten.utility.Bounds2D;
import lyzo.karten.utility.interfaces.minigame.GameState;

import java.util.*;

public class RowingGameState implements GameState {

    public final int courseLength;
    public final int playerCount;

    public final Player user;
    public final List<Player> players = new ArrayList<>();

    public RowingGameState(int courseLength, int playerCount) {
        // save key data at initialization
        this.courseLength = courseLength;
        this.playerCount = playerCount;

        // init user player
        user = new Player(0, "User player", 10, 0);
        players.add(user);

        // init players
        for (int i = 1; i < playerCount; i++) {
            players.add(new Player(i, "Player " + i, 100, 0));
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
        public final int boostChance;

        public double metersRowed = 0;
        public boolean active = false;

        public Bounds2D bounds = new Bounds2D(0, 0, WIDTH, HEIGHT);

        public Player(int id, String name, int speed, int boostChance) {
            this.id = id;
            this.name = name;

            this.speed = speed;
            this.boostChance = boostChance;
        }

        public void move(double dy) {
            bounds.setY(bounds.getY() + dy);
            metersRowed += Math.abs(dy);
        }
    }
}
