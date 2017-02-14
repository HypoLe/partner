--==============================================================
-- DBMS name:      INFORMIX SQL 9.x
-- Created on:     2011/10/18 5
--==============================================================


drop table dm_protectline_mediaPublicityApproval;

--==============================================================
-- Table: dm_protectline_advertisementApproval
--==============================================================

create table dm_protectline_mediaPublicityApproval(
  id VARCHAR(32)   not null,
  approvalUser   VARCHAR(32)   not null,
  approvalTime   VARCHAR(32)   not null,
  projectNameID VARCHAR(32)  not null,
  approvalContent VARCHAR(200)  not null,
  approvalType VARCHAR(32) not null,
  remark  VARCHAR(50),
  remark2  VARCHAR(50),
  remark3 VARCHAR(50),
  remark4 VARCHAR(50),
primary key (id)
) EXTENT SIZE 10240 NEXT SIZE 512  LOCK MODE ROW;