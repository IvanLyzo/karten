package lyzo.karten.io.resources;

import lyzo.karten.model.Bot;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

public class BotLoader {

    public final static Bot BROK = loadBot("BROK");

    public static Bot loadBot(String name) {
        try {
            List<String> lines = Files.readAllLines(Path.of(Objects.requireNonNull(BotLoader.class.getResource("/bots/" + name + ".txt")).getPath()));

            // TODO: LOAD PLAYER DATA AND RETURN BOT OBJECT

            return new Bot(name);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
