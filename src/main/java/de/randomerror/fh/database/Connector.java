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
        return null;
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
}
