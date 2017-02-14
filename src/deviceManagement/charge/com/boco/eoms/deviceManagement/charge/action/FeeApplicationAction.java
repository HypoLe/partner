package com.boco.eoms.deviceManagement.charge.action;


import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.loging.BocoLog;
import com.boco.eoms.commons.system.dept.model.TawSystemDept;
import com.boco.eoms.commons.system.dept.service.ITawSystemDeptManager;
import com.boco.eoms.commons.system.role.model.TawSystemSubRole;
import com.boco.eoms.commons.system.role.service.ITawSystemSubRoleManager;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.commons.system.user.model.TawSystemUserRefRole;
import com.boco.eoms.commons.system.user.service.ITawSystemUserRefRoleManager;
import com.boco.eoms.deviceManagement.charge.model.FeeApplicationLink;
import com.boco.eoms.deviceManagement.charge.model.FeeApplicationMain;
import com.boco.eoms.deviceManagement.charge.model.FeeApplicationMainForm;
import com.boco.eoms.deviceManagement.charge.service.FeeApplicationLinkService;
import com.boco.eoms.deviceManagement.charge.service.FeeApplicationService;
import com.boco.eoms.deviceManagement.common.service.CommonSpringJdbcService;
import com.boco.eoms.deviceManagement.common.utils.CommonConstants;
import com.boco.eoms.deviceManagement.common.utils.CommonUtils;
import com.boco.eoms.partner.baseinfo.mgr.PartnerDeptMgr;
import com.boco.eoms.partner.baseinfo.model.PartnerDept;
import com.boco.eoms.partner.baseinfo.model.TdObjModel;
import com.boco.eoms.partner.baseinfo.util.RoleIdList;
import com.boco.eoms.partner.baseinfo.util.TableHelper;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;


public final class FeeApplicationAction extends BaseAction {
 

	
	public FeeApplicationService getMainBean() {
		String source = FeeApplicationService.class.getSimpleName();
		return (FeeApplicationService) getBean(source.substring(0, 1)
				.toLowerCase().concat(source.substring(1)));
	}
	
    public FeeApplicationLinkService getLinkBean() {
		String source = FeeApplicationLinkService.class.getSimpleName();
		return (FeeApplicationLinkService) getBean(source.substring(0, 1)
			.toLowerCase().concat(source.substring(1)));
	}
	

	public CommonSpringJdbcService getJdbcBean() {
		String source = CommonSpringJdbcService.class.getSimpleName();
		return (CommonSpringJdbcService) getBean(source.substring(0, 1)
				.toLowerCase().concat(source.substring(1)));
	}

	public ActionForward forwardlist(ActionMapping mapping) {
		ActionForward forward = mapping.findForward("forwardlist");
		String path = forward.getPath();
		return new ActionForward(path, false);
	}

