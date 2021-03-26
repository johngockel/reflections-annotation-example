package de.johngockel.examples.annotation;

import de.johngockel.examples.annotation.context.Command;
import de.johngockel.examples.annotation.processor.CommandAdapter;
import de.johngockel.examples.annotation.processor.CommandConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Command(name = "SampleProcessor", configurationClass = CommandConfiguration.class)
public class SampleCommand extends CommandAdapter {

    private static final Logger log = LoggerFactory.getLogger(SampleCommand.class);

    public SampleCommand(CommandConfiguration configuration) {
        super(configuration);
    }

    @Override
    public void run() {
        log.info("SampleProcessor invoked.");
    }
}
