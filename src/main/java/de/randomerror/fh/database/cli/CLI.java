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

    public static void main(String[] args) {

        out.println("Welcome to this very Basic Database Application!");
        connector.connect();

        mainMenu();
    }

    private static void mainMenu() {
        Menu mainMenu = new Menu();

        mainMenu.registerOption("1", "show district statistics", () -> {
            Menu districtMenu = new Menu();
            choice(connector.getDistricts(), District::getPlz, district -> {
                String plz = district.getPlz();
                DistrictInfo info = connector.getDistrictInfo(plz);
                if (info.getNumProvider() == 0) {
                    out.println("Lieferbezirk ohne Lieferer");
                } else {
                    out.printf("Anzahl der Lieferer: %d\nAnzahl der abgeschlossenen Lieferungen: %d\nDurschnittliche Bestellsumme: %f",
                            info.getNumProvider(), info.getNumDeliveries(), info.getAverageOrderValue());
                }
                out.flush();
            });
        });
        mainMenu.registerOption("2", "create new provider", () -> {
            out.println("id? ");
            out.flush();
            int id = in.nextInt();
            out.println("name? ");
            out.flush();
            String name = in.next();
            connector.createProvider(id, name);
        });
        mainMenu.registerOption("3", "change district of provider", () -> {
            out.println("Zu ändernder Lieferer");

            choice(connector.getProviders(), Provider::getNachname, provider -> {
                out.println("Ändere Distrikt zu");
                choice(connector.getDistricts(), District::getPlz, district -> {
                    connector.setDistrict(provider, district);
                    out.printf("Distrikt von %s zu %s geändert%n", provider.getNachname(), district.getPlz());
                });
            });
        });

        mainMenu.show();
    }

    private static <T> void choice(List<T> list, Function<T, String> description, Consumer<T> choice) {
        Menu choiceMenu = new Menu();

        for (int j = 0; j < list.size(); j++) {
            T t = list.get(j);

            choiceMenu.registerOption("" + j, description.apply(t), () -> {
                choice.accept(t);
            });
        }

        choiceMenu.show();
    }
}
