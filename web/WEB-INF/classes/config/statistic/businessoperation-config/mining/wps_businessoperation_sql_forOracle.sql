select userid,deptid,password from taw_system_user

select * from faultsheet_main

select * from faultsheet_link

select * from commonfault_task

--businessoperation_main
MAINPRODUCTTYPE    VARCHAR2(500) Y                         
MAINPRODUCTNAME    VARCHAR2(500) Y                         
MAINPRODUCTCODE    VARCHAR2(500) Y                         
MAINISGF           VARCHAR2(500) Y                         
MAINDESIGNSHEETID  VARCHAR2(500) Y                         
MAINSHEETID        VARCHAR2(500) Y                         
MAINTASK           VARCHAR2(500) Y                         
MAINISGC           VARCHAR2(500) Y                         
                        
MAINEXTENDACC      VARCHAR2(500) Y                         
MAINISSUCCESS      VARCHAR2(20)  Y                         
SENDYEAR           INTEGER       Y        0                
SENDMONTH          INTEGER       Y        0                
SENDDAY            INTEGER       Y        0 

--businessoperation_link
                        
LINKOPERSTARTTIME    DATE          Y                         
LINKOPERENDTIME      DATE          Y                         
                         
LINKTESTRESULT       VARCHAR2(500) Y                         
LINKNETTYPE          VARCHAR2(500) Y                         
      
LINKISSUCCESS        VARCHAR2(50)  Y                         
                        
SENDYEAR             INTEGER       Y        0                
SENDMONTH            INTEGER       Y        0                
SENDDAY              INTEGER       Y        0                
OPERATERCONTACT      VARCHAR2(500) Y 

--businessoperation.task


--已处理
select 
----est_stat---
2 est_data_status,
srole.roleid send_bigrole_id,
case when task.operatetype='subrole' then orole.roleid else to_number(null) end operate_bigrole_id,
sd.linkid send_dept_level,
od.linkid operate_dept_level,
--common main
main.id MAINID,main.SHEETID,main.TITLE,main.SHEETACCEPTLIMIT,main.SHEETCOMPLETELIMIT,main.SENDTIME,main.SENDUSERID,main.SENDDEPTID,
main.SENDROLEID,main.SENDCONTACT,main.STATUS,main.HOLDSTATISFIED,main.ENDTIME,main.ENDRESULT,main.ENDUSERID,main.ENDDEPTID,main.ENDROLEID,
main.DELETED,main.PIID,main.PARENTSHEETNAME,main.PARENTSHEETID,main.SHEETTEMPLATENAME,main.SHEETACCESSORIES,
main.TODEPTID,main.CANCELREASON,
------common main backup----
--main.sendorgtype sendorgtype,send_year,send_month,send_day,send_week,send_bigrole_id
--common link
link.ID linkid,
task.ACCEPTTIMELIMIT NODEACCEPTLIMIT,
task.COMPLETETIMELIMIT NODECOMPLETELIMIT,
link.OPERATETYPE,
link.OPERATETIME,
link.OPERATEUSERID,
link.OPERATEDEPTID,
link.OPERATEROLEID,
link.TOORGTYPE,
link.TOORGUSERID,
link.TOORGDEPTID,
link.TOORGROLEID,
link.ACCEPTFLAG,
link.COMPLETEFLAG,
link.PRELINKID,
link.PARENTLINKID,
link.FIRSTLINKID,
link.AIID,
link.ACTIVETEMPLATEID,
link.NODETEMPLATENAME,
link.NODEACCESSORIES,
link.OPERATEORGTYPE,
  ------common link backup----
--operate_year,operate_month,operate_day,operate_week
--common task
task.SUBTASKFLAG,
task.PARENTTASKID,
task.taskstatus,
task.operatetype taskoperatetype
--bussines main
,main.MAINPRODUCTTYPE                        
,main.MAINPRODUCTNAME                        
,main.MAINPRODUCTCODE                         
,main.MAINISGF                         
,main.MAINDESIGNSHEETID                         
,main.MAINSHEETID                         
,main.MAINTASK                         
,main.MAINISGC                         
                        
,main.MAINEXTENDACC                         
,main.MAINISSUCCESS 
,main.mainifrecord                        
,main.SENDYEAR                
,main.SENDMONTH                
,main.SENDDAY 
--bussines link
                         
