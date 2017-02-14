/****************代维资质信息表*********************/
create table partner_dwinfo  (
   id                 VARCHAR(32)                     not null,
   groups              VARCHAR(100),
   professional       VARCHAR(255),
   skillLevel         VARCHAR(20),
   duty               VARCHAR(20),
   accountNo          VARCHAR(255),
   qualificationList  VARCHAR(200),
   memo               VARCHAR(200),
   lastEditTime       VARCHAR(20),
   lastEditerId       VARCHAR(32),
   lastEditerName     VARCHAR(20),
   addUser            VARCHAR(32),
   addDept            VARCHAR(32),
   addTime            VARCHAR(32),
   isDelete           VARCHAR(10),
   workerId           VARCHAR(32),
   workerName           VARCHAR(32),
    PRIMARY KEY(id)
)
LOCK MODE ROW
GO
/*****************人员证书信息表********************/
CREATE TABLE partner_certificate ( 
    id            	VARCHAR(32) NOT NULL,
    workerid      	VARCHAR(32),
    type          	VARCHAR(20),
    level1        	VARCHAR(10),
    issueorg      	VARCHAR(50),
    issuetime     	VARCHAR(20),
    validity      	VARCHAR(10),
    codeno        	VARCHAR(50),
    imagepath     	VARCHAR(100),
    memo          	VARCHAR(200),
    lastedittime  	VARCHAR(20),
    lastediterid  	VARCHAR(32),
    lasteditername	VARCHAR(20),
    adduser       	VARCHAR(32),
    adddept       	VARCHAR(32),
    addtime       	VARCHAR(32),
    isdelete      	VARCHAR(10),
    workername  VARCHAR(32),
    PRIMARY KEY(id)
)
LOCK MODE ROW
GO
/*********************人员奖励信息表*************/
create table panter_reward  (
   workerId           VARCHAR(32),
   id                   VARCHAR(32)                     not null,
   reward             VARCHAR(50),
   memo               VARCHAR(200),
   lastEditTime       VARCHAR(20),
   lastEditerId       VARCHAR(32),
   lastEditerName     VARCHAR(20),
   addUser            VARCHAR(32),
   addDept            VARCHAR(32),
   addTime            VARCHAR(32),
   isDelete           VARCHAR(10),
   workername  VARCHAR(32),
    PRIMARY KEY(id)
)
LOCK MODE ROW
GO
/**********************人员培训经历信息表*******************/
create table panter_pxexperience  (
   id                 VARCHAR(32)                     not null,
   workerId           VARCHAR(32),
   startTime          VARCHAR(20),
   endTime            VARCHAR(20),
   content            VARCHAR(200),
   score              VARCHAR(10),
   memo               VARCHAR(200),
   lastEditTime       VARCHAR(20),
   lastEditerId       VARCHAR(32),
   lastEditerName     VARCHAR(20),
   addUser            VARCHAR(32),
   addDept            VARCHAR(32),
   addTime            VARCHAR(32),
   isDelete           VARCHAR(10),
   workerName         VARCHAR(32),
    PRIMARY KEY(id)
)
LOCK MODE ROW
GO

/*************************学习经历信息表******************************/
create table panter_studyexperience  (
   id                 VARCHAR(32)                     not null,
   workerId           VARCHAR(32),
   inTime             VARCHAR(20),
   leaveTime          VARCHAR(20),
   university         VARCHAR(50),
   professional       VARCHAR(50),
   degree             VARCHAR(20),
   code               VARCHAR(50),
   imagePath          VARCHAR(100),
   memo               VARCHAR(200),
   lastEditTime       VARCHAR(20),
   lastEditerId       VARCHAR(32),
   lastEditerName     VARCHAR(20),
   addUser            VARCHAR(32),
   addDept            VARCHAR(32),
   addTime            VARCHAR(32),
   isDelete           VARCHAR(10),
   workerName         VARCHAR(32),
    PRIMARY KEY(id)
)
LOCK MODE ROW
GO

/*************************代维资质管理  审批信息表***********************/
create table partner_examinrstate  (
   id                   VARCHAR(32)                     not null,
   infoId             VARCHAR(32),
   applicatTime       VARCHAR(20),
   applicaterId       VARCHAR(32),
   applicaterName     VARCHAR(20),
   examinerId         VARCHAR(32),
   examinerName       VARCHAR(20),
   state              VARCHAR(10),
   opinion            VARCHAR(100),
   examineTime        VARCHAR(20),
   excelPath          VARCHAR(100),
   operrateType       VARCHAR(10),
   examineTyep        VARCHAR(50),
   memo               VARCHAR(200),
    PRIMARY KEY(id)
)
LOCK MODE ROW
GO
/***********************代维资质管理 审批历史记录表***********************/
create table partner_examinhistory  (
   id                 VARCHAR(32)                     not null,
   examinID           VARCHAR(32),
   applicatId         VARCHAR(32),
   applicatName       VARCHAR(20),
   examinerId         VARCHAR(32),
   examinerName       VARCHAR(20),
   state              VARCHAR(10),
   operatType         VARCHAR(10),
   opinion            VARCHAR(100),
   examineType        VARCHAR(50),
    PRIMARY KEY(id)
)
LOCK MODE ROW
GO



