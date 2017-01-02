
CREATE PROCEDURE `venenumBonus`.`changeDistrictOfProvider`
  (IN providerId INT, IN district INT)
BEGIN
  DECLARE c INT;
  SELECT COUNT(1) INTO c FROM (SELECT plz FROM lieferbezirk WHERE idLieferbezirk = district) a;
  IF (c = 0) THEN
    SIGNAL SQLSTATE NULL;
  END IF;
  UPDATE Lieferer_lieferbezirk
  SET Lieferbezirk_idLieferbezirk = district
  WHERE Lieferer_idLieferer = providerId;
END;