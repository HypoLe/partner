//按照需求修改字段长度，类型

  //代维人员基础信息
		CREATE
		TABLE feeApplicationMain 
		(
			id VARCHAR(30) NOT NULL,
			
			feeApplicationID VARCHAR(30) DEFAULT '',
			feeApplicationName VARCHAR(30) DEFAULT '',
			feeApplicationUser VARCHAR(30) DEFAULT '',
			feeApplicationCall VARCHAR(30) DEFAULT '',
			feeApplicationDept VARCHAR(30) DEFAULT '',
	        feeApplicationArea VARCHAR(30) DEFAULT '',
	        feeApplicationCity VARCHAR(30) DEFAULT '',
	        feeApplicationGreatTime VARCHAR(30) DEFAULT '',
	        feeApplicationType VARCHAR(30) DEFAULT '',
			feeApplicationMoney VARCHAR(30) DEFAULT '',
			feeApplicationDiscribe VARCHAR(30) DEFAULT '',
			feeApplicationRemark  VARCHAR(30) DEFAULT '',
			feeApplicationAccessory VARCHAR(30) DEFAULT '',
			feeApplicationStatus VARCHAR(30) DEFAULT '',
			deleted VARCHAR(30) DEFAULT '',
			deleteTime VARCHAR(30) DEFAULT '',
			
		
			PRIMARY KEY(id) );
			
			
CREATE
		TABLE feeApplicationLink 
		(
			id VARCHAR(30) NOT NULL,	
			feeApplicationID VARCHAR(30) DEFAULT '',
			operateUser VARCHAR(30) DEFAULT '',
			operateDept VARCHAR(30) DEFAULT '',
			operateCall VARCHAR(30) DEFAULT '',
			operateRole VARCHAR(30) DEFAULT '',
	        operateTime VARCHAR(30) DEFAULT '',
	        operateAccessory VARCHAR(30) DEFAULT '',
	        operateTarget  VARCHAR(30) DEFAULT '',
	        operateOpinion VARCHAR(30) DEFAULT '',
			operateRemark VARCHAR(30) DEFAULT '',
			operateResult VARCHAR(30) DEFAULT '',
		
			PRIMARY KEY(id) );
	