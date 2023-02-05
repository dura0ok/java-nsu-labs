package fit.nsu.labs;

import fit.nsu.labs.exceptions.CalcException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        try {
            InputStream input = selectInputStreamFromArgs(args);
            CalcEvaluator calc = new CalcEvaluator(input);
            calc.calculate();
        } catch (CalcException e) {
            System.err.println(e.getMessage());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static InputStream selectInputStreamFromArgs(String[] args) throws IOException, RuntimeException {
        InputStream input = System.in;

        if (args.length > 1) {
            throw new RuntimeException(
                    "you can add only 1 file to read from or you can not choose file and use stdin"
            );
        }

        if (args.length == 1) {
            input = Files.newInputStream(Paths.get(args[0]));
        }
        return input;
    }
}