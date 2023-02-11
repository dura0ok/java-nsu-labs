package fit.nsu.labs.commands;

import fit.nsu.labs.exceptions.CalcException;
import fit.nsu.labs.exceptions.FactoryException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CommandFactory {

    private final Map<String, String> commandsSources = new HashMap<>();

    public CommandFactory() throws CalcException {
        try (BufferedReader dataReader = new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResourceAsStream("commands.data")))
        )) {
            fillCommandsSources(dataReader);
        } catch (IOException e) {
            throw new FactoryException("IO Errors with file. " + e.getMessage());
        }
    }

    private void fillCommandsSources(BufferedReader dataReader) throws CalcException, IOException {

        for (String line = dataReader.readLine(); line != null; line = dataReader.readLine()) {
            if (line.chars().filter(ch -> ch == '@').count() != 1) {
                throw new FactoryException("Invalid Config");
            }
            String[] tokens = line.split("@");
            String name = tokens[0];
            String commandPath = tokens[1];
            commandsSources.put(name, commandPath);
        }
    }

    public Command createCommand(String name, String[] args) throws CalcException {
        try {
            Class<?> c = Class.forName(commandsSources.get(name));
            return (Command) c.getConstructor(String[].class).newInstance((Object) args);
        } catch (ReflectiveOperationException e) {
            throw new FactoryException("Can`t create command. Error: " + e.getMessage());
        } catch (NullPointerException ignored) {
            throw new FactoryException("Can`t find this command: " + name + " in commands config file");
        }
    }
}
