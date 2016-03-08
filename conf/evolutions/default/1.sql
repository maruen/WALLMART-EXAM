# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table maps_info (
  id                            bigint auto_increment not null,
  map                           varchar(255),
  origin                        varchar(255),
  destiny                       varchar(255),
  distance                      varchar(255),
  constraint pk_maps_info primary key (id)
);


# --- !Downs

drop table if exists maps_info;

