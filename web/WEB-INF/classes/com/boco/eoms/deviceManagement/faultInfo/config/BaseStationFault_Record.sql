--==============================================================
-- DBMS name:      INFORMIX SQL 9.x
-- Created on:     2011/9/6 17:40:15
--==============================================================


drop table db_faultInfo;

--==============================================================
-- Table: BaseStationFault_R
--==============================================================

create table db_faultInfo  (
  id                   VARCHAR(50)                     not null,
  reportPerson         VARCHAR(50)                     not null,
  deptId               VARCHAR(50)                     not null,
  roleId               VARCHAR(50)                     not null,
  contactNumber        VARCHAR(50)                     not null,
  reportTime           VARCHAR(50)                     not null,
  bsfrDateTime         VARCHAR(50)                     not null,
  stagnationPoint      VARCHAR(50)                     not null,
  baseStationName      VARCHAR(50)                     not null,
  maintainLevel        VARCHAR(50)                     not null,
  sheetId              VARCHAR(50)                     not null,
  sheetType            VARCHAR(50)                     not null,
  sheetTaskInfor       VARCHAR(50),
  sheetStartTime       VARCHAR(50)                     not null,
  sheetConfirmTime     VARCHAR(50)                     not null,
  sheeEndTime          VARCHAR(50)                     not null,
  takeTime             VARCHAR(50)                     not null,
  sheetReceivePerson   VARCHAR(50)                     not null,
  sheetEndPerson       VARCHAR(50)                     not null,
  sheetStartType       VARCHAR(50)                     not null,
  processingResults    VARCHAR(50),
  faultType            VARCHAR(50)                     not null,
  isExit               VARCHAR(50)                     not null,
  deviceAdjust         VARCHAR(50),
  residualProblem      VARCHAR(200),
  other                VARCHAR(200),
  isDeleted            VARCHAR(50),
  deleteTime           VARCHAR(50),
  remark1              VARCHAR(50),
  remark2              VARCHAR(50),
  remark3              VARCHAR(50),
  remark4              VARCHAR(50),
primary key (id)
      constraint PK_BASESTATIONFAUL
);