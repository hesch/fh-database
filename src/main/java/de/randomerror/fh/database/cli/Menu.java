package de.randomerror.fh.database.cli;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Henri on 02.01.17.
 */
public class Menu {
    private List<Option> menuOptions = new LinkedList<>();

    /**
     * Add an option to the selection menu
     * @param key The key used for matching the user input
     * @param description The description shown to the user
     * @param listener The callback function for when this option was selected
     */
    public void registerOption(String key, String description, Runnable listener) {
        if(menuOptions.stream().anyMatch(option -> option.key.equals(key))) // check if the key already exist
            throw new IllegalArgumentException("Key " + key + " is duplicate"); // if so throw an ecxetion
        menuOptions.add(new Option(key, description, listener)); // otherwise register it as a new option
    }

    /**
     * Prints the options to the console
     */
    public void display() {
        // for each registered option print it on the console
        menuOptions.forEach(option -> CLI.out.format("(%s) %s\n", option.key, option.description));
        CLI.out.flush();
    }

    /**
     * Prints the options to the console
     * and waits for a valid user input
     */
    public void show() {
        Optional<Option> opt;
        do {
            display(); // print all registerd options
            String input = CLI.in.next(); // get the user input
            opt = menuOptions.stream().filter(o -> o.key.equals(input)).findFirst(); // look if it matches any registerd option
        } while(!opt.isPresent()); // if it does not mathch try again

        opt.get().delegate.run(); // run the handler for the option
    }

    /**
     * Class for representing the options listed in the menu
     */
    private class Option {
        public String key; // the required user input for this option
        public String description; // the shown description
        public Runnable delegate; // the action to perform when selected

        public Option(String key, String description, Runnable delegate) {
            this.key = key;
            this.description = description;
            this.delegate = delegate;
        }
    }
}
