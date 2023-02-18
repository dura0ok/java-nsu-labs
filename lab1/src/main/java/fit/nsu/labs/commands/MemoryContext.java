package fit.nsu.labs.commands;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class MemoryContext implements Context {


    private final Deque<Double> stack;


    private final Map<String, Double> defines;

    public MemoryContext() {
        defines = new HashMap<>();
        stack = new ArrayDeque<>();
    }

    public MemoryContext(ArrayDeque<Double> inputStack, HashMap<String, Double> inputDefines) {
        defines = inputDefines;
        stack = inputStack;
    }


    public double popStack() {
        return stack.pop();
    }

    public int getStackSize() {
        return stack.size();
    }

    public void pushStack(double input) {
        stack.push(input);
    }

    public double peekStack() throws NullPointerException {
        var retValue = stack.peek();
        if (retValue == null) {
            throw new NullPointerException("Stack empty");
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
            throw new NullPointerException("can`t find defined value");
        }
        return defines.get(key);
    }


}
