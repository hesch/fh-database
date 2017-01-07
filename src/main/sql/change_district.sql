DROP PROCEDURE IF EXISTS `venenumBonus`.`changeDistrictOfProvider`;

CREATE PROCEDURE `venenumBonus`.`changeDistrictOfProvider`
  (IN providerId INT, IN districtId INT)
BEGIN
  DECLARE postalCode CHAR(5);
  DECLARE c INT(10);

  SELECT plz INTO postalCode FROM lieferbezirk WHERE idLieferbezirk = districtId;

  SELECT COUNT(*) INTO c FROM getraenkemarkt WHERE plz LIKE postalCode;
  IF (c = 0) THEN
    SIGNAL SQLSTATE '01000';
  END IF;

  UPDATE Lieferer_lieferbezirk
  SET Lieferbezirk_idLieferbezirk = districtId
  WHERE Lieferer_idLieferer = providerId;

  DELETE FROM getraenkemarkt_has_lieferer WHERE Lieferer_idLieferer = providerId;

  INSERT INTO getraenkemarkt_has_lieferer(Lieferer_idLieferer, Getraenkemarkt_idGetraenkemarkt)
  SELECT providerId, idGetraenkemarkt FROM getraenkemarkt WHERE plz LIKE postalCode;
END;