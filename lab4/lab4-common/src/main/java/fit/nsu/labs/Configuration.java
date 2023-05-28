package fit.nsu.labs;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvException;

import java.util.Objects;

public class Configuration {
    public Configuration() {
        try {
            Dotenv.configure().systemProperties().load();
        } catch (DotenvException ignored) {
            Dotenv.configure().directory(Objects.requireNonNull(getClass().getResource("/")).getPath()).systemProperties().load();
        }
    }

    public int getPort() {
        return Integer.parseInt(System.getProperty("PORT"));
    }

    public String getServerName() {
        return System.getProperty("HOST");
    }

    public Protocol getProtocol() throws IllegalArgumentException {
        try {
            return Protocol.valueOf(System.getProperty("PROTOCOL").toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("invalid protocol '" + System.getProperty("PROTOCOL") + "'");
        }
    }

    public int getCountMessages() {
        return Integer.parseInt(System.getProperty("COUNT_MSG", "10"));
    }

    public enum Protocol {
        XML,
        SERIALIZATION
    }
}
