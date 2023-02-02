package fit.nsu.labs.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CommandFactory {

    private final Map<String, String> commandsSources = new HashMap<>();

    public CommandFactory() throws IOException {
        BufferedReader dataReader = new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResourceAsStream("commands.data")))
        );

        fillCommandsSources(dataReader);
    }

    private void fillCommandsSources(BufferedReader dataReader) throws IOException {
        for (String line = dataReader.readLine(); line != null; line = dataReader.readLine()) {
            String[] tokens = line.split("@");
            String name = tokens[0];
            String commandPath = tokens[1];
            commandsSources.put(name, commandPath);
        }
    }

    public Command createCommand(String name, String[] args) throws ClassNotFoundException, NoSuchMethodException,
            InvocationTargetException, InstantiationException, IllegalAccessException {
        try {
            Class<?> c = Class.forName(commandsSources.get(name));
            return (Command) c.getConstructor(String[].class).newInstance((Object) args);
        } catch (NullPointerException e) {
            throw new RuntimeException("can`t find class by name " + name);
        }
    }
}
