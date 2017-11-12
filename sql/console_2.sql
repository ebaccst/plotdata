-----------------------------------------------------------------------------------------------------------------
-- Create Function

-- Feldpaush et al. (2012) averaged model SAmerica
--
-- Ht=42,574*(1-exp(-0,0482*DAP^0,8307)

-- DROP FUNCTION public.fheight_feld_samerica();
CREATE OR REPLACE FUNCTION public.fheight_feld_samerica()
  RETURNS TRIGGER AS
$BODY$
DECLARE
  var_dead BOOLEAN;
BEGIN

  SELECT info.dead
  INTO var_dead
  FROM information info
  WHERE info.id = NEW.information_id;

  IF NEW.dap IS NULL OR var_dead IS TRUE
  THEN
    NEW.height_feld:= NULL;
  ELSE
    NEW.height_feld:=42.574 * (1 - exp((-0.0482) * (NEW.dap ^ 0.8307)));
  END IF;

  RETURN NEW;
END$BODY$
LANGUAGE plpgsql VOLATILE
COST 100;
ALTER FUNCTION public.fheight_feld_samerica()
OWNER TO postgres;

-- Create Trigger

-- DROP TRIGGER measurements_fheight_feld_samerica ON public.measurements;
CREATE TRIGGER measurements_fheight_feld_samerica
BEFORE INSERT OR UPDATE
  ON public.measurements
FOR EACH ROW
EXECUTE PROCEDURE public.fheight_feld_samerica();