
CREATE INDEX ON public.plot
(name);
CREATE INDEX ON public.plot
(transect);
CREATE INDEX ON public.plot
(owner_id);

CREATE INDEX plot_gix ON public.plot USING GIST
(geom);

VACUUM ANALYZE plot;
CLUSTER plot USING plot_gix;
ANALYZE plot;

CREATE INDEX ON public.owner
(name);
CREATE INDEX ON public.owner
(institution);

CREATE INDEX ON public.family
(name);
CREATE INDEX ON public.genus
(name);
CREATE INDEX ON public.species
(name);

CREATE INDEX ON public.taxonomy
(family_id);
CREATE INDEX ON public.taxonomy
(genus_id);
CREATE INDEX ON public.taxonomy
(species_id);

CREATE INDEX ON public.common_name
(name);
CREATE INDEX ON public.common_name
(family_id);
CREATE INDEX ON public.common_name
(genus_id);
CREATE INDEX ON public.common_name
(species_id);

CREATE INDEX ON public.measurements
(plot_id);
CREATE INDEX ON public.measurements
(geo_reference_id);
CREATE INDEX ON public.measurements
(information_id);
CREATE INDEX ON public.measurements
(family_id);
CREATE INDEX ON public.measurements
(genus_id);
CREATE INDEX ON public.measurements
(species_id);
CREATE INDEX ON public.measurements
(common_name_id);