,link.LINKOPERSTARTTIME                         
,link.LINKOPERENDTIME                         
                         
,link.LINKTESTRESULT                         
,link.LINKNETTYPE                         
                                          
,link.LINKISSUCCESS                         
                         
,link.operateyear                
,link.operatemonth                
,link.operateday   

from businessoperation_main main

join businessoperation_link link on main.id=link.mainid and main.templateflag=0 and link.TEMPLATEFLAG=0
left join businessoperation_task task on link.aiid=task.id
left join taw_system_sub_role orole on link.operateroleid=orole.id
left join taw_system_sub_role srole on main.sendroleid=srole.id
join taw_system_dept sd on main.senddeptid=sd.deptid
join taw_system_dept od on link.operatedeptid=od.deptid


--select * from commonfault_link link , commonfault_task task where link.aiid=task.id

--select * from commonfault_task


--未处理
select 
----est_stat---
case when task.taskstatus=2 then 0 when task.taskstatus=8 then 1 end est_data_status,
srole.roleid send_bigrole_id,
case when task.operatetype='subrole' then orole.roleid else to_number(null) end operate_bigrole_id,
sd.linkid send_dept_level,
od.linkid operate_dept_level,
--common main
main.id MAINID,main.SHEETID,main.TITLE,main.SHEETACCEPTLIMIT,main.SHEETCOMPLETELIMIT,main.SENDTIME,main.SENDUSERID,main.SENDDEPTID,
main.SENDROLEID,main.SENDCONTACT,main.STATUS,main.HOLDSTATISFIED,main.ENDTIME,main.ENDRESULT,main.ENDUSERID,main.ENDDEPTID,main.ENDROLEID,
main.DELETED,main.PIID,main.PARENTSHEETNAME,main.PARENTSHEETID,main.SHEETTEMPLATENAME,main.SHEETACCESSORIES,
main.TODEPTID,main.CANCELREASON,
------user for find template---
--main.TEMPLATEFLAG
------common main backup----
--main.sendorgtype sendorgtype,send_year,send_month,send_day,send_week,send_bigrole_id
--common link
to_char(null) linkid,
task.ACCEPTTIMELIMIT NODEACCEPTLIMIT,
task.COMPLETETIMELIMIT NODECOMPLETELIMIT,
to_number(null) OPERATETYPE,
to_date(null) OPERATETIME,
case when task.taskowner!=task.operateroleid then task.taskowner else to_char(null) end OPERATEUSERID,
case when task.taskowner!=task.operateroleid then ud.deptid else u.deptid end OPERATEDEPTID,
task.operateroleid OPERATEROLEID,
to_number(null) TOORGTYPE,
to_char(null) TOORGUSERID,
to_char(null)  TOORGDEPTID,
to_char(null) TOORGROLEID,
case when to_date(to_char(task.ACCEPTTIMELIMIT,'YYYY-MM-DD HH24:MI:SS'),'YYYY-MM-DD HH24:MI:SS')>sysdate then 1 else 2 end ACCEPTFLAG,
case when to_date(to_char(task.COMPLETETIMELIMIT,'YYYY-MM-DD HH24:MI:SS'),'YYYY-MM-DD HH24:MI:SS')>sysdate then 1 else 2 end COMPLETEFLAG,
task.PRELINKID,
to_char(null) PARENTLINKID,
to_char(null) FIRSTLINKID,
task.id AIID,
task.taskname ACTIVETEMPLATEID,
to_char(null) NODETEMPLATENAME,
to_char(null) NODEACCESSORIES,
to_number(null) OPERATEORGTYPE,
  ------common link backup----
--operate_year,operate_month,operate_day,operate_week,
--common task
task.SUBTASKFLAG,
task.PARENTTASKID,
task.taskstatus,
task.operatetype taskoperatetype
--bussines main
,main.MAINPRODUCTTYPE                        
,main.MAINPRODUCTNAME                        
,main.MAINPRODUCTCODE                         
,main.MAINISGF                         
,main.MAINDESIGNSHEETID                         
,main.MAINSHEETID                         
,main.MAINTASK                         
,main.MAINISGC                         
                        