	public ActionForward goToAdd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("goToAdd");
	}

	

	// Go to the single detail page
	@SuppressWarnings("unchecked")
	public ActionForward goToDetail(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id =  request.getParameter("id");
		
		FeeApplicationMain feeApplicationMain = this.getMainBean().find(id);
		
		Search search = new Search();	
		search.addFilterEqual("feeApplicationID", id);
		SearchResult<FeeApplicationLink> searchResult = this.getLinkBean()
		.searchAndCount(search);
	    List<FeeApplicationLink> feeApplicationLinkList = searchResult.getResult();	
	    
	
		request.setAttribute("feeApplicationMain", feeApplicationMain);
		request.setAttribute("feeApplicationLinkList",feeApplicationLinkList);
		return mapping.findForward("goToDetail");
	}
	
	public ActionForward goToDeal (ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id =  request.getParameter("id");
		FeeApplicationMain feeApplicationMain = this.getMainBean().find(id);
		
		Search search = new Search();	
		search.addFilterEqual("feeApplicationID", id);
		SearchResult<FeeApplicationLink> searchResult = this.getLinkBean()
		.searchAndCount(search);
	    List<FeeApplicationLink> feeApplicationLinkList = searchResult.getResult();	
	    
	
		request.setAttribute("feeApplicationMain", feeApplicationMain);
		request.setAttribute("feeApplicationLinkList",feeApplicationLinkList);
		return mapping.findForward("goToDeal");
	}


	// Go to record editing page.
	public ActionForward goToEdit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id =  request.getParameter("id");
		FeeApplicationMain feeApplicationMain = this.getMainBean().find(id);
		Search search = new Search();	
		search.addFilterEqual("feeApplicationID", id);
		SearchResult<FeeApplicationLink> searchResult = this.getLinkBean()
		.searchAndCount(search);
	    List<FeeApplicationLink> feeApplicationLinkList = searchResult.getResult();	
	    
		request.setAttribute("feeApplicationLinkList",feeApplicationLinkList);
		request.setAttribute("feeApplicationMain", feeApplicationMain);
		return mapping.findForward("goToEdit");
	}
	
	// Edit a new record.修改
	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		FeeApplicationMainForm feeApplicationMainForm = (FeeApplicationMainForm) form;
		FeeApplicationMain feeApplicationMain = (FeeApplicationMain) convert(feeApplicationMainForm);
		
		this.getMainBean().save(feeApplicationMain);
		
		return mapping.findForward("success");
	}

	// Delete a record.Afterward forward request.
	@SuppressWarnings("finally")
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		try {
			String id = request.getParameter("id");
			FeeApplicationMain feeApplicationMain = this.getMainBean().find(id);
			
			feeApplicationMain.setDeleted("1");
			feeApplicationMain.setDeleteTime(CommonUtils.toEomsStandardDate(new Date()));
			
			this.getMainBean().save(feeApplicationMain);
			
			
			
		} catch (RuntimeException e) {
			BocoLog.info(this, "删除出错！");
			e.printStackTrace();
			CommonUtils.printJsonSuccessMsg(response);
		} finally {
			return mapping.findForward("success");
		} 
	}

	// Delete all selected records.Afterward forward request. Notice the
	// difference with delete() method.
	public ActionForward deleteAll(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String myDealingList[] = StaticMethod.nullObject2String(
				request.getParameter("deleteIds"), "").split(";");
		
		FeeApplicationMain feeApplicationMain=new FeeApplicationMain();
		
		for (String id : myDealingList) {	
			feeApplicationMain=this.getMainBean().find(id);
			feeApplicationMain.setDeleted("1");
			feeApplicationMain.setDeleteTime(CommonUtils.toEomsStandardDate(new Date()));
			this.getMainBean().save(feeApplicationMain);
		}
		
		return forwardlist(mapping);
	}
	
	// Add a record.增加到草稿或直接提交
	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String parentDeptId = "";
		TawSystemSessionForm sessionform = this.getUser(request);
		String feeApplicationUser = sessionform.getUsername();
		String feeApplicationDept = sessionform.getDeptname();
		String feeApplicationCall = sessionform.getContactMobile();
		String feeApplicationDeptId = sessionform.getDeptid();
		String feeApplicationCompanyName=sessionform.getCompanyName();
		String feeApplicationGreatTime = CommonUtils.toEomsStandardDate(new Date());
		String deptid=sessionform.getDeptid();
		String subType="fristSub";
		String bigRole=this.getMainBean().getBigRole();
		
		ITawSystemDeptManager deptMgr = (ITawSystemDeptManager)getBean("ItawSystemDeptManager");
		TawSystemDept dept = deptMgr.getDeptinfobydeptid(deptid,"0");
		
		
		
		if(dept!=null && dept.getId()!=null){//如果该部门不存在，则XXXXXX
			parentDeptId = StaticMethod.nullObject2String(dept.getParentDeptid()).trim();	
            RoleIdList roleIdList = (RoleIdList)getBean("roleIdList");
            
		       if(parentDeptId.equals("") || parentDeptId.equals(roleIdList.getParDeptId().toString())||parentDeptId.equals("1")||parentDeptId.equals("-1")){//父部门是省下属，则XXXXXX
		   		return mapping.findForward("failure");
		    	  
		    }
		       else{
		    	    
		    	    PartnerDeptMgr partnerDeptMgr=(PartnerDeptMgr)getBean("partnerDeptMgr");
		    	    
		    	    
		    	    String where=" and dept_mag_id= "+ parentDeptId;
		    	    List parentDeptList= partnerDeptMgr.getPartnerDepts(where);
                        
		    	         if(!(parentDeptList.equals(null)||parentDeptList.size()==0)){
		    	    PartnerDept parentDept=(PartnerDept) parentDeptList.get(0);
				    String parentArea=parentDept.getAreaId();
				    
				    String whereStr="roleid= "+bigRole+" and "+ " ("+"deptId= "+parentArea+" or "+" area= "+parentDeptId+" )" ;
				    ITawSystemSubRoleManager iTawSystemSubRoleManager=(ITawSystemSubRoleManager)getBean("ItawSystemSubRoleManager");
				    List subRolesList=iTawSystemSubRoleManager.getTawSystemSubRoles(whereStr);	
				    
				                 if(!(subRolesList==null||subRolesList.size()==0)) {
				    
				    TawSystemSubRole tawSystemSubRole=(TawSystemSubRole)subRolesList.get(0);
				    String subRoleId  =tawSystemSubRole.getId();
		    
				    FeeApplicationMainForm feeApplicationMainForm = (FeeApplicationMainForm) form;
					FeeApplicationMain feeApplicationMain = (FeeApplicationMain) convert(feeApplicationMainForm);
					
			    	feeApplicationMain.setFeeApplicationUser(feeApplicationUser);
			    	feeApplicationMain.setFeeApplicationDept(feeApplicationDept);
					feeApplicationMain.setFeeApplicationCall(feeApplicationCall);
			    	feeApplicationMain.setFeeApplicationGreatTime(feeApplicationGreatTime);
			    	feeApplicationMain.setFeeApplicationCompanyName(feeApplicationCompanyName);
			    	feeApplicationMain.setFeeApplicationRoleID(subRoleId);
			    	feeApplicationMain.setSubType(subType);
			    	feeApplicationMain.setDeleted("0");
					
					getMainBean().save(feeApplicationMain);	
					
					return mapping.findForward("success");
				     }
				                 else{
		                        	  return mapping.findForward("failure");
		                          }
                          }
                          else{
                        	  return mapping.findForward("failure");
                          }	  
		       }
		}
		 else{
			 return mapping.findForward("failure");
	    }
	 }
	
	public ActionForward file(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		
			String id = request.getParameter("id");
			FeeApplicationMain feeApplicationMain = this.getMainBean().find(id);
			
			feeApplicationMain.setFeeApplicationStatus("5");

			this.getMainBean().save(feeApplicationMain);

			return mapping.findForward("success");
		
	}
	
	
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {


		Search search = new Search();	
		search = CommonUtils.getSqlFromRequestMap(request.getParameterMap(), search);
		// Get userId and deptId to add privileges searching.
		TawSystemSessionForm sessionform = this.getUser(request);	
		String deptId=sessionform.getDeptid();
		String userId=sessionform.getUserid();
		String userName=sessionform.getUsername();
		String type = request.getParameter("Type");
		String listType="list"+type;
		
		search.addFilterEqual("deleted", "0");
	
		
		if((!type.equals("1"))&&(!type.equals("4"))){	
			String roleid="";
			
			if(type.equals("2")){
				roleid=this.getMainBean().getBigRole();
				
				}	
			if(type.equals("3")){
					roleid=this.getMainBean().getParterRole();
				}
			
			
			ITawSystemUserRefRoleManager refRoleMgr =(ITawSystemUserRefRoleManager)getBean("itawSystemUserRefRoleManager");	
			String refRole="";
		    ITawSystemSubRoleManager iTawSystemSubRoleManager=(ITawSystemSubRoleManager)getBean("ItawSystemSubRoleManager");
			List userRefRoleList=refRoleMgr.getUserRefRoleByuserid(userId);
			List<String> roleList = new ArrayList<String>();
			
			 if(!(userRefRoleList==null||userRefRoleList.size()==0)){
				 for(int i=0;i<userRefRoleList.size();i++ ){
				 TawSystemUserRefRole tawSystemUserRefRole=(TawSystemUserRefRole)userRefRoleList.get(i);
				 refRole =tawSystemUserRefRole.getSubRoleid();
				 
				 }
				 TawSystemSubRole subRole=iTawSystemSubRoleManager.getTawSystemSubRole(refRole);	
				    
				 
				 if(subRole.equals("")||subRole.equals(null)){
					 
					 Integer size=new Integer(0);
	            	  List<FeeApplicationMain> feeApplicationMainList = new ArrayList<FeeApplicationMain>();
	          		
	          		request.setAttribute("feeApplicationMainList",feeApplicationMainList);
	          		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
	          		request.setAttribute("size", size);
	          	
	          		
	            	  return mapping.findForward(listType);
				 }
				 else{
					 
					 
				 
				    String subRoleId  =String.valueOf(subRole.getRoleId());
			 
				  if((roleid).equals(subRoleId)){
					  roleList.add(refRole);
	                 }
				 
		         if(roleList!=null && !roleList.isEmpty()){
		        	 Object[] roleArray = new Object[roleList.size()];
		        	 for (int i = 0; i < roleList.size(); i++) {
						roleArray[i] = roleList.get(i);
						
					}
		        	 search.addFilterIn("feeApplicationRoleID", roleList); 
		        	 search.addFilterEqual("feeApplicationStatus", type);
		         } 
		        	 
		         
				 
		         else{
	            	  
	            	  Integer size=new Integer(0);
	            	  List<FeeApplicationMain> feeApplicationMainList = new ArrayList<FeeApplicationMain>();
	          		
	          		request.setAttribute("feeApplicationMainList",feeApplicationMainList);
	          		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
	          		request.setAttribute("size", size);
	          	
	          		
	            	  return mapping.findForward(listType);
				
			 }
				 }
			 }
		
              else{
            	  
            	  Integer size=new Integer(0);
            	  List<FeeApplicationMain> feeApplicationMainList = new ArrayList<FeeApplicationMain>();
          		
          		request.setAttribute("feeApplicationMainList",feeApplicationMainList);
          		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
          		request.setAttribute("size", size);
          	
          		
            	  return mapping.findForward(listType);

              }
		      
		}
		if(type.equals("4")){			
		    search.addFilterEqual("feeApplicationStatus", "5");	
		    search.addFilterEqual("feeApplicationUser", userName);
			}
		if(type.equals("1")){	
			search.addFilterEqual("feeApplicationUser", userName);
			search.addFilterNotEqual("feeApplicationStatus","5");
			}
		
		String pageIndexString = request
				.getParameter((new org.displaytag.util.ParamEncoder(
						"feeApplicationMainList")
						.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE)));
		search.setMaxResults(CommonConstants.PAGE_SIZE);
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		search.setFirstResult(firstResult * CommonConstants.PAGE_SIZE);
		search.setMaxResults(CommonConstants.PAGE_SIZE);
		SearchResult<FeeApplicationMain> searchResult = this.getMainBean()
				.searchAndCount(search);
		
		List<FeeApplicationMain> feeApplicationMainList = searchResult.getResult();
		
		request.setAttribute("feeApplicationMainList",feeApplicationMainList);
		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
