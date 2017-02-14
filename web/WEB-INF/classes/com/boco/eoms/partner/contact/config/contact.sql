/*  联系函实例 */ 
CREATE TABLE contact_main ( 
    id               	VARCHAR(32) NOT NULL,
    code      	VARCHAR(255),
    publisherid      	VARCHAR(32),
    publishername    	VARCHAR(32),
    publisherdeptid  	VARCHAR(32),
    publisherdeptname	VARCHAR(32),
    subject          	VARCHAR(100),
    publishtime      	DATETIME YEAR to SECOND,
    deathtime            	DATETIME YEAR to SECOND,
    content   	LVARCHAR(2000),
    file             	VARCHAR(100),
    type             	INTEGER,
    isSendSMS INTEGER,
    isUrgent INTEGER,
    PRIMARY KEY(id)
	ENABLED
)
LOCK MODE ROW
GO

/* TASK */
create table contact_task 
(
   id                   VARCHAR(32)                    not null,
   mainId               VARCHAR(32),
   taskOwnerId            VARCHAR(32),
     taskOwnerName            VARCHAR(32),
   taskOwnerType        VARCHAR(32),
   taskState            INTEGER,
   taskName             VARCHAR(32),
   taskType             INTEGER,
   operationTime  	DATETIME YEAR to SECOND,
   operationContent LVARCHAR(2000),
   PRIMARY KEY(id)
)
LOCK MODE ROW
GO