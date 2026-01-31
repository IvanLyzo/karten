package lyzo.karten.utility.structures.minigame;

import javafx.animation.AnimationTimer;
import lyzo.karten.utility.structures.Controller;

public abstract class MinigameController implements Controller {

    private long lastTime = 0;

    public abstract void initGame();

    public abstract void updateGame(double delta);
    public abstract void submitResponse(String s);

    public abstract void drawGame();

    public AnimationTimer createLoop() {
        return new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (lastTime == 0) {
                    lastTime = now;
                    return;
                }

                double delta = (now - lastTime) / 1_000_000_000.0;

                updateGame(delta);
                drawGame();

                lastTime = now;
            }
        };
    }
}