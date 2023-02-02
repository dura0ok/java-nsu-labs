package fit.nsu.labs;

import fit.nsu.labs.commands.Command;
import fit.nsu.labs.commands.CommandFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;


public class CommandParser {
    BufferedReader in;

    public CommandParser(InputStream input) {
        in = new BufferedReader(new InputStreamReader(input));

    }

    public ArrayList<Command> parseCommands() throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        var factory = new CommandFactory();
        var commands = new ArrayList<Command>();

        for (String line = in.readLine(); line != null; line = in.readLine()) {
            line = line.trim();
            if (line.startsWith("#") || line.isEmpty()) {
                continue;
            }
            String[] tokens = line.split("\\s+");
            String[] args = Arrays.copyOfRange(tokens, 1, tokens.length);
            commands.add(factory.createCommand(tokens[0], args));

        }

        return commands;
    }
}
