package lyzo.karten.feature.play.minigame.rowing;

import lyzo.karten.utility.interfaces.minigame.GameState;

import java.util.ArrayList;
import java.util.List;

public class RowingGameState implements GameState {

    public final int playerCount;

    public final List<Player> players = new ArrayList<>();

    public RowingGameState(int playerCount) {
        this.playerCount = playerCount;

        for (int i = 0; i < playerCount; i++) {
            players.add(new Player("Player " + (i + 1), 0));
        }
    }

    public class Player {
        public final String name;
        public final int boostChance;

        public int metersRowed = 0;
        public boolean active = false;

        public Player(String name, int boostChance) {
            this.name = name;
            this.boostChance = boostChance;
        }
    }
}
