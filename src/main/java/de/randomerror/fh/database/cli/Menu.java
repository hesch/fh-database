package de.randomerror.fh.database.cli;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created by Henri on 02.01.17.
 */
public class Menu {
    private List<Option> menuOptions = new LinkedList<>();

    public void registerOption(String key, String description, Consumer<String> listener) {
        if(menuOptions.stream().anyMatch(option -> option.key.equals(key)))
            throw new IllegalArgumentException("Key " + key + " is duplicate");
        menuOptions.add(new Option(key, description, listener));
    }

    public void display() {
        menuOptions.forEach(option -> CLI.out.format("(%s) %s\n", option.key, option.description));
        CLI.out.flush();
    }

    public void show() {
        Optional<Option> opt = null;
        do {
            display();
            String input = CLI.in.next();
            opt = menuOptions.stream().filter(o -> o.key.equals(input)).findFirst();
        } while(!opt.isPresent());

        opt.get().delegate.accept(opt.get().key);
    }

    private class Option {
        public String key;
        public String description;
        public Consumer<String> delegate;

        public Option(String key, String description, Consumer<String> delegate) {
            this.key = key;
            this.description = description;
            this.delegate = delegate;
        }
    }
}
