package de.randomerror.fh.database.cli;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

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
        menuOptions.forEach(option -> CLI.out.format("(%s) %s\n", option.key, option.description));
        CLI.out.flush();
    }

    public void show() {
        Optional<Option> opt;
        do {
            display();
            String input = CLI.in.next();
            opt = menuOptions.stream().filter(o -> o.key.equals(input)).findFirst();
        } while(!opt.isPresent());

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
