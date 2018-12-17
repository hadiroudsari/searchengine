# search-engines
full text bloom filter based search engine

 we have two tables
 tables script:
```
CREATE TABLE public.bloomtype
(
    bloomid integer NOT NULL DEFAULT nextval('bloomtype_bloomid_seq'::regclass),
    letters bytea[],
    numbers bytea[],
    operands bytea[],
    bloomfid integer NOT NULL DEFAULT nextval('bloomtype_bloomfid_seq'::regclass),
    CONSTRAINT bloomtype_pkey PRIMARY KEY (bloomid),
    CONSTRAINT bloomtype_bloomfid_key UNIQUE (bloomfid)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.bloomtype
    OWNER to postgres;
```
