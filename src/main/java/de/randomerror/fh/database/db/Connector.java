package de.randomerror.fh.database.db;

import de.randomerror.fh.database.entities.District;
import de.randomerror.fh.database.entities.DistrictInfo;
import de.randomerror.fh.database.entities.Provider;

import java.sql.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Henri on 02.01.17.
 */
public class Connector {

    private Connection conn;

    public void connect() {
        try {
            conn = DriverManager.getConnection(Config.URL + Config.DATABASE, Config.USER, Config.PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // exercise 1a
    public DistrictInfo getDistrictInfo(String postalCode) {
        return new DistrictInfo(
                numProvidersInDistrict(postalCode),
                numClosedOrdersInDistrict(postalCode),
                averageOrderValueInDistrict(postalCode));
    }

    public int numProvidersInDistrict(String postalCode) {
        String query = "SELECT COUNT(1) AS anzahl " +
                "FROM venenumbonus.lieferer_lieferbezirk JOIN venenumbonus.lieferbezirk " +
                "ON Lieferbezirk_idLieferbezirk=idLieferbezirk " +
                "WHERE plz LIKE ?;";

        try (PreparedStatement s = conn.prepareStatement(query)) {
            s.setString(1, postalCode);
            ResultSet set = s.executeQuery();
            return set.first() ? set.getInt(1) : 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int numClosedOrdersInDistrict(String postalCode) {
        String query = "SELECT COUNT(1) AS anzahl " +
                "FROM venenumbonus.bestellung " +
                "JOIN venenumbonus.getraenkemarkt ON Getraenkemarkt_idGetraenkemarkt=idGetraenkemarkt " +
                "WHERE plz LIKE ? " +
                "AND bestellstatus LIKE 'abgeschlossen';";

        try (PreparedStatement s = conn.prepareStatement(query)) {
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
                "FROM venenumbonus.bestellposition " +
                "JOIN venenumbonus.artikel ON Artikel_idArtikel = idArtikel " +
                "JOIN venenumbonus.bestellung ON Bestellung_idBestellung = idBestellung " +
                "JOIN venenumbonus.getraenkemarkt ON Getraenkemarkt_idGetraenkemarkt = idGetraenkemarkt " +
                "WHERE plz LIKE ?) a;";
        try (PreparedStatement s = conn.prepareStatement(query)) {
            s.setString(1, postalCode);
            ResultSet set = s.executeQuery();
            return set.first() ? set.getDouble(1) : 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<District> getDistricts() {
        String query = "SELECT * FROM lieferbezirk;";
        List<District> result = new LinkedList<>();

        try (PreparedStatement s = conn.prepareStatement(query)) {
            ResultSet set = s.executeQuery();
            while (set.next()) {
                result.add(new District(set.getInt(1), set.getString(2)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    public void createProvider(int id, String firstName) {
        String lastName = "SchmidtKaiser";
        String banking = "7097650";

        String providerQuery = "INSERT INTO venenumbonus.lieferer (idLieferer, passwort, anrede, vorname, nachname, geburtsdatum, strasse, wohnort, plz, tel, mail, beschreibung, konto_nr, blz, bankname) " +
                "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement s = conn.prepareStatement(providerQuery)) {
            s.setInt(1, id);
            s.setString(2, "hafdfhjka");
            s.setString(3, "frau");
            s.setString(4, firstName);
            s.setString(5, lastName);
            s.setDate(6, new java.sql.Date(new Date().getTime()));
            s.setString(7, "jfdsjdsjka");
            s.setString(8, "hafdfhjka");
            s.setString(9, "39000");
            s.setString(10, "2935899835");
            s.setString(11, "test@test");
            s.setString(12, "ich bin toll");
            s.setString(13, banking);
            s.setString(14, "49050101");
            s.setString(15, "bingo bank");
            s.execute();
            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                throw new RuntimeException(e1);
            }
            throw new RuntimeException(e);
        }
    }

    public List<Provider> getProviders() {
        String query = "SELECT * FROM venenumbonus.lieferer;";
        List<Provider> result = new LinkedList<>();

        try (PreparedStatement s = conn.prepareStatement(query)) {
            ResultSet set = s.executeQuery();
            while (set.next()) {
                Provider provider = new Provider();
                provider.setId(set.getInt("idLieferer"));
                provider.setPasswort(set.getString("passwort"));
                provider.setAnrede(set.getString("anrede"));
                provider.setVorname(set.getString("vorname"));
                provider.setNachname(set.getString("nachname"));
                provider.setGeburtsdatum(set.getDate("geburtsdatum").toLocalDate());
                provider.setStrasse(set.getString("strasse"));
                provider.setWohnort(set.getString("wohnort"));
                provider.setPlz(set.getString("plz"));
                provider.setTel(set.getString("tel"));
                provider.setMail(set.getString("mail"));
                provider.setBeschreibung(set.getString("beschreibung"));
                provider.setKontoNr(set.getString("konto_nr"));
                provider.setBlz(set.getString("blz"));
                provider.setBankname(set.getString("bankname"));
                result.add(provider);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    public void updateDistrict(int providerId, int districtId) {
        String query = "UPDATE venenumbonus.lieferer_lieferbezirk " +
                "SET Lieferbezirk_idLieferbezirk=? " +
                "WHERE Lieferer_idLieferer=?;";

        try (PreparedStatement s = conn.prepareStatement(query)) {
            s.setInt(1, districtId);
            s.setInt(2, providerId);

            s.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createProviderProcedure(int id, String vorname) {
        String query = "CALL venenumbonus.createProviderProc (?,?)";
        
        try (CallableStatement stmt = conn.prepareCall(query)) {
            stmt.setInt(1, id);
            stmt.setString(2, vorname);
            
            stmt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    public void updateDistrictProcedure(int providerId, int districtId) {
        String query = "CALL venenumbonus.changeDistrictOfProvider (?,?)";

        try (CallableStatement stmt = conn.prepareCall(query)) {
            stmt.setInt(1, providerId);
            stmt.setInt(2, districtId);
            
            stmt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
