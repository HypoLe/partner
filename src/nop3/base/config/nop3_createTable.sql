CREATE TABLE your_main  (
	id                     	VARCHAR(32) NOT NULL,
	status                 	VARCHAR(20) DEFAULT '',
	verticalstatus         	VARCHAR(20) DEFAULT '',
	taskownertype          	VARCHAR(20) DEFAULT '',
	taskowner              	VARCHAR(20) DEFAULT '',
	createuserid           	VARCHAR(20) DEFAULT '',
	createdate             	dateTime year to second,
	yearflag               	Integer,
	monthflag              	Integer,
	dayflag                	Integer,
	PRIMARY KEY(id)
) EXTENT SIZE 51200 NEXT SIZE 25600 LOCK MODE ROW
GO
CREATE INDEX your_main_createdate ON your_main(createdate)

CREATE TABLE pnr_feeInfo_link  (
	id                      VARCHAR(32) NOT NULL,
	mainId                  VARCHAR(50) DEFAULT '',
	taskHandler             VARCHAR(30) DEFAULT '',
	taskHandlerType         VARCHAR(30) DEFAULT '',
	taskReceiver            VARCHAR(30) DEFAULT '',
	taskReceiverType        VARCHAR(30) DEFAULT '',
	operateTime             VARCHAR(50) DEFAULT '',
	operateTimeAtom         VARCHAR(50) DEFAULT '',
	operateType             VARCHAR(30) DEFAULT '',
	myText                	VARCHAR(255) DEFAULT '',
    myTextType   	        VARCHAR(20) DEFAULT '',
	PRIMARY KEY(id)
) EXTENT SIZE 51200 NEXT SIZE 25600 LOCK MODE ROW
GO
CREATE INDEX  pnr_feeInfo_link_operateTimeAtom
	ON pnr_feeInfo_link(operateTimeAtom)