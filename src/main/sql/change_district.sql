
CREATE PROCEDURE `venenumBonus`.`changeDistrictOfProvider`
  (IN providerId INT, IN districtId INT)
BEGIN
  DECLARE postalCode CHAR(5);
  SELECT plz INTO postalCode FROM lieferbezirk WHERE idLieferbezirk = districtId;

  DECLARE c INT;
  SELECT COUNT(1) INTO c FROM getraenkemarkt WHERE plz = postalCode;
  IF (c = 0) THEN
    SIGNAL SQLSTATE NULL;
  END IF;

  UPDATE Lieferer_lieferbezirk
  SET Lieferbezirk_idLieferbezirk = districtId
  WHERE Lieferer_idLieferer = providerId;

  DELETE FROM getraenkemarkt_has_lieferer WHERE Lieferer_idLieferer = providerId;

  INSERT INTO getraenkemarkt_has_lieferer(Lieferer_idLieferer, Getraenkemarkt_idGetraenkemarkt)
  SELECT providerId, idGetraenkemarkt FROM getraenkemarkt WHERE plz = postalCode;
END;