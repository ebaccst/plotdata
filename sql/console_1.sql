-----------------------------------------------------------------------------------------------------------------
-- Create Function

-- Function: public.fselect_scientific_name(character varying)
-- DROP FUNCTION public.fselect_scientific_name(character varying);
CREATE OR REPLACE FUNCTION public.fselect_scientific_name(
    IN common character varying,
    OUT family_id integer,
    OUT genus_id integer,
    OUT species_id integer)
  RETURNS record AS
$BODY$
BEGIN
  IF exists(SELECT 1
            FROM common_name
            WHERE common_name.name = common AND common_name.species_id IS NOT NULL)
  THEN
    SELECT
      common_name.family_id,
      common_name.genus_id,
      common_name.species_id
    INTO
      family_id, genus_id, species_id
    FROM
      common_name
    WHERE
      common_name.name = common
    GROUP BY
      common_name.family_id, common_name.genus_id, common_name.species_id
    ORDER BY count(common_name.species_id) DESC LIMIT 1;
  ELSEIF exists(SELECT 1
                FROM common_name
                WHERE common_name.name = common AND common_name.genus_id IS NOT NULL)
    THEN
      SELECT
        common_name.family_id,
        common_name.genus_id,
        common_name.species_id
      INTO
        family_id, genus_id, species_id
      FROM
        common_name
      WHERE
        common_name.name = common
      GROUP BY
        common_name.family_id, common_name.genus_id, common_name.species_id
      ORDER BY count(common_name.genus_id) DESC LIMIT 1;
  ELSE
    SELECT
      common_name.family_id,
      common_name.genus_id,
      common_name.species_id
    INTO
      family_id, genus_id, species_id
    FROM
      common_name
    WHERE
      common_name.name = common
    GROUP BY
      common_name.family_id, common_name.genus_id, common_name.species_id
    ORDER BY count(common_name.family_id) DESC LIMIT 1;
  END IF;
END; $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.fselect_scientific_name(character varying)
  OWNER TO eba;

-----------------------------------------------------------------------------------------------------------------
-- Create Function

-- Function: public.fselect_common_name(character varying)
-- DROP FUNCTION public.fselect_common_name(character varying);
CREATE OR REPLACE FUNCTION public.fselect_common_name(
    IN common character varying,
    OUT id integer,
    OUT name character varying,
    OUT family_id integer,
    OUT genus_id integer,
    OUT species_id integer)
  RETURNS record AS
$BODY$
BEGIN
  IF exists(SELECT 1
            FROM common_name
            WHERE common_name.name = common AND common_name.species_id IS NOT NULL)
  THEN
    SELECT
      common_name.id,
      common_name.name,
      common_name.family_id,
      common_name.genus_id,
      common_name.species_id
    INTO
      id, name, family_id, genus_id, species_id
    FROM
      common_name
    WHERE
      common_name.name = common
    GROUP BY
      common_name.id, common_name.name, common_name.family_id, common_name.genus_id, common_name.species_id
    ORDER BY count(common_name.species_id) DESC
    LIMIT 1;
  ELSEIF exists(SELECT 1
                FROM common_name
                WHERE common_name.name = common AND common_name.genus_id IS NOT NULL)
    THEN
      SELECT
      common_name.id,
      common_name.name,
      common_name.family_id,
      common_name.genus_id,
      common_name.species_id
    INTO
      id, name, family_id, genus_id, species_id
    FROM
      common_name
    WHERE
      common_name.name = common
    GROUP BY
      common_name.id, common_name.name, common_name.family_id, common_name.genus_id, common_name.species_id
    ORDER BY count(common_name.genus_id) DESC
    LIMIT 1;
  ELSE
    SELECT
      common_name.id,
      common_name.name,
      common_name.family_id,
      common_name.genus_id,
      common_name.species_id
    INTO
      id, name, family_id, genus_id, species_id
    FROM
      common_name
    WHERE
      common_name.name = common
    GROUP BY
      common_name.id, common_name.name, common_name.family_id, common_name.genus_id, common_name.species_id
    ORDER BY count(common_name.family_id) DESC
    LIMIT 1;
  END IF;
