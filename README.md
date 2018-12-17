# search-engines
full text bloom filter based search engine

 we have two tables
 tables script 
 
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

and 

```
CREATE TABLE public.url
(
    urlid integer NOT NULL DEFAULT nextval('url_urlid_seq'::regclass),
    urlfullname text COLLATE pg_catalog."default" NOT NULL,
    urlalltext text COLLATE pg_catalog."default" NOT NULL,
    urlhashcode integer NOT NULL DEFAULT nextval('url_urlhashcode_seq'::regclass),
    CONSTRAINT url_pkey PRIMARY KEY (urlid),
    CONSTRAINT url_urlfullname_key UNIQUE (urlfullname)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.url
    OWNER to postgres;
```
