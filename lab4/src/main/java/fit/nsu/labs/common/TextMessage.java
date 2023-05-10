package fit.nsu.labs.common;

public record TextMessage(String name, String text) {
    @Override
    public String toString() {
        return name + ": " + text;
    }
}
