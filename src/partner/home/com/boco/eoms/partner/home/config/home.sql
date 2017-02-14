/*  公告实例 */ 
CREATE TABLE publicnotice_main ( 
    id               	VARCHAR(32) NOT NULL,
    publisherid      	VARCHAR(32),
    publishername    	VARCHAR(32),
    publisherdeptid  	VARCHAR(32),
    publisherdeptname	VARCHAR(32),
    subject          	VARCHAR(100),
    publishtime      	DATETIME YEAR to SECOND,
    publishcontent   	LVARCHAR(2000),
    valid            	DATETIME YEAR to SECOND,
    isaudit          	INTEGER,
    publisharea      	VARCHAR(255),
    file             	VARCHAR(100),
    type             	INTEGER,
    PRIMARY KEY(id)
	ENABLED
)
LOCK MODE ROW
GO

/* TASK */
create table publicnotice_task 
(
   id                   VARCHAR(32)                    not null,
   mainId               VARCHAR(32),
   taskOwnerId            VARCHAR(32),
   taskOwnerType        VARCHAR(32),
   taskState            INTEGER,
   taskName             VARCHAR(32),
   taskType             INTEGER,
   PRIMARY KEY(id)
)
LOCK MODE ROW
GO

/* LINK */

create table publicnotice_link 
(
   id                   VARCHAR(32)                    not null,
   mainId               VARCHAR(32),
   taskId               VARCHAR(32),
   operateType          integer ,
   auditResult          integer,
   auditaDvice          VARCHAR(255),
   operateTime         VARCHAR(32),
   PRIMARY KEY(id)
)
LOCK MODE ROW
GO
/*资料库 管理*/
CREATE TABLE partner_materialLibrary ( 
    id               	VARCHAR(32) NOT NULL,
    publisherid      	VARCHAR(32),
    publishername    	VARCHAR(32),
    publisherdeptid  	VARCHAR(32),
    publisherdeptname	VARCHAR(32),
    subject          	VARCHAR(100),
    publishtime      	DATETIME YEAR to SECOND,
    outline   	VARCHAR(255),
    keyWord varchar(32),
    specialty      	VARCHAR(32),
    file             	VARCHAR(100),
    scopeIds             	VARCHAR(32),
    scopeNames             	VARCHAR(32),
    PRIMARY KEY(id)
	ENABLED
)
LOCK MODE ROW
GO