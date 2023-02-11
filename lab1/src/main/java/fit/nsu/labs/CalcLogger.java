package fit.nsu.labs;

import fit.nsu.labs.exceptions.CalcException;
import fit.nsu.labs.exceptions.LogConfigNotFound;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

public class CalcLogger {
    public static Logger getLogger(Class<?> InputClass) throws CalcException {
        try{
            InputStream stream = ClassLoader.getSystemResourceAsStream("logging.properties");
            java.util.logging.LogManager.getLogManager().readConfiguration(stream);
            return Logger.getLogger(InputClass.getSimpleName());
        }catch (IOException exception){
               throw new LogConfigNotFound();
        }
    }
}
