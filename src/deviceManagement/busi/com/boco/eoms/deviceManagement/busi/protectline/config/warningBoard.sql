

  //警示牌、宣传牌管理
	

             CREATE TABLE informix.dm_protectline_warningboard (
    id                    	VARCHAR(32) NOT NULL,
    personnelid           	VARCHAR(32) DEFAULT '',
    greattime             	VARCHAR(32) DEFAULT '',
    areaid                	VARCHAR(32) DEFAULT '',
    auditid               	VARCHAR(32) DEFAULT '',
    itemname              	VARCHAR(32) DEFAULT '',
    repeatersection       	VARCHAR(32) DEFAULT '',
    repeatersectionmileage	VARCHAR(32) DEFAULT '',
    warningboardoldnumber    	VARCHAR(32) DEFAULT '',
    warningboardnewnumber    	VARCHAR(32) DEFAULT '',
    actualcompletion      	VARCHAR(32) DEFAULT '',
    completiontime        	VARCHAR(32) DEFAULT '',
    unfinishedreason      	VARCHAR(32) DEFAULT '',
    assessresult         	VARCHAR(32) DEFAULT '',
    remarks               	VARCHAR(32) DEFAULT '',
    deleted               	VARCHAR(32) DEFAULT '',
    status                	VARCHAR(32) DEFAULT '',
    PRIMARY KEY(id)
)


   CREATE TABLE informix.dm_protectline_warningboardoperate (
    id              	VARCHAR(32) NOT NULL,
    warningboardid	VARCHAR(32) DEFAULT '',
    operatetime     	VARCHAR(32) DEFAULT '',
    operateuserid   	VARCHAR(32) DEFAULT '',
    operateresult   	VARCHAR(32) DEFAULT '',
    operateopinion  	VARCHAR(32) DEFAULT '',
    operateremark   	VARCHAR(32) DEFAULT '',
    operatestatus   	VARCHAR(32) DEFAULT '',
    operatetarget   	VARCHAR(32) DEFAULT '',
    PRIMARY KEY(id)
)