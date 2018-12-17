# search-engines
full text bloom filter based search engine


table creation: 

---------------------------------------------------------------------------------------------------------------------------------
-- Table: public.bloomtype

-- DROP TABLE public.bloomtype;

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
    
    ---------------------------------------------------------------------------------------------------------------------------
    
    -- Table: public.url

-- DROP TABLE public.url;

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
    
    ---------------------------------------------------------------------------------------------------------------------------
    
    Functions:
    ---------------------------------------------------------------------------------------------------------------------------
    
    -- FUNCTION: public.search_bloom(integer[], integer[])

-- DROP FUNCTION public.search_bloom(integer[], integer[]);

CREATE OR REPLACE FUNCTION public.search_bloom(
	array1 integer[],
	frequent integer[])
    RETURNS TABLE(bloomid integer, bloomfid integer, mini double precision) 
    LANGUAGE 'plpgsql'

    COST 100
    VOLATILE 
    ROWS 1000
AS $BODY$

 
declare

queryString varchar(30000):='SELECT bloomtype.bloomid,bloomtype.bloomfid,  ';
counter int :=1;
BEGIN
while counter <= array_length(array1,1) loop
queryString:=queryString||' encode(bloomtype.letters[ '|| array1[counter] ||' ],''escape'')::float ' ||' / '||frequent[counter] ;

if counter != array_length(array1,1)
 then queryString:=queryString ||' +';
 
 end if;
  
counter:=counter+1;
end loop;
queryString:=queryString|| '   FROM public.bloomtype;';
RAISE INFO 'information message %', queryString ;

RETURN QUERY execute queryString;

end ; 

$BODY$;

ALTER FUNCTION public.search_bloom(integer[], integer[])
    OWNER TO postgres;

    ---------------------------------------------------------------------------------------------------------------------------
    
    
