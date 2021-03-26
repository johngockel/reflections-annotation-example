package de.johngockel.examples.annotation;


import de.johngockel.examples.annotation.processor.Command;
import de.johngockel.examples.annotation.processor.CommandConfiguration;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

public class ImportRunner {

    private static final Logger log = LoggerFactory.getLogger(ImportRunner.class);

    private final Collection<Command> commands;

    public static void run() {
        Reflections reflections = new Reflections("de.johngockel.examples", new TypeAnnotationsScanner(), new SubTypesScanner());
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(de.johngockel.examples.annotation.context.Command.class);

        ImportRunner importRunner = new ImportRunner();
        for (Class<?> clazz: classes) {
            if(!Command.class.isAssignableFrom(clazz)) {
                throw new RuntimeException("Classes that annotated with @ImportProcessor muss implement the interface Processor. (" + clazz.getName() + ")");
            }

            de.johngockel.examples.annotation.context.Command annotation = clazz.getAnnotation(de.johngockel.examples.annotation.context.Command.class);
            String processorName = annotation.name();
            Class<?> configurationClass = annotation.configurationClass();
            if(!CommandConfiguration.class.isAssignableFrom(configurationClass)) {
                throw new RuntimeException("ConfigurationClasses must implements ProcessorConfiguration");
            }

            try {
                Constructor<?> constructorConf = configurationClass.getConstructor(String.class);
                CommandConfiguration configuration = (CommandConfiguration) constructorConf.newInstance(processorName);

                Constructor<?> constructor = clazz.getConstructor(configurationClass);
                Command command = (Command) constructor.newInstance(configuration);
                importRunner.addProcessor(command);
            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                log.error("Error while creating instance of {}.", clazz.getName());
            }

            for(Command command : importRunner.getProcessors()) {
                log.info(command.toString());
                command.run();
            }
        }
    }

    private ImportRunner() {
        commands = new LinkedHashSet<>();
    }

    public Collection<Command> getProcessors() {
        return commands;
    }

    public void addProcessor(Command command) {
        commands.add(command);
    }


    public static void main(String... args) {
        ImportRunner.run();
    }
}
