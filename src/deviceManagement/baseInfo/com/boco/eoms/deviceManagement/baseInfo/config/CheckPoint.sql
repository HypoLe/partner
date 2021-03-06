/*
Script generated by Aqua Data Studio 6.5.7 on ʮ��-18-2011 09:54:03 ����
Database: partnerhlj
Schema: informix
Objects: TABLE
*/
ALTER TABLE informix.db_pole
	DROP CONSTRAINT ( u886_2955 ) 
GO
ALTER TABLE informix.db_lightjoinbox
	DROP CONSTRAINT ( u885_2953 ) 
GO
ALTER TABLE informix.db_basestation
	DROP CONSTRAINT ( u882_2948 ) 
GO
DROP INDEX informix. 886_2955
GO
DROP INDEX informix. 885_2953
GO
DROP INDEX informix. 882_2948
GO
DROP TABLE informix.db_pole
GO
DROP TABLE informix.db_lightjoinbox
GO
DROP TABLE informix.db_handwell
GO
DROP TABLE informix.db_checkpoint
GO
DROP TABLE informix.db_basestation
GO

CREATE TABLE informix.db_basestation ( 
	id              	VARCHAR(50) NOT NULL,
	basestationlevel	VARCHAR(50) DEFAULT '',
	basestationname 	VARCHAR(50) DEFAULT '' 
	)
LOCK MODE PAGE
GO
CREATE TABLE informix.db_checkpoint ( 
	id                 	VARCHAR(50) NOT NULL,
	resourcecode       	VARCHAR(50) DEFAULT '',
	resourcename       	VARCHAR(50) DEFAULT '',
	address            	VARCHAR(50) DEFAULT '',
	type               	VARCHAR(50) DEFAULT '',
	longitude          	VARCHAR(50) DEFAULT '',
	latitude           	VARCHAR(50) DEFAULT '',
	checkpointsegmentid	VARCHAR(50) DEFAULT '',
	importantfocus     	VARCHAR(50) DEFAULT '',
	ischeckpoint       	VARCHAR(50) DEFAULT '' 
	)
LOCK MODE PAGE
GO
CREATE TABLE informix.db_handwell ( 
	id                  	VARCHAR(50) NOT NULL,
	checkpointuser      	VARCHAR(50) DEFAULT '',
	loadsyslevel        	VARCHAR(50) DEFAULT '',
	loadcablesegmentname	VARCHAR(50) DEFAULT '',
	handlewellnum       	VARCHAR(50) DEFAULT '',
	pipelinename        	VARCHAR(50) DEFAULT '',
	isjointwell         	VARCHAR(50) DEFAULT '',
	iscableobligate     	VARCHAR(50) DEFAULT '',
	isimportantfocus    	VARCHAR(50) DEFAULT '' 
	)
LOCK MODE PAGE
GO
CREATE TABLE informix.db_lightjoinbox ( 
	id              	VARCHAR(50) NOT NULL,
	lightjoinboxname	VARCHAR(50) DEFAULT '' 
	)
LOCK MODE PAGE
GO
CREATE TABLE informix.db_pole ( 
	id                  	VARCHAR(50) NOT NULL,
	poletuser           	VARCHAR(50) DEFAULT '',
	loadsyslevel        	VARCHAR(50) DEFAULT '',
	loadcablesegmentname	VARCHAR(50) DEFAULT '',
	polenum             	VARCHAR(50) DEFAULT '',
	polename            	VARCHAR(50) DEFAULT '',
	isjointpole         	VARCHAR(50) DEFAULT '',
	iscableobligate     	VARCHAR(50) DEFAULT '',
	isimportantfocus    	VARCHAR(50) DEFAULT '' 
	)
LOCK MODE PAGE
GO

CREATE UNIQUE INDEX informix. 882_2948
	ON informix.db_basestation(id)
GO
CREATE UNIQUE INDEX informix. 885_2953
	ON informix.db_lightjoinbox(id)
GO
CREATE UNIQUE INDEX informix. 886_2955
	ON informix.db_pole(id)
GO
ALTER TABLE informix.db_basestation
	ADD CONSTRAINT ( PRIMARY KEY (id) CONSTRAINT u882_2948 )
GO
ALTER TABLE informix.db_lightjoinbox
	ADD CONSTRAINT ( PRIMARY KEY (id) CONSTRAINT u885_2953 )
GO
ALTER TABLE informix.db_pole
	ADD CONSTRAINT ( PRIMARY KEY (id) CONSTRAINT u886_2955 )
GO