,main.MAINEXTENDACC                         
,main.MAINISSUCCESS    
,main.mainifrecord                       
,main.SENDYEAR                
,main.SENDMONTH                
,main.SENDDAY
--bussines link
                         
,to_date(null) LINKOPERSTARTTIME                         
,to_date(null) LINKOPERENDTIME                         
                         
,to_char(null) LINKTESTRESULT                         
,to_char(null) LINKNETTYPE                                                                
                        
,to_char(null) LINKISSUCCESS                         
                         
,to_number(null) operateyear             
,to_number(null) operatemonth               
,to_number(null) operateday     

from businessoperation_main main

join businessoperation_task task on main.id=task.sheetkey and task.taskstatus!=5 and main.templateflag=0
left join taw_system_user ud on task.taskowner!=task.operateroleid and task.taskowner=ud.userid
left join taw_system_sub_role orole on orole.id=task.operateroleid
left join taw_system_sub_role srole on srole.id=main.sendroleid
left join taw_system_userrefrole refr on refr.subroleid = task.operateroleid
left join taw_system_user u on u.userid = refr.userid
join taw_system_dept sd on main.senddeptid=sd.deptid
join taw_system_dept od on u.deptid=od.deptid

select case when to_date('2008-09-16 06:41:53','YYYY-MM-DD HH24:MI:SS')-sysdate>0 then 1 else 0 end from dual

select * from common

create view v_test
select TOORGROLEID,ACCEPTFLAG,ACCEPTTIME from commonfault_link

TOORGROLEID               VARCHAR2(510)  Y                         
ACCEPTFLAG                NUMBER(10)     Y                         
ACCEPTTIME                TIMESTAMP(6)

select 1 from dual

select to_char(null) from dual

select null from dual

select to_date(null) from dual

create table test1 as select null a from dual

drop table test1


------------------------------VIEW----------------------------------------
select * from v_businessoperation
select distinct * from v_businessoperation

