package fit.nsu.labs.commands;

public interface Context {
    public Double popStack();

    public int getStackSize();

    public void pushStack(double input);

    public boolean isStackEmpty();

    public double peekStack() throws NullPointerException;


    public void defineNumber(String key, double value);

    public boolean isDefined(String key);

    public double getDefinedByKey(String key);
}
