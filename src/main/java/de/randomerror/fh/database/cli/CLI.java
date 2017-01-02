package de.randomerror.fh.database.cli;

import de.randomerror.fh.database.db.Connector;
import de.randomerror.fh.database.entities.District;
import de.randomerror.fh.database.entities.DistrictInfo;

import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;

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

        mainMenu.registerOption("1", "show district statistics", key -> {
            Menu districtMenu = new Menu();
            List<District> districts = connector.getDistricts();

            for(int i = 0; i < districts.size(); i++) {
                districtMenu.registerOption("" + i, districts.get(i).getPlz(), k -> {
                    int num = Integer.parseInt(k);
                    String plz = districts.get(num).getPlz();
                    DistrictInfo info = connector.getDistrictInfo(plz);
                    if(info.getNumProvider() == 0) {
                        out.println("Lieferbezirk ohne Lieferer");
                    } else {
                        out.printf("Anzahl der Lieferer: %d\nAnzahl der abgeschlossenen Lieferungen: %d\nDurschnittliche Bestellsumme: %f",
                                info.getNumProvider(), info.getNumDeliveries(), info.getAverageOrderValue());
                    }
                    out.flush();
                });
            }
            districtMenu.show();
        });
        mainMenu.registerOption("2", "create new provider", key -> {
            System.out.println("number 2");
            out.println(2);
        });
        mainMenu.registerOption("3", "change district of provider", key -> {
            System.out.println("number 3");
            out.println(3);
        });

        mainMenu.show();
    }
}