--drop view v_businessoperation
create or replace view v_businessoperation(
----est_stat---
est_data_status,
send_bigrole_id,
operate_bigrole_id,
send_dept_level,
operate_dept_level,
--common main
MAINID,SHEETID,TITLE,SHEETACCEPTLIMIT,SHEETCOMPLETELIMIT,SENDTIME,SENDUSERID,SENDDEPTID,
SENDROLEID,SENDCONTACT,STATUS,HOLDSTATISFIED,ENDTIME,ENDRESULT,ENDUSERID,ENDDEPTID,ENDROLEID,
DELETED,PIID,PARENTSHEETNAME,PARENTSHEETID,SHEETTEMPLATENAME,SHEETACCESSORIES,
TODEPTID,CANCELREASON,
------common main backup----
--main.sendorgtype sendorgtype,send_year,send_month,send_day,send_week,send_bigrole_id
--common link
linkid,
NODEACCEPTLIMIT,
NODECOMPLETELIMIT,
OPERATETYPE,
OPERATETIME,
OPERATEUSERID,
OPERATEDEPTID,
OPERATEROLEID,
TOORGTYPE,
TOORGUSERID,
TOORGDEPTID,
TOORGROLEID,
ACCEPTFLAG,
COMPLETEFLAG,
PRELINKID,
PARENTLINKID,
FIRSTLINKID,
AIID,
ACTIVETEMPLATEID,
NODETEMPLATENAME,
NODEACCESSORIES,
OPERATEORGTYPE,
  ------common link backup----
--operate_year,operate_month,operate_day,operate_week,
--common task
SUBTASKFLAG,
PARENTTASKID,
taskstatus,
taskoperatetype
--bussines main
,MAINPRODUCTTYPE                        
,MAINPRODUCTNAME                        
,MAINPRODUCTCODE                         
,MAINISGF                         
,MAINDESIGNSHEETID                         
,MAINSHEETID                         
,MAINTASK                         
,MAINISGC                                    
,MAINEXTENDACC                         
,MAINISSUCCESS
,mainifrecord                         
,SENDYEAR                
,SENDMONTH                
,SENDDAY
--bussines link
                         
,LINKOPERSTARTTIME                         
,LINKOPERENDTIME                         
                         
,LINKTESTRESULT                         
,LINKNETTYPE                                                 
,LINKISSUCCESS                                               
,operateyear              
,operatemonth             
,operateday       
) as 
(
--已处理
select 
----est_stat---
2 est_data_status,
srole.roleid send_bigrole_id,
case when task.operatetype='subrole' then orole.roleid else to_number(null) end operate_bigrole_id,
sd.linkid send_dept_level,
od.linkid operate_dept_level,
--common main
main.id MAINID,main.SHEETID,main.TITLE,main.SHEETACCEPTLIMIT,main.SHEETCOMPLETELIMIT,main.SENDTIME,main.SENDUSERID,main.SENDDEPTID,
main.SENDROLEID,main.SENDCONTACT,main.STATUS,main.HOLDSTATISFIED,main.ENDTIME,main.ENDRESULT,main.ENDUSERID,main.ENDDEPTID,main.ENDROLEID,
main.DELETED,main.PIID,main.PARENTSHEETNAME,main.PARENTSHEETID,main.SHEETTEMPLATENAME,main.SHEETACCESSORIES,
main.TODEPTID,main.CANCELREASON,
------common main backup----
--main.sendorgtype sendorgtype,send_year,send_month,send_day,send_week,send_bigrole_id
--common link
link.ID linkid,
task.ACCEPTTIMELIMIT NODEACCEPTLIMIT,
task.COMPLETETIMELIMIT NODECOMPLETELIMIT,
link.OPERATETYPE,
link.OPERATETIME,
link.OPERATEUSERID,
link.OPERATEDEPTID,
link.OPERATEROLEID,
link.TOORGTYPE,
link.TOORGUSERID,
link.TOORGDEPTID,
link.TOORGROLEID,
link.ACCEPTFLAG,
link.COMPLETEFLAG,
link.PRELINKID,
link.PARENTLINKID,
link.FIRSTLINKID,
link.AIID,
link.ACTIVETEMPLATEID,
link.NODETEMPLATENAME,
link.NODEACCESSORIES,
link.OPERATEORGTYPE,
  ------common link backup----
--operate_year,operate_month,operate_day,operate_week
--common task
task.SUBTASKFLAG,
task.PARENTTASKID,
task.taskstatus,
task.operatetype taskoperatetype
--bussines main
,main.MAINPRODUCTTYPE                        
,main.MAINPRODUCTNAME                        
,main.MAINPRODUCTCODE                         
,main.MAINISGF                         
,main.MAINDESIGNSHEETID                         
,main.MAINSHEETID                         
,main.MAINTASK                         
,main.MAINISGC                         
                        
,main.MAINEXTENDACC                         
,main.MAINISSUCCESS 
,main.mainifrecord                        
,main.SENDYEAR                
,main.SENDMONTH                
,main.SENDDAY 
--bussines link
                         
,link.LINKOPERSTARTTIME                         
,link.LINKOPERENDTIME                         
                         
,link.LINKTESTRESULT                         
,link.LINKNETTYPE                         
                                          
,link.LINKISSUCCESS                         
                         
,link.operateyear                
,link.operatemonth                
,link.operateday   

from businessoperation_main main

join businessoperation_link link on main.id=link.mainid and main.templateflag=0 and link.TEMPLATEFLAG=0
left join businessoperation_task task on link.aiid=task.id
left join taw_system_sub_role orole on link.operateroleid=orole.id
left join taw_system_sub_role srole on main.sendroleid=srole.id
join taw_system_dept sd on main.senddeptid=sd.deptid
join taw_system_dept od on link.operatedeptid=od.deptid
)
union all
(
--未处理
select 
----est_stat---
case when task.taskstatus=2 then 0 when task.taskstatus=8 then 1 end est_data_status,
srole.roleid send_bigrole_id,
case when task.operatetype='subrole' then orole.roleid else to_number(null) end operate_bigrole_id,
sd.linkid send_dept_level,
od.linkid operate_dept_level,
--common main
main.id MAINID,main.SHEETID,main.TITLE,main.SHEETACCEPTLIMIT,main.SHEETCOMPLETELIMIT,main.SENDTIME,main.SENDUSERID,main.SENDDEPTID,
main.SENDROLEID,main.SENDCONTACT,main.STATUS,main.HOLDSTATISFIED,main.ENDTIME,main.ENDRESULT,main.ENDUSERID,main.ENDDEPTID,main.ENDROLEID,
main.DELETED,main.PIID,main.PARENTSHEETNAME,main.PARENTSHEETID,main.SHEETTEMPLATENAME,main.SHEETACCESSORIES,
main.TODEPTID,main.CANCELREASON,
------user for find template---
--main.TEMPLATEFLAG
------common main backup----
--main.sendorgtype sendorgtype,send_year,send_month,send_day,send_week,send_bigrole_id
--common link
to_char(null) linkid,
task.ACCEPTTIMELIMIT NODEACCEPTLIMIT,
task.COMPLETETIMELIMIT NODECOMPLETELIMIT,
to_number(null) OPERATETYPE,
to_date(null) OPERATETIME,
case when task.taskowner!=task.operateroleid then task.taskowner else to_char(null) end OPERATEUSERID,
case when task.taskowner!=task.operateroleid then ud.deptid else u.deptid end OPERATEDEPTID,
task.operateroleid OPERATEROLEID,
to_number(null) TOORGTYPE,
to_char(null) TOORGUSERID,
to_char(null)  TOORGDEPTID,
to_char(null) TOORGROLEID,
case when to_date(to_char(task.ACCEPTTIMELIMIT,'YYYY-MM-DD HH24:MI:SS'),'YYYY-MM-DD HH24:MI:SS')>sysdate then 1 else 2 end ACCEPTFLAG,
case when to_date(to_char(task.COMPLETETIMELIMIT,'YYYY-MM-DD HH24:MI:SS'),'YYYY-MM-DD HH24:MI:SS')>sysdate then 1 else 2 end COMPLETEFLAG,
task.PRELINKID,
to_char(null) PARENTLINKID,
to_char(null) FIRSTLINKID,
task.id AIID,
task.taskname ACTIVETEMPLATEID,
to_char(null) NODETEMPLATENAME,
to_char(null) NODEACCESSORIES,
to_number(null) OPERATEORGTYPE,
  ------common link backup----
--operate_year,operate_month,operate_day,operate_week,
--common task
task.SUBTASKFLAG,
task.PARENTTASKID,
task.taskstatus,
task.operatetype taskoperatetype
--bussines main
,main.MAINPRODUCTTYPE                        
,main.MAINPRODUCTNAME                        
,main.MAINPRODUCTCODE                         
,main.MAINISGF                         
,main.MAINDESIGNSHEETID                         
,main.MAINSHEETID                         
,main.MAINTASK                         
,main.MAINISGC                         
                        
,main.MAINEXTENDACC                         
,main.MAINISSUCCESS    
,main.mainifrecord                       
,main.SENDYEAR                
,main.SENDMONTH                
,main.SENDDAY
--bussines link
                         
,to_date(null) LINKOPERSTARTTIME                         
,to_date(null) LINKOPERENDTIME                         
                         
,to_char(null) LINKTESTRESULT                         
,to_char(null) LINKNETTYPE                                                                
                        
,to_char(null) LINKISSUCCESS                         
                         
,to_number(null) operateyear             
,to_number(null) operatemonth               
,to_number(null) operateday     

from businessoperation_main main

join businessoperation_task task on main.id=task.sheetkey and task.taskstatus!=5 and main.templateflag=0
left join taw_system_user ud on task.taskowner!=task.operateroleid and task.taskowner=ud.userid
left join taw_system_sub_role orole on orole.id=task.operateroleid
left join taw_system_sub_role srole on srole.id=main.sendroleid
left join taw_system_userrefrole refr on refr.subroleid = task.operateroleid
left join taw_system_user u on u.userid = refr.userid
join taw_system_dept sd on main.senddeptid=sd.deptid
join taw_system_dept od on u.deptid=od.deptid
)


