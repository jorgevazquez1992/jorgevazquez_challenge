drop table if exists "cliente" CASCADE;
drop table if exists "pedido_detalle" CASCADE;
drop table if exists "pedido_cabecera" CASCADE;
drop table if exists "producto_por_tienda" CASCADE;
drop table if exists "producto" CASCADE;
drop table if exists "tienda" CASCADE;

create sequence IF NOT EXISTS hibernate_sequence start with 4 increment by 1;

CREATE TABLE "cliente" (
    "id_cliente" bigint not null, 
    "identificacion" varchar(13) not null,
    "nombre" varchar(50) not null,
    "url_foto" varchar(50) not null,
     primary key ("id_cliente")
);

CREATE TABLE "tienda" (
    "id_tienda" bigint not null, 
    "nombre" varchar(100) not null,
     primary key ("id_tienda")
);

CREATE TABLE "producto" (
    "id_producto" bigint not null, 
    "codigo" varchar(10) not null,
    "nombre" varchar(10) not null,
    "precio" float not null,
    "stock" int not null,
     primary key ("id_producto")
);

CREATE TABLE "producto_por_tienda" (
    "id_producto_por_tienda" bigint not null, 
    "id_producto" bigint not null,
    "id_tienda" bigint not null,  
     primary key ("id_producto_por_tienda")
);

alter table "producto_por_tienda" add constraint "fk_ppt_p" foreign key ("id_producto") references "producto";
alter table "producto_por_tienda" add constraint "fk_ppt_t" foreign key ("id_tienda") references "tienda";

CREATE TABLE "pedido_cabecera" (
    "id_pedido_cabecera" bigint not null, 
    "id_cliente" bigint not null,
    "fecha_pedido" Date not null,  
     primary key ("id_pedido_cabecera")
);

CREATE TABLE "pedido_detalle" (
	"id_pedido_detalle" bigint not null,
	"id_pedido_cabecera" bigint not null,
	"id_producto_por_tienda" bigint not null,
	"cantidad" int not null,
	 primary key ("id_pedido_detalle")
);

alter table "pedido_detalle" add constraint "fk_pd_pc" foreign key ("id_pedido_cabecera") references "pedido_cabecera";
alter table "pedido_detalle" add constraint "fk_pd_ppt" foreign key ("id_producto_por_tienda") references "producto_por_tienda";

insert into "tienda" ("id_tienda", "nombre") values (1,'Tienda 1');
insert into "tienda" ("id_tienda", "nombre") values (2,'Tienda 2');
insert into "tienda" ("id_tienda", "nombre") values (3,'Tienda 3');
insert into "tienda" ("id_tienda", "nombre") values (4,'Tienda 4');