--==============================================================
-- DBMS name:     informix
-- Created on:     2012/3
--==============================================================


drop table aggregation

--==============================================================
-- Table: aggregationset
--==============================================================
create table aggregation(
  id VARCHAR(32)   not null,
  creatUser   VARCHAR(32),
  creatTime  DATETIME year to second,
  moduleUrl VARCHAR(200),
  moduleName VARCHAR(50),
  deleted VARCHAR(32),
  content   varchar(200),
  accessory  varchar(50),
  photo  varchar(50),
  remark  VARCHAR(50),
  remark2  VARCHAR(50),
  remark3 VARCHAR(50),
  remark4 VARCHAR(50),
  primary key (id)
)