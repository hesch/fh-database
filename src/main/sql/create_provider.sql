DROP PROCEDURE IF EXISTS `venenumbonus`.`createProviderProc`;

CREATE PROCEDURE `venenumbonus`.`createProviderProc`
  (IN id INTEGER, IN vorname VARCHAR(45))
  BEGIN
    
    DECLARE plz CHAR(5);
    DECLARE bezirk INT(10);

    SELECT getraenkemarkt.plz
    INTO plz
    FROM getraenkemarkt
    WHERE name = 'Top';

    SELECT lieferbezirk.idLieferbezirk
    INTO bezirk
    FROM lieferbezirk
    WHERE lieferbezirk.plz = plz;

    INSERT INTO lieferer (idLieferer, passwort, anrede, vorname, nachname, geburtsdatum, strasse, wohnort, plz, tel, mail, beschreibung, konto_nr, blz, bankname)
    VALUES
      (id, 'passwort', 'anrede', vorname, 'Kaiser', CURDATE(), 'strasse', 'wohnort', plz, 'tel', 'mail', 'beschreibung',
       '07097566', 'blz', 'bankname');

    INSERT INTO lieferer_lieferbezirk (Lieferbezirk_idLieferbezirk, Lieferer_idLieferer, Lieferzeit, Lieferpreis)
    VALUES
      (bezirk, id, CURDATE(), 10.0);
  END;