package fit.nsu.labs.commands;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;

public class MemoryContext implements Context {


    private final ArrayDeque<Double> stack;


    private final Map<String, Double> defines;

    public MemoryContext() {
        defines = new HashMap<>();
        stack = new ArrayDeque<>();
    }

    public MemoryContext(ArrayDeque<Double> inputStack, HashMap<String, Double> inputDefines) {
        defines = inputDefines;
        stack = inputStack;
    }


    public Double popStack() {
        return stack.pop();
    }

    public int getStackSize() {
        return stack.size();
    }

    public void pushStack(double input) {
        stack.push(input);
    }

    public boolean isStackEmpty() {
        return stack.isEmpty();
    }

    public double peekStack() throws NullPointerException {
        var retValue = stack.peek();
        if (retValue == null) {
            throw new NullPointerException();
        }
        return retValue;
    }


    public void defineNumber(String key, double value) {
        defines.put(key, value);
    }

    public boolean isDefined(String key) {
        return defines.containsKey(key);
    }

    public double getDefinedByKey(String key) {
        if (!defines.containsKey(key)) {
            throw new NullPointerException();
        }
        return defines.get(key);
    }


}
