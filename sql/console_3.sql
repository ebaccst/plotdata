-----------------------------------------------------------------------------------------------------------------
-- Create Function

-- (a) Living trees, Chave et al. (2014): AGB = 0.0673 * (((density * (dap ^ 2)) * height_feld) ^ 0.976)
-- (c) Living palms, Goodman et al. (2013): AGB = 0.03781 * (dap ^ 2.7483)
-- ρw = densidade da madeira
-- DBH= diâmetro (DAP)
-- Ht= Altura total
--
-- kg/m²

DROP FUNCTION public.fheight_agb_living_trees();
CREATE OR REPLACE FUNCTION public.fheight_agb_living_trees()
  RETURNS BOOLEAN AS
$BODY$
DECLARE
    plots CURSOR FOR SELECT
                       p.id,
                       p.geom
                     FROM plot p
                     WHERE p.geom IS NOT NULL;
BEGIN
  UPDATE measurements
  SET agb = 0.03781 * (measurements.dap ^ 2.7483)
  FROM plot pl, information info
  WHERE
    measurements.dap IS NOT NULL AND measurements.height_feld IS NOT NULL AND measurements.information_id = info.id AND
    info.type = 'pal';

  UPDATE measurements
  SET agb = 0.0673 * (((measurements.density * (measurements.dap ^ 2)) * measurements.height_feld) ^ 0.976)
  FROM plot pl, information info
  WHERE
    measurements.dap IS NOT NULL AND measurements.height_feld IS NOT NULL AND measurements.information_id = info.id AND
    info.type IS NULL;

  FOR pl IN plots LOOP
    UPDATE plot
    SET agb_plot   = meas.agb_plot,
      density_plot = meas.density_plot
    FROM (SELECT
            (SUM(meas.agb) / st_area(pl.geom)) AS agb_plot,
            AVG(meas.density)                  AS density_plot
          FROM measurements meas
          WHERE meas.plot_id = pl.id AND meas.agb IS NOT NULL) AS meas
    WHERE id = pl.id;
  END LOOP;
  RETURN TRUE;
END$BODY$
LANGUAGE plpgsql VOLATILE
COST 100;
ALTER FUNCTION public.fheight_agb_living_trees()
OWNER TO postgres;

SELECT fheight_agb_living_trees();
