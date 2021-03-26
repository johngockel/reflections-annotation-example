package de.johngockel.examples.annotation.processor;

public abstract class CommandAdapter implements Command {

    private String name;

    protected CommandAdapter(CommandConfiguration configuration) {
        name = configuration.getProcessorName();
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
