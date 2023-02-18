package fit.nsu.labs.commands;

public interface Context {
    double popStack();

    int getStackSize();

    void pushStack(double input);

    double peekStack() throws NullPointerException;


    void defineNumber(String key, double value);

    boolean isDefined(String key);

    double getDefinedByKey(String key) throws NullPointerException;
}
