package fit.nsu.labs.commands;

import fit.nsu.labs.exceptions.CalcException;
import fit.nsu.labs.exceptions.ConfigurationException;
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
        try (BufferedReader dataReader = getCommandsReaders()) {
            fillCommandsSources(dataReader);
        } catch (IOException e) {
            throw new FactoryException("IO Errors with file. " + e.getMessage());
        }
    }

    private static BufferedReader getCommandsReaders() {
        return new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResourceAsStream("commands.data")))
        );
    }

    private void fillCommandsSources(BufferedReader dataReader) throws CalcException, IOException {

        for (String line = dataReader.readLine(); line != null; line = dataReader.readLine()) {
            String[] tokens = line.split("@");

            if (tokens.length != 2) {
                throw new FactoryException("Invalid Config");
            }

            String name = tokens[0];
            String commandPath = tokens[1];
            commandsSources.put(name, commandPath);
        }
    }

    // todo: problem
    public AbstractCommand createCommand(String name, String[] args) throws CalcException {
        try {
            var classPath = commandsSources.get(name.toLowerCase());
            if (classPath == null) {
                throw new ConfigurationException("class path " + name + " not found in config");
            }
            Class<?> c = Class.forName(classPath);
            return (AbstractCommand) c.getConstructor(String[].class).newInstance((Object) args);
        } catch (ClassNotFoundException e) {
            throw new FactoryException("Can`t find this command with name " + name + " in available commands");
        } catch (ReflectiveOperationException e) {
            throw new FactoryException("Unknown error when trying to create command. " + e.getMessage());
        }
    }

    // todo: problem
    public void printAvailableCommandsInfo() throws CalcException {
        System.out.println("Available commands:");
        System.out.println("--------------------");
        for (var command : commandsSources.keySet()) {
            System.out.println(command + " " + createCommand(command, null).getCommandDescription());
        }
        System.out.println("--------------------");
    }
}
