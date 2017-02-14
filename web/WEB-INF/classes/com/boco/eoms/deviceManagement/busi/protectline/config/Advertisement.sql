--==============================================================
-- DBMS name:      INFORMIX SQL 9.x
-- Created on:     2011/10/18
--==============================================================


drop table dm_protectline_advertisement;

--==============================================================
-- Table: dm_protectline_advertisement
--==============================================================

create table dm_protectline_advertisement(
  id VARCHAR(32)   not null,
  creatUser   VARCHAR(32)   not null,
  creatTime   VARCHAR(32)   not null,
  creatDept   VARCHAR(32)  not null,
  approvalUser VARCHAR(32) not null,
  projectName VARCHAR(50)  not null,
  advertisementArea VARCHAR(50)   not null,
  advertisementContent VARCHAR(200)  not null,
  advertisementAmount VARCHAR(50)  not null,
  finishTime VARCHAR(32)  not null,
  incompleteCause  VARCHAR(200) not null,
  remark  VARCHAR(50) not null,
  deleted VARCHAR(32) not null,
 approvalType VARCHAR(32) not null,
  modifyUser VARCHAR(32),
  modifyTime  VARCHAR(32),
  modifyDept VARCHAR(32),
  remark1   VARCHAR(50),
  remark2  VARCHAR(50),
  remark3 VARCHAR(50),
  remark4 VARCHAR(50),
primary key (id)
) EXTENT SIZE 10240 NEXT SIZE 512  LOCK MODE ROW;