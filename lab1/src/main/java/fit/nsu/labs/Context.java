package fit.nsu.labs;

import fit.nsu.labs.exceptions.NotEnoughtArgumentsInStack;

import java.util.ArrayDeque;
import java.util.EmptyStackException;
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


    public Double popStack(){
        return stack.pop();
    }

    public int getStackSize(){
        return stack.size();
    }

    public void pushStack(double input){
        stack.push(input);
    }

    public boolean isStackEmpty(){
        return stack.isEmpty();
    }

    public double peekStack() throws NullPointerException{
        var retValue = stack.peek();
        if(retValue == null){
            throw new NullPointerException();
        }
        return retValue;
    }


    public void defineNumber(String key, double value){
        defines.put(key, value);
    }

    public boolean isDefined(String key){
        return defines.containsKey(key);
    }

    public double getDefinedByKey(String key){
        return defines.get(key);
    }


}
