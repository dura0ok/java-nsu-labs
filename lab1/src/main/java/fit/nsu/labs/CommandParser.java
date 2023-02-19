package fit.nsu.labs;

import fit.nsu.labs.commands.Command;
import fit.nsu.labs.commands.CommandFactory;
import fit.nsu.labs.exceptions.CalcException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;


public class CommandParser {
    // todo: problem
    BufferedReader in;

    public CommandParser(InputStream input) {
        in = new BufferedReader(new InputStreamReader(input));
    }

    // todo: problem
    public ArrayList<Command> parseCommands() throws CalcException, IOException {
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
