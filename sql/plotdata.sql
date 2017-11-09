-- Function: public.fget_plot_wood_density(character varying)

-- DROP FUNCTION public.fget_plot_wood_density(character varying);

CREATE OR REPLACE FUNCTION public.fget_plot_wood_density(
    IN plot_name character varying,
    OUT meanwd double precision,
    OUT levelwd character varying)
  RETURNS record AS
$BODY$
BEGIN
  SELECT
    AVG(meas.density),
    'dataset'
  INTO
    meanwd,
    levelwd
  FROM measurements AS meas
  WHERE meas.plot = plot_name;
END; $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.fget_plot_wood_density(character varying)
  OWNER TO eba;

-- Function: public.fget_wood_density(character varying, character varying, character varying)

-- DROP FUNCTION public.fget_wood_density(character varying, character varying, character varying);

CREATE OR REPLACE FUNCTION public.fget_wood_density(
    IN family_name character varying,
    IN genus_name character varying,
    IN species_name character varying,
    OUT meanwd double precision,
    OUT sdwd double precision,
    OUT levelwd character varying)
  RETURNS record AS
$BODY$
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
END; $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.fget_wood_density(character varying, character varying, character varying)
  OWNER TO eba;

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