END; $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.fselect_common_name(character varying)
  OWNER TO postgres;

-----------------------------------------------------------------------------------------------------------------
-- Create Function

DROP FUNCTION public.fget_wood_density(
IN family_name VARCHAR,
IN genus_name VARCHAR,
IN species_name VARCHAR,
OUT meanwd DOUBLE PRECISION,
OUT sdwd DOUBLE PRECISION,
OUT levelwd VARCHAR
);

CREATE OR REPLACE FUNCTION fget_wood_density(
  IN  family_name  VARCHAR,
  IN  genus_name   VARCHAR,
  IN  species_name VARCHAR,
  OUT meanwd       DOUBLE PRECISION,
  OUT sdwd         DOUBLE PRECISION,
  OUT levelwd      VARCHAR
) AS $$
BEGIN
  IF exists(SELECT 1
            FROM species AS sp
            WHERE sp.name = species_name AND exists(SELECT 1
                                                    FROM taxonomy AS taxo
                                                    WHERE taxo.species_id = sp.id AND taxo.region IN
                                                                                      ('south america (tropical)', 'south america (extratropical)', 'central america (tropical)')))

  THEN
    SELECT
      AVG(taxo.density),
      0.07082326,
      'species'
    INTO meanwd, sdwd, levelwd
    FROM taxonomy AS taxo, (SELECT s.id AS species_id
                            FROM species AS s
                            WHERE s.name = species_name) AS sp
    WHERE taxo.species_id = sp.species_id AND
          taxo.region IN ('south america (tropical)', 'south america (extratropical)', 'central america (tropical)');
  ELSEIF exists(SELECT 1
                FROM genus AS gn
                WHERE gn.name = genus_name AND exists(SELECT 1
                                                      FROM taxonomy AS taxo
                                                      WHERE taxo.genus_id = gn.id AND taxo.region IN
                                                                                      ('south america (tropical)', 'south america (extratropical)', 'central america (tropical)')))
    THEN
      SELECT
        AVG(taxo.density),
        0.09413391,
        'genus'
      INTO meanwd, sdwd, levelwd
      FROM taxonomy AS taxo, (SELECT g.id AS genus_id
                              FROM genus AS g
                              WHERE g.name = genus_name) AS gen
      WHERE taxo.genus_id = gen.genus_id AND
            taxo.region IN ('south america (tropical)', 'south america (extratropical)', 'central america (tropical)');
  ELSEIF exists(SELECT 1
                FROM family AS fm
                WHERE fm.name = family_name AND exists(SELECT 1
                                                       FROM taxonomy AS taxo
                                                       WHERE taxo.family_id = fm.id AND taxo.region IN
                                                                                        ('south america (tropical)', 'south america (extratropical)', 'central america (tropical)')))
    THEN
      SELECT
        AVG(taxo.density),
        0.12340172,
        'family'
      INTO meanwd, sdwd, levelwd
      FROM taxonomy AS taxo, (SELECT f.id AS family_id
                              FROM family AS f
                              WHERE f.name = family_name) AS fam
      WHERE taxo.family_id = fam.family_id AND
            taxo.region IN ('south america (tropical)', 'south america (extratropical)', 'central america (tropical)');
  END IF;
END; $$
LANGUAGE plpgsql;

-----------------------------------------------------------------------------------------------------------------
-- Create Function

DROP FUNCTION public.fget_plot_wood_density(
IN  plotid   BIGINT,
OUT meanwd DOUBLE PRECISION,
OUT levelwd VARCHAR
);

CREATE OR REPLACE FUNCTION fget_plot_wood_density(
  IN  plotid   BIGINT,
  OUT meanwd    DOUBLE PRECISION,
  OUT levelwd   VARCHAR
) AS $$
BEGIN
  SELECT
    AVG(meas.density),
    'dataset'
  INTO
    meanwd,
    levelwd
  FROM measurements AS meas
  WHERE meas.plot_id = plotid;