//		request.setAttribute("feeApplicationMainFormList", searchResult.getResult());
		request.setAttribute("size", searchResult.getTotalCount());
		
		return mapping.findForward(listType);
		
	
		
	}
	
	public ActionForward importRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		response.setCharacterEncoding("utf-8");
		FeeApplicationMainForm uploadForm = (FeeApplicationMainForm) form;
		FormFile formFile = uploadForm.getImportFile();
		
		TawSystemSessionForm sessionform = this.getUser(request);
		String feeApplicationUser = sessionform.getUsername();
		String feeApplicationDept = sessionform.getDeptname();
		String feeApplicationCall = sessionform.getContactMobile();	
		String feeApplicationCompanyName=sessionform.getCompanyName();
	
//		String parentDeptId = "";
//	
//		String deptid=sessionform.getDeptid();
//		String subType="fristSub";
//		String bigRole=this.getMainBean().getBigRole();
//		
//		ITawSystemDeptManager deptMgr = (ITawSystemDeptManager)getBean("ItawSystemDeptManager");
//		TawSystemDept dept = deptMgr.getDeptinfobydeptid(deptid,"0");
//		
//		
//		if(dept!=null && dept.getId()!=null){//如果该部门不存在，则XXXXXX
//			parentDeptId = StaticMethod.nullObject2String(dept.getParentDeptid()).trim();	
//            RoleIdList roleIdList = (RoleIdList)getBean("roleIdList");
//            
//		       if(parentDeptId.equals("") || parentDeptId.equals(roleIdList.getParDeptId().toString())||parentDeptId.equals("1")||parentDeptId.equals("-1")){//父部门是省下属，则XXXXXX
//		   		return mapping.findForward("failure");
//		    	  
//		    }
//		       else{
//		    	    
//		    	    PartnerDeptMgr partnerDeptMgr=(PartnerDeptMgr)getBean("partnerDeptMgr");
//		    	    
//		    	    
//		    	    String where=" and dept_mag_id= "+ parentDeptId;
//		    	    List parentDeptList= partnerDeptMgr.getPartnerDepts(where);
//                        
//		    	         if(!(parentDeptList.equals(null)||parentDeptList.size()==0)){
//		    	    PartnerDept parentDept=(PartnerDept) parentDeptList.get(0);
//				    String parentArea=parentDept.getAreaId();
//				    
//				    String whereStr="roleid= "+bigRole+" and "+ " ("+"deptId= "+parentArea+" or "+" area= "+parentDeptId+" )" ;
//				    ITawSystemSubRoleManager iTawSystemSubRoleManager=(ITawSystemSubRoleManager)getBean("ItawSystemSubRoleManager");
//				    List subRolesList=iTawSystemSubRoleManager.getTawSystemSubRoles(whereStr);	
//				    
//				                 if(!(subRolesList==null||subRolesList.size()==0)) {
//				    
//				    TawSystemSubRole tawSystemSubRole=(TawSystemSubRole)subRolesList.get(0);
//				    String subRoleId  =tawSystemSubRole.getId();
		    
				   
				    Map<String,String> params = new HashMap<String,String>();
					params.put("feeApplicationUser", feeApplicationUser);
					params.put("feeApplicationDept", feeApplicationDept);
					params.put("feeApplicationCall", feeApplicationCall);
					params.put("feeApplicationCompanyName", feeApplicationCompanyName);
