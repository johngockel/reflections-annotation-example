package de.johngockel.examples.annotation.processor;

public class CommandConfiguration {

    private String processorName;

    public CommandConfiguration(String processorName) {
        this.processorName = processorName;
    }

    public void setProcessorName(String processorName) {
        this.processorName = processorName;
    }

    public String getProcessorName() {
        return processorName;
    }
}
