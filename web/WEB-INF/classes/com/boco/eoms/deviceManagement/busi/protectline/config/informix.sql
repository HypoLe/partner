

  //护线宣传协议管理
		CREATE
		TABLE informix.dm_protectline_promoAgreement (
    id                       	VARCHAR(32) NOT NULL,
    personnelId       	        VARCHAR(32) DEFAULT '',
    greatTime               	VARCHAR(32) DEFAULT '',
    areaId              	VARCHAR(32) DEFAULT '',
    auditId              	VARCHAR(32) DEFAULT '',
    itemName              	VARCHAR(32) DEFAULT '',
    repeaterSection             VARCHAR(32) DEFAULT'',
    repeaterSectionMileage      VARCHAR(32) DEFAULT '',
    agreementOldNumber       	VARCHAR(32) DEFAULT '',
    agreementNewNumber       	VARCHAR(32) DEFAULT '',
    ActualCompletion       	VARCHAR(32) DEFAULT '',
    CompletionTime       	VARCHAR(32) DEFAULT '',
    unfinishedReason       	VARCHAR(32) DEFAULT '',
    remarks                	VARCHAR(32) DEFAULT '',
    status                	VARCHAR(32) DEFAULT '',
    deleted                    	VARCHAR(32) DEFAULT '',

    PRIMARY KEY(id)
)


 CREATE TABLE informix.dm_protectline_promoagreementoperate (
    id                    	VARCHAR(32) NOT NULL,
    promoAgreementId         VARCHAR(32) DEFAULT '',
    operateTime            	VARCHAR(32) DEFAULT '',
    operateUserId            VARCHAR(32) DEFAULT '',
    operateResult              	VARCHAR(32) DEFAULT '',
    operateOpinion       	VARCHAR(32) DEFAULT '',
    operateRemark       	VARCHAR(32) DEFAULT '',
    operateStatus    	VARCHAR(32) DEFAULT '',
    operateTarget     	VARCHAR(32) DEFAULT '',
    PRIMARY KEY(id)
)