//					params.put("feeApplicationRoleID", subRoleId);
					
					PrintWriter writer = null;
					try{
						writer = response.getWriter();
						String result = this.getMainBean().importRecord(formFile.getInputStream(),formFile.getFileName(),params);
							writer.write(
									new Gson().toJson(new ImmutableMap.Builder<String, String>()
											.put("success", "true")
											.put("msg", "ok")
											.put("infor", "导入成功:"+result).build()));
						
					}catch(Exception e){
						e.printStackTrace();
						writer.write(
								new Gson().toJson(new ImmutableMap.Builder<String, String>()
										.put("success", "failure")
										.put("msg", "failure")
										.put("infor", "导入失败").build()));
						e.printStackTrace();
					} finally {
						if(writer != null) {
							writer.close();
						}
					}
	    
					
				return null;
//				     
//				     }
//				                 else{
//		                        	  return mapping.findForward("failure");
//		                          }
//                          }
//                          else{
//                        	  return mapping.findForward("failure");
//                          }	  
//		       }
//		}
//		 else{
//			 return mapping.findForward("failure");
//	    }
//		
		
	}
	
	
	public ActionForward deal(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		
		
		
		
		
		

		FeeApplicationMain feeApplicationMain=new FeeApplicationMain();
		FeeApplicationMainForm feeApplicationLinkForm = (FeeApplicationMainForm) form;
		FeeApplicationLink feeApplicationLink =new FeeApplicationLink();	
		TawSystemSessionForm sessionform = this.getUser(request);
		String type = request.getParameter("Type");	
		String Status=request.getParameter("feeApplicationStatus");
		String id = request.getParameter("id");
		feeApplicationMain=this.getMainBean().find(id);
		String feeApplicationID=id;
		String operateOpinion  =feeApplicationLinkForm.getOperateOpinion();
		String operateRemark   =feeApplicationLinkForm.getOperateRemark();
		String operateAccessory=feeApplicationLinkForm.getOperateAccessory();
		String operateTarget   =feeApplicationLinkForm.getOperateTarget();
		String operateResult   =feeApplicationLinkForm.getOperateResult();
		String operateUser     = sessionform.getUsername();
		String operateDept     = sessionform.getDeptname();
		String operateCall     = sessionform.getContactMobile();	
		String operateRole     = feeApplicationMain.getFeeApplicationRoleID();
		String operateTime     = CommonUtils.toEomsStandardDate(new Date());	
		String feeApplicationStatus=String.valueOf(Integer.valueOf(Status)+Integer.valueOf(operateResult));
		
		
		
		feeApplicationLink.setFeeApplicationID(feeApplicationID);
		feeApplicationLink.setOperateOpinion(operateOpinion);
		feeApplicationLink.setOperateRemark(operateRemark);
		feeApplicationLink.setOperateResult(operateResult);
		feeApplicationLink.setOperateAccessory(operateAccessory);
		feeApplicationLink.setOperateUser(operateUser);
    	feeApplicationLink.setOperateDept(operateDept);
		feeApplicationLink.setOperateCall(operateCall);
		feeApplicationLink.setOperateRole(operateRole);
		feeApplicationLink.setOperateTime(operateTime) ; 
		feeApplicationLink.setOperateStatus(feeApplicationStatus);
		feeApplicationMain.setFeeApplicationStatus(feeApplicationStatus);
		
		  
		if(operateResult.equals("-1")){
			if(Status.equals("3")){
			Search search = new Search();	
			search.addFilterEqual("operateStatus", Status);
			search.addFilterEqual("operateTarget", operateRole);
			SearchResult<FeeApplicationLink> searchResult = this.getLinkBean()
			.searchAndCount(search);
			
		    List<FeeApplicationLink> feeApplicationLinkList = searchResult.getResult();	
			

		    
		    FeeApplicationLink feeAppliLink=feeApplicationLinkList.get(0);
		    String targetRole=feeAppliLink.getOperateRole();
			
		    feeApplicationLink.setOperateTarget(targetRole);
		    feeApplicationMain.setFeeApplicationRoleID(targetRole);
		       }	
		}	
		else{
			String deptid=sessionform.getDeptid();
			String subType="fristSub";
			String roleid="";
			if(Status.equals("2")){
			roleid=this.getMainBean().getParterRole();
			
			String parentDeptId = "";
			ITawSystemDeptManager deptMgr = (ITawSystemDeptManager)getBean("ItawSystemDeptManager");
			TawSystemDept dept = deptMgr.getDeptinfobydeptid(deptid,"0");
			
			
			if(dept!=null && dept.getId()!=null){//如果该部门不存在，则XXXXXX
				parentDeptId = StaticMethod.nullObject2String(dept.getParentDeptid()).trim();	
	            RoleIdList roleIdList = (RoleIdList)getBean("roleIdList");
	            
			       if(parentDeptId.equals("") || parentDeptId.equals(roleIdList.getParDeptId().toString())||parentDeptId.equals("1")||parentDeptId.equals("-1"))
			          {//父部门是省下属，则XXXXXX
			   		return mapping.findForward("failure");  	  
			          }
			       else{
			    	    
			    	    PartnerDeptMgr partnerDeptMgr=(PartnerDeptMgr)getBean("partnerDeptMgr");
			    	    String where=" and dept_mag_id= "+ parentDeptId;
			    	    List parentDeptList= partnerDeptMgr.getPartnerDepts(where);
	                        
			    	         if(!(parentDeptList.equals(null)||parentDeptList.size()==0))
			    	         {
			    	    PartnerDept parentDept=(PartnerDept) parentDeptList.get(0);
			    	    
					    String parentArea=parentDept.getAreaId();
					    
					    String whereStr="roleid= "+roleid+" and "+ " ("+"deptId= "+parentArea+" or "+" area= "+parentDeptId+" )" ;
					    ITawSystemSubRoleManager iTawSystemSubRoleManager=(ITawSystemSubRoleManager)getBean("ItawSystemSubRoleManager");
					    List subRolesList=iTawSystemSubRoleManager.getTawSystemSubRoles(whereStr);	
					    
					                 if(!(subRolesList==null||subRolesList.size()==0)) {
					    
					    TawSystemSubRole tawSystemSubRole=(TawSystemSubRole)subRolesList.get(0);
					    String subRoleId  =tawSystemSubRole.getId();
					    feeApplicationLink.setOperateTarget(subRoleId);
					    feeApplicationMain.setFeeApplicationRoleID(subRoleId);
					                 }
					                 else{
			                        	  return mapping.findForward("failure");
			                              }
	                          }
	                          else{
	                        	  return mapping.findForward("failure");
	                              }	  

		
			    }
            }
		}
		}
		this.getLinkBean().save(feeApplicationLink);
		this.getMainBean().save(feeApplicationMain);
		return mapping.findForward("success");
	}
	
	
	public ActionForward goToShowPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return   mapping.findForward("goToShowPage");
	}
	//list
	public ActionForward  staffContent(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String rows[] = StaticMethod.nullObject2String(
				request.getParameter("deleteIdss"), "").split(";");
		String checkString = StaticMethod.nullObject2String(
				request.getParameter("checks"), "");
		String search = "";
		String group="";
		
		for (int i = 0; i < rows.length; i++) {
			String row = rows[i].replace("0", ".");
			if (i == rows.length - 1) {
				search += row +" as "+rows[i] ;
				group+= row;
			} else {
				search += row + " as "+rows[i]+",";
				group+= row+",";
			}

		}

		//get the text to where condition.
		String whereCondition =" ";
//		String province=request.getParameter("province");
		String feeApplicationCity=request.getParameter("sy0feeApplicationCityT");
		String feeApplicationCompanyName=request.getParameter("fe0feeApplicationCompanyNameT");
		
		String feeApplicationGreatTime=request.getParameter("fe0feeApplicationGreatTimeT");
		String feeApplicationType=request.getParameter("fe0feeApplicationTypeT");
//		if(!"".equals(province)){
//		    whereCondition+=" and fe.id like '%"+"%'";
//		}
		if(!"".equals(feeApplicationCity)){
			whereCondition+=" and sy.areaid ='"+feeApplicationCity+"'";
		}
		if(!"".equals(feeApplicationCompanyName)){
			whereCondition+=" and fe.feeApplicationCompanyName like '%"+feeApplicationCompanyName+"%'";
		}
		if(!"".equals(feeApplicationGreatTime)){
			whereCondition+=" and fe.feeApplicationGreatTime like '%"+feeApplicationGreatTime+"%'";
		}
		if(!"".equals(feeApplicationType)){
			whereCondition+=" and fe.feeApplicationType like '%"+feeApplicationType+"%'";
		}
		// sql：		
//		select de.deptname,ct.party_a,ct.party_b ,count(contract_title) 
//		from  taw_system_dept de  
//		left join pnr_ct_contents  ct on ct.create_dept=de.deptid  
//		where ct.create_dept='1'
//		group by de.deptname,ct.party_a,ct.party_b  
//		order by de.deptname,ct.party_a,ct.party_b 
		//get the search column .change the '0' to '.'ept de
		
		String searchSql="select "+search+
		        " ,count(fe.id) " +
				"from  taw_system_area sy"+
				" left join feeapplicationmain  fe on fe.feeApplicationCity=sy.areaid "+ 
				"where deleted =0"+""+
				whereCondition+""+
		"group by " +" " +  group
		+" "+"  order by "+" "+  group; 


		List<String> headList = new ArrayList<String>();
		for (int i = 0; i < rows.length; i++) {
			if("fe0id".equals(rows[i])){
				headList.add("全省");
			}
			if("sy0areaname".equals(rows[i])){
				headList.add("地市");
			}
			else if("fe0feeApplicationCompanyName".equals(rows[i])){
				headList.add("代维公司");
			}
			else if("fe0feeApplicationGreatTime".equals(rows[i])){
				headList.add("时间段");
			}
			else if("fe0feeApplicationType".equals(rows[i])){
				headList.add("费用类型");
			}
		}
		
		headList.add("总数");

		List<List<TdObjModel>> tempList = TableHelper.verticalGrowp(rows, searchSql,"/deviceManagement/chargeFeeAppli/charge.do?method=searchInto");
		request.setAttribute("tableList", tempList);
		request.setAttribute("headList", headList);
		request.setAttribute("checkList", checkString);

		return mapping.findForward("goToShowPage");
	}
	
	public ActionForward searchInto(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String fe0feeapplicationcompanyname =request.getParameter("fe0feeapplicationcompanyname");
		String sy0areaname =request.getParameter("sy0areaname");
		String fe0feeapplicationgreattime =request.getParameter("fe0feeapplicationgreattime");
		String fe0feeapplicationtype =request.getParameter("fe0feeapplicationtype");
		
		
		Search search = new Search();
		
		   if(fe0feeapplicationcompanyname!=null){		
			  String feeApplicationCompanyName =new String(fe0feeapplicationcompanyname.toString().trim().getBytes("ISO-8859-1"),"utf-8");
			  search.addFilterEqual("feeApplicationCompanyName", feeApplicationCompanyName);
		   }
		   if(sy0areaname!=null){
	
			   String areaname =new String(sy0areaname.toString().trim().getBytes("ISO-8859-1"),"utf-8");		
			   String sql = "select areaid from taw_system_area where areaname="			
				   + "'"+areaname+"'";	   
			   List<ListOrderedMap> list =this.getJdbcBean().queryForList(sql); 	   
			   Map areaId =list.get(0);	   
			   String area=(String)areaId.get("areaid");	   
			   search.addFilterEqual("feeApplicationCity", area);
		   }
		   if(fe0feeapplicationgreattime!=null){
	
			   String feeApplicationGreatTime = new String(fe0feeapplicationgreattime.toString().trim().getBytes("ISO-8859-1"),"utf-8");
			   search.addFilterEqual("feeApplicationGreatTime", feeApplicationGreatTime); 
		   }
		   if(fe0feeapplicationtype!=null){
		
			   String feeApplicationType =  new String(fe0feeapplicationtype.toString().trim().getBytes("ISO-8859-1"),"utf-8");  
			   search.addFilterEqual("feeApplicationType", feeApplicationType);
		   }
		   
		search.addFilterEqual("deleted", "0");
		String pageIndexString = request
				.getParameter((new org.displaytag.util.ParamEncoder(
						"feeApplicationMainList")
						.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE)));
		search.setMaxResults(CommonConstants.PAGE_SIZE);
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		search.setFirstResult(firstResult * CommonConstants.PAGE_SIZE);
		search.setMaxResults(CommonConstants.PAGE_SIZE);
		SearchResult<FeeApplicationMain> searchResult = this.getMainBean()
		.searchAndCount(search);
	    List<FeeApplicationMain> feeApplicationMainList = searchResult.getResult();	
		request.setAttribute("feeApplicationMainList",feeApplicationMainList);
		String next = "listAcl";
		request.setAttribute("size", searchResult.getTotalCount());
		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
		return mapping.findForward("statisticsList");
	}
	
}
