--
-- PostgreSQL database dump
--

-- Dumped from database version 10.3
-- Dumped by pg_dump version 10.3

-- Started on 2018-04-18 20:50:19

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 7 (class 2615 OID 16385)
-- Name: mod_administracion; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA mod_administracion;


ALTER SCHEMA mod_administracion OWNER TO postgres;

--
-- TOC entry 1 (class 3079 OID 12278)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2171 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 199 (class 1259 OID 16394)
-- Name: s_perfil_usuario; Type: TABLE; Schema: mod_administracion; Owner: postgres
--

CREATE TABLE mod_administracion.s_perfil_usuario (
    codigo_per integer NOT NULL,
    nombre_per character(30)
);


ALTER TABLE mod_administracion.s_perfil_usuario OWNER TO postgres;

--
-- TOC entry 200 (class 1259 OID 16397)
-- Name: s_perfil_usuario_codigo_per_seq; Type: SEQUENCE; Schema: mod_administracion; Owner: postgres
--

CREATE SEQUENCE mod_administracion.s_perfil_usuario_codigo_per_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mod_administracion.s_perfil_usuario_codigo_per_seq OWNER TO postgres;

--
-- TOC entry 2172 (class 0 OID 0)
-- Dependencies: 200
-- Name: s_perfil_usuario_codigo_per_seq; Type: SEQUENCE OWNED BY; Schema: mod_administracion; Owner: postgres
--

ALTER SEQUENCE mod_administracion.s_perfil_usuario_codigo_per_seq OWNED BY mod_administracion.s_perfil_usuario.codigo_per;


--
-- TOC entry 198 (class 1259 OID 16388)
-- Name: s_usuario; Type: TABLE; Schema: mod_administracion; Owner: postgres
--

CREATE TABLE mod_administracion.s_usuario (
    codigo_usu integer NOT NULL,
    nombre_usu character(50),
    usuario_usu character(12),
    clave_usu character(12),
    codigo_per integer
);


ALTER TABLE mod_administracion.s_usuario OWNER TO postgres;

--
-- TOC entry 197 (class 1259 OID 16386)
-- Name: s_usuario_codigo_usu_seq; Type: SEQUENCE; Schema: mod_administracion; Owner: postgres
--

CREATE SEQUENCE mod_administracion.s_usuario_codigo_usu_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mod_administracion.s_usuario_codigo_usu_seq OWNER TO postgres;

--
-- TOC entry 2173 (class 0 OID 0)
-- Dependencies: 197
-- Name: s_usuario_codigo_usu_seq; Type: SEQUENCE OWNED BY; Schema: mod_administracion; Owner: postgres
--

ALTER SEQUENCE mod_administracion.s_usuario_codigo_usu_seq OWNED BY mod_administracion.s_usuario.codigo_usu;


--
-- TOC entry 2032 (class 2604 OID 16399)
-- Name: s_perfil_usuario codigo_per; Type: DEFAULT; Schema: mod_administracion; Owner: postgres
--

ALTER TABLE ONLY mod_administracion.s_perfil_usuario ALTER COLUMN codigo_per SET DEFAULT nextval('mod_administracion.s_perfil_usuario_codigo_per_seq'::regclass);


--
-- TOC entry 2031 (class 2604 OID 16391)
-- Name: s_usuario codigo_usu; Type: DEFAULT; Schema: mod_administracion; Owner: postgres
--

ALTER TABLE ONLY mod_administracion.s_usuario ALTER COLUMN codigo_usu SET DEFAULT nextval('mod_administracion.s_usuario_codigo_usu_seq'::regclass);


--
-- TOC entry 2162 (class 0 OID 16394)
-- Dependencies: 199
-- Data for Name: s_perfil_usuario; Type: TABLE DATA; Schema: mod_administracion; Owner: postgres
--

COPY mod_administracion.s_perfil_usuario (codigo_per, nombre_per) FROM stdin;
1	Administrado                  
\.


--
-- TOC entry 2161 (class 0 OID 16388)
-- Dependencies: 198
-- Data for Name: s_usuario; Type: TABLE DATA; Schema: mod_administracion; Owner: postgres
--

COPY mod_administracion.s_usuario (codigo_usu, nombre_usu, usuario_usu, clave_usu, codigo_per) FROM stdin;
1	Brian                                             	\N	1234        	1
2	Carlos                                            	\N	1234        	1
3	Pedro                                             	\N	789         	1
\.


--
-- TOC entry 2174 (class 0 OID 0)
-- Dependencies: 200
-- Name: s_perfil_usuario_codigo_per_seq; Type: SEQUENCE SET; Schema: mod_administracion; Owner: postgres
--

SELECT pg_catalog.setval('mod_administracion.s_perfil_usuario_codigo_per_seq', 1, true);


--
-- TOC entry 2175 (class 0 OID 0)
-- Dependencies: 197
-- Name: s_usuario_codigo_usu_seq; Type: SEQUENCE SET; Schema: mod_administracion; Owner: postgres
--

SELECT pg_catalog.setval('mod_administracion.s_usuario_codigo_usu_seq', 3, true);


--
-- TOC entry 2037 (class 2606 OID 16404)
-- Name: s_perfil_usuario pk_s_perfil_usuario; Type: CONSTRAINT; Schema: mod_administracion; Owner: postgres
--

ALTER TABLE ONLY mod_administracion.s_perfil_usuario
    ADD CONSTRAINT pk_s_perfil_usuario PRIMARY KEY (codigo_per);


--
-- TOC entry 2035 (class 2606 OID 16393)
-- Name: s_usuario pk_s_usuario; Type: CONSTRAINT; Schema: mod_administracion; Owner: postgres
--

ALTER TABLE ONLY mod_administracion.s_usuario
    ADD CONSTRAINT pk_s_usuario PRIMARY KEY (codigo_usu);


--
-- TOC entry 2033 (class 1259 OID 16410)
-- Name: fki_s_usurio_vs_s_perfil_usuario; Type: INDEX; Schema: mod_administracion; Owner: postgres
--

CREATE INDEX fki_s_usurio_vs_s_perfil_usuario ON mod_administracion.s_usuario USING btree (codigo_per);


--
-- TOC entry 2038 (class 2606 OID 16405)
-- Name: s_usuario fk_s_usurio_vs_s_perfil_usuario; Type: FK CONSTRAINT; Schema: mod_administracion; Owner: postgres
--

ALTER TABLE ONLY mod_administracion.s_usuario
    ADD CONSTRAINT fk_s_usurio_vs_s_perfil_usuario FOREIGN KEY (codigo_per) REFERENCES mod_administracion.s_perfil_usuario(codigo_per);


-- Completed on 2018-04-18 20:50:19

--
-- PostgreSQL database dump complete
--

