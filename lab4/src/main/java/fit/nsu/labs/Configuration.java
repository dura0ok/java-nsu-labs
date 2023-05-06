package fit.nsu.labs;

import io.github.cdimascio.dotenv.Dotenv;

public class Configuration {
    private final Dotenv dotenv;

    public Configuration() {
        dotenv = Dotenv.configure()
                .ignoreIfMissing()
                .load();
        dotenv.entries().forEach(e -> System.setProperty(e.getKey(), e.getValue()));
    }

    public int getPort() {
        return Integer.parseInt(System.getProperty("PORT"));
    }

    public String getServerName() {
        return System.getProperty("HOST");
    }
}
