package ru.job4j.tracker;

public class StubAction implements UserAction {
    private boolean call = false;

    @Override
    public String name() {
        return "Stub action";
    }

    @Override
    public boolean execute(final Input input, final Store memTracker) {
        call = true;
        return false;
    }

    public boolean isCall() {
        return call;
    }
}
