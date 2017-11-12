CREATE TRIGGER measurements_update_density
BEFORE INSERT OR UPDATE
  ON public.measurements
FOR EACH ROW
EXECUTE PROCEDURE public.fupdate_measurements_density();

CREATE TRIGGER measurements_fheight_feld_samerica
BEFORE INSERT OR UPDATE
  ON public.measurements
FOR EACH ROW
EXECUTE PROCEDURE public.fheight_feld_samerica();
