package fit.nsu.labs;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;

public class Context {


    private final ArrayDeque<Double> stack;


    private final Map<String, Double> defines;

    public Context() {
        defines = new HashMap<>();
        stack = new ArrayDeque<>();
    }

    public Context(ArrayDeque<Double> inputStack, HashMap<String, Double> inputDefines) {
        defines = inputDefines;
        stack = inputStack;
    }

    public ArrayDeque<Double> getStack() {
        return stack;
    }

    public Map<String, Double> getDefines() {
        return defines;
    }


}
