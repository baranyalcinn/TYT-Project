PGDMP                      |            tytDB    15.7    16.3 8    N           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            O           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            P           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            Q           1262    16398    tytDB    DATABASE     |   CREATE DATABASE "tytDB" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Turkish_T�rkiye.1254';
    DROP DATABASE "tytDB";
                postgres    false            �            1259    16647    cart_entity    TABLE     �   CREATE TABLE public.cart_entity (
    id bigint NOT NULL,
    quantity integer NOT NULL,
    total_price double precision NOT NULL,
    applied_offer_id bigint,
    product_id bigint
);
    DROP TABLE public.cart_entity;
       public         heap    postgres    false            �            1259    16646    cart_entity_id_seq    SEQUENCE     �   ALTER TABLE public.cart_entity ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.cart_entity_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    224            �            1259    16400 
   categories    TABLE     (  CREATE TABLE public.categories (
    id bigint NOT NULL,
    created_at timestamp(6) without time zone,
    created_by character varying(255),
    updated_at timestamp(6) without time zone,
    updated_by character varying(255),
    is_active boolean NOT NULL,
    name character varying(255)
);
    DROP TABLE public.categories;
       public         heap    postgres    false            �            1259    16399    categories_id_seq    SEQUENCE     �   ALTER TABLE public.categories ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.categories_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    215            �            1259    16448    offers    TABLE     /  CREATE TABLE public.offers (
    id bigint NOT NULL,
    name character varying(255),
    offer_type character varying(255),
    CONSTRAINT offers_offer_type_check CHECK (((offer_type)::text = ANY ((ARRAY['BUY_THREE_PAY_TWO'::character varying, 'TEN_PERCENT_DISCOUNT'::character varying])::text[])))
);
    DROP TABLE public.offers;
       public         heap    postgres    false            �            1259    16447    offers_id_seq    SEQUENCE     �   ALTER TABLE public.offers ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.offers_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    222            �            1259    16723    order_campaign    TABLE     Z   CREATE TABLE public.order_campaign (
    offer_id bigint,
    order_id bigint NOT NULL
);
 "   DROP TABLE public.order_campaign;
       public         heap    postgres    false            �            1259    16658    order_product    TABLE     �   CREATE TABLE public.order_product (
    id bigint NOT NULL,
    quantity integer NOT NULL,
    order_id bigint,
    product_id bigint
);
 !   DROP TABLE public.order_product;
       public         heap    postgres    false            �            1259    16657    order_product_id_seq    SEQUENCE     �   ALTER TABLE public.order_product ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.order_product_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    226            �            1259    16664    orders    TABLE     o  CREATE TABLE public.orders (
    id bigint NOT NULL,
    order_date timestamp(6) without time zone,
    order_number uuid,
    total double precision NOT NULL,
    payment_method character varying(255),
    CONSTRAINT orders_payment_method_check CHECK (((payment_method)::text = ANY ((ARRAY['CASH'::character varying, 'CREDIT_CARD'::character varying])::text[])))
);
    DROP TABLE public.orders;
       public         heap    postgres    false            �            1259    16663    orders_id_seq    SEQUENCE     �   ALTER TABLE public.orders ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.orders_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    228            �            1259    16408    products    TABLE     �  CREATE TABLE public.products (
    id bigint NOT NULL,
    created_at timestamp(6) without time zone,
    created_by character varying(255),
    updated_at timestamp(6) without time zone,
    updated_by character varying(255),
    description character varying(255),
    is_active boolean NOT NULL,
    name character varying(255),
    price double precision NOT NULL,
    stock integer NOT NULL,
    category_id bigint
);
    DROP TABLE public.products;
       public         heap    postgres    false            �            1259    16407    products_id_seq    SEQUENCE     �   ALTER TABLE public.products ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.products_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    217            �            1259    16422 	   user_role    TABLE       CREATE TABLE public.user_role (
    user_id bigint NOT NULL,
    roles character varying(255),
    CONSTRAINT user_role_roles_check CHECK (((roles)::text = ANY ((ARRAY['ADMIN'::character varying, 'MANAGER'::character varying, 'CASHIER'::character varying])::text[])))
);
    DROP TABLE public.user_role;
       public         heap    postgres    false            �            1259    16427    users    TABLE     �  CREATE TABLE public.users (
    id bigint NOT NULL,
    created_at timestamp(6) without time zone,
    created_by character varying(255),
    updated_at timestamp(6) without time zone,
    updated_by character varying(255),
    email character varying(255) NOT NULL,
    is_active boolean NOT NULL,
    name character varying(255) NOT NULL,
    password character varying(255) NOT NULL,
    surname character varying(255) NOT NULL
);
    DROP TABLE public.users;
       public         heap    postgres    false            �            1259    16426    users_id_seq    SEQUENCE     �   ALTER TABLE public.users ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    220            F          0    16647    cart_entity 
   TABLE DATA           ^   COPY public.cart_entity (id, quantity, total_price, applied_offer_id, product_id) FROM stdin;
    public          postgres    false    224   mF       =          0    16400 
   categories 
   TABLE DATA           i   COPY public.categories (id, created_at, created_by, updated_at, updated_by, is_active, name) FROM stdin;
    public          postgres    false    215   �F       D          0    16448    offers 
   TABLE DATA           6   COPY public.offers (id, name, offer_type) FROM stdin;
    public          postgres    false    222   CG       K          0    16723    order_campaign 
   TABLE DATA           <   COPY public.order_campaign (offer_id, order_id) FROM stdin;
    public          postgres    false    229   �G       H          0    16658    order_product 
   TABLE DATA           K   COPY public.order_product (id, quantity, order_id, product_id) FROM stdin;
    public          postgres    false    226   �G       J          0    16664    orders 
   TABLE DATA           U   COPY public.orders (id, order_date, order_number, total, payment_method) FROM stdin;
    public          postgres    false    228   gH       ?          0    16408    products 
   TABLE DATA           �   COPY public.products (id, created_at, created_by, updated_at, updated_by, description, is_active, name, price, stock, category_id) FROM stdin;
    public          postgres    false    217   bL       @          0    16422 	   user_role 
   TABLE DATA           3   COPY public.user_role (user_id, roles) FROM stdin;
    public          postgres    false    218   ~M       B          0    16427    users 
   TABLE DATA           ~   COPY public.users (id, created_at, created_by, updated_at, updated_by, email, is_active, name, password, surname) FROM stdin;
    public          postgres    false    220   �M       R           0    0    cart_entity_id_seq    SEQUENCE SET     A   SELECT pg_catalog.setval('public.cart_entity_id_seq', 31, true);
          public          postgres    false    223            S           0    0    categories_id_seq    SEQUENCE SET     ?   SELECT pg_catalog.setval('public.categories_id_seq', 7, true);
          public          postgres    false    214            T           0    0    offers_id_seq    SEQUENCE SET     ;   SELECT pg_catalog.setval('public.offers_id_seq', 2, true);
          public          postgres    false    221            U           0    0    order_product_id_seq    SEQUENCE SET     C   SELECT pg_catalog.setval('public.order_product_id_seq', 41, true);
          public          postgres    false    225            V           0    0    orders_id_seq    SEQUENCE SET     <   SELECT pg_catalog.setval('public.orders_id_seq', 41, true);
          public          postgres    false    227            W           0    0    products_id_seq    SEQUENCE SET     =   SELECT pg_catalog.setval('public.products_id_seq', 7, true);
          public          postgres    false    216            X           0    0    users_id_seq    SEQUENCE SET     :   SELECT pg_catalog.setval('public.users_id_seq', 3, true);
          public          postgres    false    219            �           2606    16651    cart_entity cart_entity_pkey 
   CONSTRAINT     Z   ALTER TABLE ONLY public.cart_entity
    ADD CONSTRAINT cart_entity_pkey PRIMARY KEY (id);
 F   ALTER TABLE ONLY public.cart_entity DROP CONSTRAINT cart_entity_pkey;
       public            postgres    false    224            �           2606    16406    categories categories_pkey 
   CONSTRAINT     X   ALTER TABLE ONLY public.categories
    ADD CONSTRAINT categories_pkey PRIMARY KEY (id);
 D   ALTER TABLE ONLY public.categories DROP CONSTRAINT categories_pkey;
       public            postgres    false    215            �           2606    16455    offers offers_pkey 
   CONSTRAINT     P   ALTER TABLE ONLY public.offers
    ADD CONSTRAINT offers_pkey PRIMARY KEY (id);
 <   ALTER TABLE ONLY public.offers DROP CONSTRAINT offers_pkey;
       public            postgres    false    222            �           2606    16727 "   order_campaign order_campaign_pkey 
   CONSTRAINT     f   ALTER TABLE ONLY public.order_campaign
    ADD CONSTRAINT order_campaign_pkey PRIMARY KEY (order_id);
 L   ALTER TABLE ONLY public.order_campaign DROP CONSTRAINT order_campaign_pkey;
       public            postgres    false    229            �           2606    16662     order_product order_product_pkey 
   CONSTRAINT     ^   ALTER TABLE ONLY public.order_product
    ADD CONSTRAINT order_product_pkey PRIMARY KEY (id);
 J   ALTER TABLE ONLY public.order_product DROP CONSTRAINT order_product_pkey;
       public            postgres    false    226            �           2606    16668    orders orders_pkey 
   CONSTRAINT     P   ALTER TABLE ONLY public.orders
    ADD CONSTRAINT orders_pkey PRIMARY KEY (id);
 <   ALTER TABLE ONLY public.orders DROP CONSTRAINT orders_pkey;
       public            postgres    false    228            �           2606    16414    products products_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public.products
    ADD CONSTRAINT products_pkey PRIMARY KEY (id);
 @   ALTER TABLE ONLY public.products DROP CONSTRAINT products_pkey;
       public            postgres    false    217            �           2606    16435 !   users uk6dotkott2kjsp8vw4d0m25fb7 
   CONSTRAINT     ]   ALTER TABLE ONLY public.users
    ADD CONSTRAINT uk6dotkott2kjsp8vw4d0m25fb7 UNIQUE (email);
 K   ALTER TABLE ONLY public.users DROP CONSTRAINT uk6dotkott2kjsp8vw4d0m25fb7;
       public            postgres    false    220            �           2606    16670 '   cart_entity ukkjnwkuo7ko6h6nihqxg24xjx0 
   CONSTRAINT     n   ALTER TABLE ONLY public.cart_entity
    ADD CONSTRAINT ukkjnwkuo7ko6h6nihqxg24xjx0 UNIQUE (applied_offer_id);
 Q   ALTER TABLE ONLY public.cart_entity DROP CONSTRAINT ukkjnwkuo7ko6h6nihqxg24xjx0;
       public            postgres    false    224            �           2606    16674 "   orders uknthkiu7pgmnqnu86i2jyoe2v7 
   CONSTRAINT     e   ALTER TABLE ONLY public.orders
    ADD CONSTRAINT uknthkiu7pgmnqnu86i2jyoe2v7 UNIQUE (order_number);
 L   ALTER TABLE ONLY public.orders DROP CONSTRAINT uknthkiu7pgmnqnu86i2jyoe2v7;
       public            postgres    false    228            �           2606    16416 $   products uko61fmio5yukmmiqgnxf8pnavn 
   CONSTRAINT     _   ALTER TABLE ONLY public.products
    ADD CONSTRAINT uko61fmio5yukmmiqgnxf8pnavn UNIQUE (name);
 N   ALTER TABLE ONLY public.products DROP CONSTRAINT uko61fmio5yukmmiqgnxf8pnavn;
       public            postgres    false    217            �           2606    16433    users users_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.users DROP CONSTRAINT users_pkey;
       public            postgres    false    220            �           2606    16728 *   order_campaign fk2xfm06eql76ypv8sd2mqa2cuk    FK CONSTRAINT     �   ALTER TABLE ONLY public.order_campaign
    ADD CONSTRAINT fk2xfm06eql76ypv8sd2mqa2cuk FOREIGN KEY (offer_id) REFERENCES public.offers(id);
 T   ALTER TABLE ONLY public.order_campaign DROP CONSTRAINT fk2xfm06eql76ypv8sd2mqa2cuk;
       public          postgres    false    229    3225    222            �           2606    16680 '   cart_entity fk6jv58l3g9l59kkcllvuigfvf3    FK CONSTRAINT     �   ALTER TABLE ONLY public.cart_entity
    ADD CONSTRAINT fk6jv58l3g9l59kkcllvuigfvf3 FOREIGN KEY (product_id) REFERENCES public.products(id);
 Q   ALTER TABLE ONLY public.cart_entity DROP CONSTRAINT fk6jv58l3g9l59kkcllvuigfvf3;
       public          postgres    false    3217    224    217            �           2606    16733 *   order_campaign fk7s7fypw54trv6npvsoiiil7sw    FK CONSTRAINT     �   ALTER TABLE ONLY public.order_campaign
    ADD CONSTRAINT fk7s7fypw54trv6npvsoiiil7sw FOREIGN KEY (order_id) REFERENCES public.orders(id);
 T   ALTER TABLE ONLY public.order_campaign DROP CONSTRAINT fk7s7fypw54trv6npvsoiiil7sw;
       public          postgres    false    228    3233    229            �           2606    16675 '   cart_entity fkgjiher6e98brwwmo1thdc0822    FK CONSTRAINT     �   ALTER TABLE ONLY public.cart_entity
    ADD CONSTRAINT fkgjiher6e98brwwmo1thdc0822 FOREIGN KEY (applied_offer_id) REFERENCES public.offers(id);
 Q   ALTER TABLE ONLY public.cart_entity DROP CONSTRAINT fkgjiher6e98brwwmo1thdc0822;
       public          postgres    false    222    224    3225            �           2606    16436 %   user_role fkj345gk1bovqvfame88rcx7yyx    FK CONSTRAINT     �   ALTER TABLE ONLY public.user_role
    ADD CONSTRAINT fkj345gk1bovqvfame88rcx7yyx FOREIGN KEY (user_id) REFERENCES public.users(id);
 O   ALTER TABLE ONLY public.user_role DROP CONSTRAINT fkj345gk1bovqvfame88rcx7yyx;
       public          postgres    false    3223    218    220            �           2606    16695 )   order_product fkl5mnj9n0di7k1v90yxnthkc73    FK CONSTRAINT     �   ALTER TABLE ONLY public.order_product
    ADD CONSTRAINT fkl5mnj9n0di7k1v90yxnthkc73 FOREIGN KEY (order_id) REFERENCES public.orders(id);
 S   ALTER TABLE ONLY public.order_product DROP CONSTRAINT fkl5mnj9n0di7k1v90yxnthkc73;
       public          postgres    false    226    228    3233            �           2606    16700 )   order_product fko6helt0ucmegaeachjpx40xhe    FK CONSTRAINT     �   ALTER TABLE ONLY public.order_product
    ADD CONSTRAINT fko6helt0ucmegaeachjpx40xhe FOREIGN KEY (product_id) REFERENCES public.products(id);
 S   ALTER TABLE ONLY public.order_product DROP CONSTRAINT fko6helt0ucmegaeachjpx40xhe;
       public          postgres    false    3217    217    226            �           2606    16417 $   products fkog2rp4qthbtt2lfyhfo32lsw9    FK CONSTRAINT     �   ALTER TABLE ONLY public.products
    ADD CONSTRAINT fkog2rp4qthbtt2lfyhfo32lsw9 FOREIGN KEY (category_id) REFERENCES public.categories(id);
 N   ALTER TABLE ONLY public.products DROP CONSTRAINT fkog2rp4qthbtt2lfyhfo32lsw9;
       public          postgres    false    215    217    3215            F      x������ � �      =   �   x�m��
