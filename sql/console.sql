DROP TABLE measurements;
DROP TABLE geo_reference;
DROP TABLE information;
DROP TABLE plot;
DROP TABLE owner;

DROP SEQUENCE public.measurements_id_seq;
DROP SEQUENCE public.geo_reference_id_seq;
DROP SEQUENCE public.information_id_seq;
DROP SEQUENCE public.plot_id_seq;
DROP SEQUENCE public.owner_id_seq;

CREATE SEQUENCE public.measurements_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE public.measurements_id_seq
  OWNER TO eba;

CREATE SEQUENCE public.geo_reference_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE public.geo_reference_id_seq
  OWNER TO eba;

CREATE SEQUENCE public.information_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE public.information_id_seq
  OWNER TO eba;

CREATE SEQUENCE public.plot_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE public.plot_id_seq
  OWNER TO eba;

CREATE SEQUENCE public.owner_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE public.owner_id_seq
  OWNER TO eba;

-- SELECT * FROM family WHERE lower(name) = 'na';
-- SELECT * FROM family WHERE lower(name) = '';
-- SELECT * FROM family WHERE lower(name) LIKE '%#n/d%';
-- SELECT * FROM family WHERE lower(name) LIKE '%#valor!%';
-- SELECT * FROM family WHERE lower(name) LIKE '%n/d%';
-- SELECT * FROM family WHERE lower(name) LIKE '%valor%';
--
-- SELECT * FROM common_name WHERE family_id = 194;
-- SELECT * FROM taxonomy WHERE family_id = 194;
--
-- DELETE FROM family WHERE id = 194;
-- SELECT last_value FROm family_id_seq;


-- SELECT * FROM genus WHERE lower(name) = 'na';
-- SELECT * FROM genus WHERE lower(name) = '';
-- SELECT * FROM genus WHERE lower(name) LIKE '%#n/d%';
-- SELECT * FROM genus WHERE lower(name) LIKE '%#valor!%';
-- SELECT * FROM genus WHERE lower(name) LIKE '%n/d%';
-- SELECT * FROM genus WHERE lower(name) LIKE '%valor%';
--
-- SELECT * FROM common_name WHERE genus_id = 1713;
-- SELECT * FROM taxonomy WHERE genus_id = 1713;
--
-- DELETE FROM genus WHERE id = 1713;

-- SELECT * FROM species WHERE lower(name) = 'na';
-- SELECT * FROM species WHERE lower(name) = '';
-- SELECT * FROM species WHERE lower(name) LIKE '%#n/d%';
-- SELECT * FROM species WHERE lower(name) LIKE '%#valor!%';
-- SELECT * FROM species WHERE lower(name) LIKE '%n/d%';
-- SELECT * FROM species WHERE lower(name) LIKE '%valor%';