END; $$
LANGUAGE plpgsql;

-----------------------------------------------------------------------------------------------------------------
-- Create Function

DROP FUNCTION public.fupdate_measurements_density();
CREATE OR REPLACE FUNCTION public.fupdate_measurements_density()
  RETURNS TRIGGER AS $$
DECLARE
  family_name  VARCHAR;
  genus_name   VARCHAR;
  species_name VARCHAR;
  res_meanwd   DOUBLE PRECISION;
  res_sdwd     DOUBLE PRECISION;
  res_levelwd  VARCHAR;
BEGIN

  SELECT fam.name
  INTO family_name
  FROM family AS fam
  WHERE fam.id = NEW.family_id;
  SELECT gen.name
  INTO genus_name
  FROM genus AS gen
  WHERE gen.id = NEW.genus_id;
  SELECT sp.name
  INTO species_name
  FROM species AS sp
  WHERE sp.id = NEW.species_id;

  IF family_name IS NULL AND genus_name IS NULL AND species_name IS NULL AND NEW.common_name_id IS NOT NULL
  THEN
    SELECT fam.name
    INTO family_name
    FROM common_name AS common, family AS fam
    WHERE common.id = NEW.common_name_id AND common.family_id = fam.id;
    SELECT gen.name
    INTO genus_name
    FROM common_name AS common, genus AS gen
    WHERE common.id = NEW.common_name_id AND common.genus_id = gen.id;
    SELECT sp.name
    INTO species_name
    FROM common_name AS common, species AS sp
    WHERE common.id = NEW.common_name_id AND common.species_id = sp.id;
  END IF;

  SELECT
    meanwd,
    sdwd,
    levelwd
  INTO res_meanwd, res_sdwd, res_levelwd
  FROM fget_wood_density(family_name, genus_name, species_name);

  IF res_meanwd IS NULL
  THEN
    SELECT
      meanwd,
      NULL,
      levelwd
    INTO res_meanwd, res_sdwd, res_levelwd
    FROM fget_plot_wood_density(NEW.plot_id);
  END IF;

  NEW.density:=res_meanwd;
  NEW.density_sd:=res_sdwd;
  UPDATE information
  SET density = res_levelwd
  WHERE id = NEW.information_id;
  RETURN NEW;
END; $$
LANGUAGE plpgsql;

-- Create Trigger

DROP TRIGGER measurements_update_density ON public.measurements;
CREATE TRIGGER measurements_update_density
BEFORE INSERT OR UPDATE
  ON public.measurements
FOR EACH ROW
EXECUTE PROCEDURE public.fupdate_measurements_density();

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
    plots CURSOR FOR SELECT p.id
                     FROM plot p;
BEGIN
  UPDATE measurements
  SET agb = 0.03781 * (measurements.dap ^ 2.7483)
  FROM plot pl, information info
  WHERE measurements.dap IS NOT NULL AND measurements.height_feld IS NOT NULL AND measurements.information_id = info.id AND info.type = 'pal';

  UPDATE measurements
  SET agb = 0.0673 * (((measurements.density * (measurements.dap ^ 2)) * measurements.height_feld) ^ 0.976)
  FROM plot pl, information info
  WHERE measurements.dap IS NOT NULL AND measurements.height_feld IS NOT NULL AND measurements.information_id = info.id AND info.type IS NULL;

  FOR pl IN plots LOOP
    UPDATE plot
    SET agb_plot   = meas.agb_plot,
      density_plot = meas.density_plot
    FROM (SELECT
            AVG(meas.agb)     AS agb_plot,
            AVG(meas.density) AS density_plot
          FROM measurements meas
          WHERE meas.plot_id = pl.id) AS meas
    WHERE id = pl.id;
  END LOOP;
  RETURN TRUE;
END$BODY$
LANGUAGE plpgsql VOLATILE
COST 100;
ALTER FUNCTION public.fheight_agb_living_trees()
OWNER TO postgres;
