
CREATE TABLE city_of_edinburgh_council_area
  (
    gid integer ,
    name character varying(100),
    review character varying(100),
    council character varying(100),
    ward_no numeric,
    ward_name character varying(100),
    date character varying(50),
    report_no character varying(50),
    ward_code character varying(50),
    poly_area numeric,
    part_count numeric,
    geom geometry(MultiPolygon,27700),
    CONSTRAINT city_of_edinburgh_council_area_pkey PRIMARY KEY (gid)
  )
  WITH (
    OIDS=FALSE
  );
