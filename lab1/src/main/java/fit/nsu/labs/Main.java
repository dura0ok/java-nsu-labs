package fit.nsu.labs;

import fit.nsu.labs.exceptions.CalcException;

import java.nio.file.Files;
import java.nio.file.Paths;


public class Main {
    public static void main(String[] args) {
        try {
            if (args.length == 0) {
                CalcEvaluator calc = new CalcEvaluator(System.in);
                calc.calculate();
                return;
            }

            if (args.length == 1) {
                try (var input = Files.newInputStream(Paths.get(args[0]))) {
                    CalcEvaluator calc = new CalcEvaluator(input);
                    calc.calculate();
                }

                return;
            }

            throw new RuntimeException(
                    "you can add only 1 file to read from or you can not choose file and use stdin"
            );

        } catch (CalcException e) {
            System.err.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}