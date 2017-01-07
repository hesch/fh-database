DROP PROCEDURE IF EXISTS `venenumbonus`.`createProviderProc`;

# exercise 2b / 2c
CREATE PROCEDURE `venenumbonus`.`createProviderProc`
  (IN id INTEGER, IN vorname VARCHAR(45))
  BEGIN
    DECLARE bezirk INT(10);
    DECLARE done BOOL DEFAULT FALSE;
    DECLARE market_id INT;

    DECLARE market_id_cursor CURSOR FOR SELECT getraenkemarkt.idGetraenkemarkt FROM getraenkemarkt WHERE getraenkemarkt.plz IN (
      SELECT getraenkemarkt.plz
      FROM getraenkemarkt
      WHERE name = 'Top'
    );

    DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET done=TRUE;


    SELECT lieferbezirk.idLieferbezirk
    INTO bezirk
    FROM lieferbezirk
    WHERE lieferbezirk.plz IN (
      SELECT getraenkemarkt.plz
      FROM getraenkemarkt
      WHERE name = 'Top'
    );

    INSERT INTO lieferer (idLieferer, passwort, anrede, vorname, nachname, geburtsdatum, strasse, wohnort, plz, tel, mail, beschreibung, konto_nr, blz, bankname)
    VALUES
      (id, 'passwort', 'anrede', vorname, 'Kaiser', CURDATE(), 'strasse', 'wohnort', plz, 'tel', 'mail', 'beschreibung',
       '7097566', 'blz', 'bankname');

    INSERT INTO lieferer_lieferbezirk (Lieferbezirk_idLieferbezirk, Lieferer_idLieferer, Lieferzeit, Lieferpreis)
    VALUES
      (bezirk, id, CURDATE(), 10.0);
    
#     exercise 2c
    
    OPEN market_id_cursor;
    
    WHILE NOT done DO 
      FETCH market_id_cursor INTO market_id;
      IF NOT done THEN
        INSERT INTO getraenkemarkt_has_lieferer VALUES (
          id,
          market_id
        );
      END IF;
    END WHILE;
    
    CLOSE market_id_cursor;
  END;