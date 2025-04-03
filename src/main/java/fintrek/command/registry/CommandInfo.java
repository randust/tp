package fintrek.command.registry;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used to provide metadata for commands.
 *
 * <p>This annotation can be applied to classes that extend {@link fintrek.command.Command}
 * to supply a human-readable description for help messages, documentation, or command discovery.</p>
 *
 * <p>The description is typically shown in command listings or user guides
 * to explain the expected usage format and behavior of the command.</p>
 *
 * <p>This annotation is retained at runtime and targets types (i.e., classes).</p>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CommandInfo {
    String recurringFormat();
    String regularFormat();
    /**
     * A brief description or usage guide for the command.
     *
     * @return the description string
     */
    String description();
}
