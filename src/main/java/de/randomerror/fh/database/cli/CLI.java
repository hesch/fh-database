package de.randomerror.fh.database.cli;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by Henri on 02.01.17.
 */
public class CLI {

    public static PrintWriter out = new PrintWriter(System.out);
    public static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        out.println("Welcome to this very Basic Database Application!");

        menu();
    }

    private static void menu() {

    }
}