create table est_last_oper(
id number(5) not null primary key,--对应工单类型
last_operate_time TIMESTAMP(6),
comments varchar2(128)--标注
)

--delete from est_last_oper

--初始化任务工单采集时间
insert into est_last_oper values (23,to_date('2007-07-07 00:00:00','YYYY-MM-DD HH24:MI:SS'),'新业务正式实施工单基础表采集的上一次操作的时间');

--drop table est_last_oper
--delete from est_last_oper
select * from est_last_oper

SELECT last_operate_time 
FROM est_last_oper  
WHERE 
id=51

select link.operatetime from businessoperation_link link

SELECT count(distinct mainid) FROM businessoperation_link HAVING max(operatetime) >= to_date('2007-07-07 00:00:00','YYYY-MM-DD HH24:MI:SS') and max(operatetime) < sysdate

select mainid,operatetime from businessoperation_link

delete from est_businessoperation
select * from est_businessoperation
drop table est_businessoperation
--中间表
CREATE TABLE est_businessoperation
(
  EST_DATA_STATUS NUMBER(5)
, send_bigrole_id number(5)
, operate_bigrole_id number(5)
, send_dept_level VARCHAR2(30)
, operate_dept_level  VARCHAR2(30)
, MAINID VARCHAR2(510)
, SHEETID VARCHAR2(510)
, TITLE VARCHAR2(510)
, SHEETACCEPTLIMIT date
, SHEETCOMPLETELIMIT date
, SENDTIME date
, SENDUSERID VARCHAR2(510)
, SENDDEPTID VARCHAR2(510)
, SENDROLEID VARCHAR2(510)
, SENDCONTACT VARCHAR2(510)
, STATUS number(10)
, HOLDSTATISFIED number(10)
, ENDTIME date
, ENDRESULT VARCHAR2(510)
, ENDUSERID VARCHAR2(510)
, ENDDEPTID VARCHAR2(510)
, ENDROLEID VARCHAR2(510)
, DELETED NUMBER(10)
, PIID VARCHAR2(510)
, PARENTSHEETNAME VARCHAR2(510)
, PARENTSHEETID VARCHAR2(510)
, SHEETTEMPLATENAME VARCHAR2(510)
, SHEETACCESSORIES VARCHAR2(510)
, TODEPTID VARCHAR2(510)
, CANCELREASON VARCHAR2(510)
, LINKID VARCHAR2(510)
, NODEACCEPTLIMIT date
, NODECOMPLETELIMIT date
, OPERATETYPE NUMBER(10)
, OPERATETIME date
, OPERATEUSERID VARCHAR2(510)
, OPERATEDEPTID VARCHAR2(510)
, OPERATEROLEID VARCHAR2(510)
, TOORGTYPE NUMBER(10)
, TOORGUSERID VARCHAR2(510)
, TOORGDEPTID VARCHAR2(510)
, TOORGROLEID VARCHAR2(510)
, ACCEPTFLAG NUMBER(10)
, COMPLETEFLAG NUMBER(10)
, PRELINKID VARCHAR2(510)
, PARENTLINKID VARCHAR2(510)
, FIRSTLINKID VARCHAR2(510)
, AIID VARCHAR2(510)
, ACTIVETEMPLATEID VARCHAR2(510)
, NODETEMPLATENAME VARCHAR2(510)
, NODEACCESSORIES VARCHAR2(510)
, OPERATEORGTYPE INTEGER  
, SUBTASKFLAG VARCHAR2(10)
, PARENTTASKID VARCHAR2(50)
, TASKSTATUS VARCHAR2(510)
, taskoperatetype VARCHAR2(500)
, MAINPRODUCTTYPE    VARCHAR2(500)                        
, MAINPRODUCTNAME    VARCHAR2(500)                        
, MAINPRODUCTCODE    VARCHAR2(500)                       
, MAINISGF           VARCHAR2(500)                     
, MAINDESIGNSHEETID  VARCHAR2(500)                      
, MAINSHEETID        VARCHAR2(500)                       
, MAINTASK           VARCHAR2(500)                       
, MAINISGC           VARCHAR2(500)                       
                          
, MAINEXTENDACC      VARCHAR2(500)                        
, MAINISSUCCESS      VARCHAR2(20)
, mainifrecord number                         
, SENDYEAR           number  default 0                
, SENDMONTH          number  default 0                
, SENDDAY            number  default 0                   
, LINKOPERSTARTTIME    DATE                                
, LINKOPERENDTIME      DATE                                                        
, LINKTESTRESULT       VARCHAR2(500)                         
, LINKNETTYPE          VARCHAR2(500)                        
                    
, LINKISSUCCESS        VARCHAR2(50)                          
                          
, operateYEAR             number   default 0                
, operateMONTH            number   default 0                
, operateDAY              number   default 0
)


