package fit.nsu.labs;

import fit.nsu.labs.exceptions.CalcException;
import fit.nsu.labs.exceptions.ConfigurationException;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

import static java.util.logging.LogManager.getLogManager;

// todo: problem
// fixed as add abstract
public abstract class CalcLogger {
    public static Logger getLogger(Class<?> InputClass) throws CalcException {
        try {
            try (InputStream stream = ClassLoader.getSystemResourceAsStream("logging.properties")) {
                getLogManager().readConfiguration(stream);
                return Logger.getLogger(InputClass.getSimpleName());
            }
        } catch (IOException exception) {
            // todo: problem: the original exception and its stack trace is lost
            // Fixed as: print stack trace and create error with get message
            exception.printStackTrace();
            throw new ConfigurationException("can`t open/find log file. Detailed: " + exception.getMessage());
        }
    }
}