�0������J�%�ҫz�v�P��lc��Ep�B��I�  .�� ;�)0Y"����3Kq��n���o�j�j^�8O�8$SmP[B����>^���VCg�V���.�m���h�)��_޻��v;� U �^4����e���x�P�%�:�N�^��Ƙ'WcK      D   K   x�3�44PUp�,N�/�+�q��prv��w�v���2�4VH�/R0�t
���ru�p����b���� ���      K   -   x�3�4��2�4�� ˈ��D���f H��W� ��      H   |   x���!�V1�3�zI�uD�b1��.WWkDs�+G7zs7��~��چ6t�SC}����0�	ፄpDB8�0���ǹ�����<�Im�X�5�#!��dʡ��a2&�fC���#���!�      J   �  x�e�Kn#GD׭S���_UV�n0c�ގ�4`���GpPҌHj)�eV���8�������2�$����0��i�4Hv2Y�ꦞb��Ⓠ��D������������NP_����Y��1�j6�d��d�2wO���;�uH�=H*}j�W�+�K�̅��kw囹�.ɢ�Է�4�ix�e�!�����g)���DFʇ�0v|�-�g��H���M�fk�R�IJ�6��T��4d��"��������Չw��[���+�K�29E
�TTi��F���JU���G��E�G�z�ëq��"ťrB�����iЫ�3٘��%i���8"��Ǖ��t{=�0���V[�-�\%5-��6�R�g�*)�l�]��X�,�+���� ���Z8��͖m������-�)7�%� �~xTZ�f�L�bڪ�0�9~G����p�2������Nk��P0¯�]��>��G������qY�8��8�h`{��	�T)0Z*��2+�%�,>�M*<9	v;�;r7�A`��\��\)�]�CJ����|b~c?���G�T3��z����>#o�m7x�g^��5��.�Zb�����\SL���%v�.E>�}E�2K��F*o]��[�l�@�@�63�P� %����t#����]�� �z�*ß:C�r�ͿR���G���zI9�h\�X�a�qڋq+��]5#��f��@�C)�58�ó�Qs��)ZC|m��%�*E6�Cn}�K+?��'	Z*�6���4a�	B�@��&�7��2�̃=��ܕN��G��c)J3�z%��z�+�XlSZ����7C����aG�h��{�s[�i5DԼ�T�fB��Q6�E�����O,�S�؁J_���[�g��ʑG�ܵ��r�ǀ��-�*6P���;f��q��1S��S\L:u��L����#H+_aL�o��g88	v��Pbf\ 
��x���A�/z;���!�����kE�gߩ+t{��2}���x�{����/yЏ      ?     x�e��N1��S��i���.JA� �"��ˉ\�.���c� Q��}��h*=C[����q�u����w%'IGM�t��5m5�wҠ�q���TJ�NP�uH�P���]��`�4	ss�8"�kJ37��)|�����r9��2�{�ئ�ϓ\��.!���?��.�����5���g�� p�R���7�[m�:�ye��v6��"+�ұ��Y���߫h.Ҡ����mm�:d�J�U]�a���k��i<~�
�)��v�N�;Ii      @   +   x�3�tt����2�tv��t�2��u�st�bU1z\\\ "�
�      B     x�}�Mn�@ ��5���N��W��UH3BŸ�L/�u���6iBb�&��{�)�H�C��Ű�f����!i��F�*�YE���Jj$�֔K=D{
��"#�r��V�8t�E�F�<[r:�̰!^�Ӗd�
���x`�B�i�����etO�e�&�fg`���7��
V%��I�B[���ɛq�`��N#c��,<v�$�M�)�´!)*T:E��Ғ������N��2�ЌZR��
��kI,Nf�x���z_M:�Ȳ�tz�     