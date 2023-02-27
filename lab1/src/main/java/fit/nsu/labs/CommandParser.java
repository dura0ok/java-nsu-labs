package fit.nsu.labs;

import fit.nsu.labs.commands.Command;
import fit.nsu.labs.commands.CommandFactory;
import fit.nsu.labs.exceptions.CalcException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;


public class CommandParser {
    private final BufferedReader in;
    private final boolean isCommandLineInput;

    public CommandParser(InputStream input) {
        in = new BufferedReader(new InputStreamReader(input));
        // todo: super unreliable way to detect interactive mode.
        isCommandLineInput = input.equals(System.in);
    }

    public Command parseCommand() throws CalcException {
        try {
            // todo: problem
            var factory = new CommandFactory();
            String line;
            do {
                line = in.readLine();
                if (line == null || (line.equalsIgnoreCase("exit") && isCommandLineInput)) {
                    return null;
                }

                line = line.strip();

            } while (line.startsWith("#") || line.isEmpty());


            String[] tokens = line.split("\\s+");
            String[] args = Arrays.copyOfRange(tokens, 1, tokens.length);
            return factory.createCommand(tokens[0], args);
        } catch (IOException e) {
            // todo: problem
            e.printStackTrace();
            throw new CalcException("Error when parse commands ", e);
        }
    }
}
