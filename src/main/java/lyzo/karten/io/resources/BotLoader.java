package lyzo.karten.io.resources;

import lyzo.karten.model.Bot;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BotLoader {

    public static List<String> getBotList()  {
        try (InputStream is = Objects.requireNonNull(BotLoader.class.getResource("/bots/bots.txt")).openStream()) {

            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            return br.lines().toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Bot loadBot(String name) {
        try (InputStream is = Objects.requireNonNull(BotLoader.class.getResource("/bots/bots.txt")).openStream()) {

            // TODO: LOAD PLAYER DATA AND RETURN BOT OBJECT

            return new Bot(name);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
