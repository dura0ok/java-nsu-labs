package fit.nsu.labs;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
public class Context {


    private final ArrayDeque<Integer> stack;


    private final Map<String, Integer> defines;

    Context(){
        defines = new HashMap<>();
        stack = new ArrayDeque<>();
    }

    public ArrayDeque<Integer> getStack() {
        return stack;
    }

    public Map<String, Integer> getDefines() {
        return defines;
    }





}
