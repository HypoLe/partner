--==============================================================
-- DBMS name:      INFORMIX SQL 9.x
-- Created on:     2011/10/21
--==============================================================


drop table dm_protectline_mediaPublicity;

--==============================================================
-- Table: dm_protectline_mediaPublicity
--==============================================================

create table dm_protectline_mediaPublicity(
  id VARCHAR(32)   not null,
  creatUser   VARCHAR(32)   not null,
  creatTime   VARCHAR(32)   not null,
  creatDept   VARCHAR(32)  not null,
  approvalUser VARCHAR(32) not null,
  mediaName VARCHAR(100)  not null,
  mediaContent VARCHAR(200)  not null,
  mediaStyle VARCHAR(100)  not null,
  mediaTime VARCHAR(32)  not null,
  mediaAssess  VARCHAR(100)  not null,
  deleted VARCHAR(32) not null,
 approvalType VARCHAR(32) not null,
  modifyUser VARCHAR(32),
  modifyTime  VARCHAR(32),
  modifyDept VARCHAR(32),
  remark   VARCHAR(50) ,
  remark1   VARCHAR(50),
  remark2  VARCHAR(50),
  remark3 VARCHAR(50),
  remark4 VARCHAR(50),
primary key (id)
) EXTENT SIZE 10240 NEXT SIZE 512  LOCK MODE ROW;