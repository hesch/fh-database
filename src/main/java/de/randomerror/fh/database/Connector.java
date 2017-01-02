package de.randomerror.fh.database;

import java.sql.*;

/**
 * Created by Henri on 02.01.17.
 */
public class Connector {

    Connection conn;

    public void connect() {
        try {
            conn = DriverManager.getConnection(Config.URL + Config.DATABASE, Config.USER, Config.PASSWORD);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public DistrictInfo getDistrictInfo(String postalCode) {
        return new DistrictInfo(
                numProvidersInDistrict(postalCode),
                numClosedOrdersInDistrict(postalCode),
                averageOrderValueInDistrict(postalCode));
    }

    public int numProvidersInDistrict(String postalCode) {
        String query = "SELECT COUNT(1) AS anzahl " +
                "FROM " +
                "Lieferer_lieferbezirk JOIN venenumBonus.lieferbezirk " +
                "ON Lieferbezirk_idLieferbezirk=idLieferbezirk " +
                "WHERE plz LIKE ?;";
        try {
            PreparedStatement s = conn.prepareStatement(query);
            s.setString(1, postalCode);
            ResultSet set = s.executeQuery();
            return set.first() ? set.getInt(1) : 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int numClosedOrdersInDistrict(String postalCode) {
        String query = "SELECT COUNT(1) AS anzahl " +
                "FROM " +
                "bestellung JOIN getraenkemarkt " +
                "ON Getraenkemarkt_idGetraenkemarkt=idGetraenkemarkt " +
                "WHERE plz LIKE ? AND bestellstatus LIKE 'abgeschlossen';";
        try {
            PreparedStatement s = conn.prepareStatement(query);
            s.setString(1, postalCode);
            ResultSet set = s.executeQuery();
            return set.first() ? set.getInt(1) : 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public double averageOrderValueInDistrict(String postalCode) {
        String query = "SELECT AVG(gesamtpreis) AS average " +
                "FROM " +
                "(SELECT (1-reduktion)*preis*anzahl AS gesamtpreis " +
                "FROM " +
                "bestellposition JOIN artikel " +
                "ON Artikel_idArtikel = idArtikel " +
                "JOIN bestellung " +
                "ON Bestellung_idBestellung = idBestellung " +
                "JOIN getraenkemarkt " +
                "ON Getraenkemarkt_idGetraenkemarkt = idGetraenkemarkt " +
                "WHERE plz LIKE ?) a;";
        try {
            PreparedStatement s = conn.prepareStatement(query);
            s.setString(1, postalCode);
            ResultSet set = s.executeQuery();
            return set.first() ? set.getDouble(1) : 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
