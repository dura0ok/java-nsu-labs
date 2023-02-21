package fit.nsu.labs;

import fit.nsu.labs.exceptions.CalcException;
import fit.nsu.labs.exceptions.ConfigurationException;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

// todo: problem
public class CalcLogger {
    public static Logger getLogger(Class<?> InputClass) throws CalcException {
        try {
            try (InputStream stream = ClassLoader.getSystemResourceAsStream("logging.properties")) {
                java.util.logging.LogManager.getLogManager().readConfiguration(stream);
                return Logger.getLogger(InputClass.getSimpleName());
            }
        } catch (IOException exception) {
            // todo: problem: the original exception and its stack trace is lost
            exception.printStackTrace();
            throw new ConfigurationException("can`t open/find log file");
        }
    }
}
