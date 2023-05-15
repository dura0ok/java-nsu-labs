package fit.nsu.labs;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvException;

import java.util.Objects;

public class Configuration {
    public Configuration() {
        try{
            Dotenv.configure().systemProperties().load();
        }catch(DotenvException ignored){
            Dotenv.configure().directory(Objects.requireNonNull(getClass().getResource("/")).getPath()).systemProperties().load();
        }
    }

    public int getPort() {
        return Integer.parseInt(System.getProperty("PORT"));
    }

    public String getServerName() {
        return System.getProperty("HOST");
    }
}
