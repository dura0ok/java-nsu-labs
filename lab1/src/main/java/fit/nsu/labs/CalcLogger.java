package fit.nsu.labs;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

public class CalcLogger {
    public static Logger getLogger(Class<?> InputClass) throws IOException {
        InputStream stream = Main.class.getClassLoader().getResourceAsStream("logging.properties");
        java.util.logging.LogManager.getLogManager().readConfiguration(stream);
        return Logger.getLogger(InputClass.getSimpleName());
    }
}
