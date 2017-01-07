package de.randomerror.fh.database.cli;

import de.randomerror.fh.database.db.Connector;
import de.randomerror.fh.database.entities.District;
import de.randomerror.fh.database.entities.DistrictInfo;
import de.randomerror.fh.database.entities.Provider;

import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created by Henri on 02.01.17.
 */
public class CLI {

    public static PrintWriter out = new PrintWriter(System.out);
    public static Scanner in = new Scanner(System.in);
    public static Connector connector = new Connector();

    /**
     * Connects to the database and thes shows the main menu
     * @param args call arguments
     */
    public static void main(String[] args) {
        out.println("Welcome to this very Basic Database Application!\n");
        out.println("Created By:\nJannis Kaiser - 7097566\nHenri Schmidt - 7097650\n");
        connector.connect(); // initiate the database connection

        mainMenu(); // show the main menu
    }

    // exercise 1b
    /**
     * Shows the main menu for selecting what to do
     */
    private static void mainMenu() {
        Menu mainMenu = new Menu();

        // exercise 1a
        mainMenu.registerOption("1", "show district statistics", () -> { // add statistics option to main menu
            // when the option 1 is selected
            choice(connector.getDistricts(), District::getPlz, district -> { // load the districts and tet the user chose one
                String plz = district.getPlz();
                DistrictInfo info = connector.getDistrictInfo(plz); // get the required information for the district
                if (info.getNumProvider() == 0) { // if there are no providers in the district
                    out.println("district has no provider");
                } else { // if there are providers in the district
                    out.printf("number of providers: %d%nnumber of completed deliveries: %d%naverage order value: %f",
                            info.getNumProvider(), info.getNumDeliveries(), info.getAverageOrderValue());
                }
                out.flush();
            });
        });
        // exercise 1b
        mainMenu.registerOption("2", "create new provider", () -> { // add create option to main menu
            // when the option 2 is selected
            out.println("id? ");
            out.flush();
            int id = in.nextInt(); // get id from console
            out.println("name? ");
            out.flush();
            String name = in.next(); // get name from console
//            connector.createProvider(id, name);
            
            // exercise 2b
            connector.createProviderProcedure(id, name); // call the database procedure for creating a provider
        });
        // exercise 1b
        mainMenu.registerOption("3", "change district of provider", () -> { // add update option to main menu
            // when the option 3 is selected
            out.println("provider to change");

            choice(connector.getProviders(), Provider::getNachname, provider -> { // let the user choose a provider
                out.printf("change district of '%s' to%n", provider.getNachname());
                choice(connector.getDistricts(), District::getPlz, district -> { // let the user choose a district to update it to
//                    connector.updateDistrict(provider.getId(), district.getId());
                    
                    // exercise 2a
                    connector.updateDistrictProcedure(provider.getId(), district.getId()); // call th database procedure for updating

                    out.printf("district of '%s' changed to '%s'%n", provider.getNachname(), district.getPlz());
                });
            });
        });

        mainMenu.show(); // show the menu
    }

    /**
     * Creates a console menu that gives the user a choice between the options provided
     * The description function is used to extract the shown description for each object in the list
     * when the user has made a choice the choice Consumer is called with the chosen item
     * if the user made an invalid choice he will be prompted again
     * 
     * @param list The list of options to choose from
     * @param description A function for extracting the description from an item
     * @param choice The callback function for the selected item
     * @param <T> The type of data to choose from
     */
    private static <T> void choice(List<T> list, Function<T, String> description, Consumer<T> choice) {
        Menu choiceMenu = new Menu(); // create a new menu

        for (int j = 0; j < list.size(); j++) { // for each item in the provided in the list
            T t = list.get(j);

            // register a option with the menu
            choiceMenu.registerOption("" + (j + 1), description.apply(t), () -> choice.accept(t));
        }

        choiceMenu.show(); // show the creted menu
    }
}
