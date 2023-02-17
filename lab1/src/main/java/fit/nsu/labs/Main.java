package fit.nsu.labs;

import fit.nsu.labs.exceptions.BadNumberOfArguments;
import fit.nsu.labs.exceptions.CalcException;

import java.nio.file.Files;
import java.nio.file.Paths;


public class Main {
    public static void main(String[] args) {
        try {

            switch (args.length) {
                case 0 -> {
                    CalcExecutor calc = new CalcExecutor(System.in);
                    calc.calculate();
                }

                case 1 -> {
                    try (var input = Files.newInputStream(Paths.get(args[0]))) {
                        CalcExecutor calc = new CalcExecutor(input);
                        calc.calculate();
                    }
                }
                default -> throw new BadNumberOfArguments(
                        "You can add only 1 file to read from or stdin can be used by default"
                );
            }


        } catch (CalcException e) {
            System.err.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}