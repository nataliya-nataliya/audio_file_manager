PGDMP         !                 |           audio    14.5    14.5 	    �           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            �           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            �           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            �           1262    49525    audio    DATABASE     b   CREATE DATABASE audio WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'Russian_Russia.1251';
    DROP DATABASE audio;
                postgres    false            �            1259    49557    info    TABLE     �   CREATE TABLE public.info (
    id bigint NOT NULL,
    date timestamp(6) without time zone,
    duration integer NOT NULL,
    file_name character varying(64)
);
    DROP TABLE public.info;
       public         heap    postgres    false            �            1259    49556    info_id_seq    SEQUENCE     t   CREATE SEQUENCE public.info_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 "   DROP SEQUENCE public.info_id_seq;
       public          postgres    false    210            �           0    0    info_id_seq    SEQUENCE OWNED BY     ;   ALTER SEQUENCE public.info_id_seq OWNED BY public.info.id;
          public          postgres    false    209            \           2604    49560    info id    DEFAULT     b   ALTER TABLE ONLY public.info ALTER COLUMN id SET DEFAULT nextval('public.info_id_seq'::regclass);
 6   ALTER TABLE public.info ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    209    210    210            ^           2606    49562    info info_pkey 
   CONSTRAINT     L   ALTER TABLE ONLY public.info
    ADD CONSTRAINT info_pkey PRIMARY KEY (id);
 8   ALTER TABLE ONLY public.info DROP CONSTRAINT info_pkey;
       public            postgres    false    210           