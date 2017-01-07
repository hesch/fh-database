package de.randomerror.fh.database.entities;

import lombok.Data;

import java.time.LocalDate;

/**
 * Class for representing a database provider
 * it is the equivalent for 'lieferer'
 */
@Data
public class Provider {
    private int id;
    private String passwort;
    private String anrede;
    private String vorname;
    private String nachname;
    private LocalDate geburtsdatum;
    private String strasse;
    private String wohnort;
    private String plz;
    private String tel;
    private String mail;
    private String beschreibung;
    private String kontoNr;
    private String blz;
    private String bankname;
}
