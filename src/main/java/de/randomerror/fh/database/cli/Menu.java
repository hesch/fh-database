package de.randomerror.fh.database.cli;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * Created by Henri on 02.01.17.
 */
public class Menu {
    private List<Option> menuOptions = new LinkedList<>();

    public void registerOption(String key, String description, Runnable listener) {
        if(menuOptions.stream().anyMatch(option -> option.key.equals(key)))
            throw new IllegalArgumentException("Key " + key + " is duplicate");
        menuOptions.add(new Option(key, description, listener));
    }

    public void display() {
        menuOptions.forEach(option -> CLI.out.format("(%s) %s", option.key, option.description));
    }

    public void waitForSelection() {
        Optional<Option> opt = null;
        do {
            String input = CLI.in.next();
            opt = menuOptions.stream().filter(o -> o.key.equals(input)).findFirst();
        } while(opt.isPresent());

        opt.get().delegate.run();
    }

    private class Option {
        public String key;
        public String description;
        public Runnable delegate;

        public Option(String key, String description, Runnable delegate) {
            this.key = key;
            this.description = description;
            this.delegate = delegate;
        }
    }
}
