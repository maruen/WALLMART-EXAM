# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table map (
  id                            varchar(255) not null,
  name                          varchar(255),
  origin                        varchar(255),
  destiny                       varchar(255),
  distance                      varchar(255),
  constraint pk_map primary key (id)
);


# --- !Downs

drop table if exists map;

