package com.boco.eoms.deviceManagement.busi.protectline.service.impl;

import java.io.UnsupportedEncodingException;

import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.deviceManagement.busi.protectline.dao.ArmyPoliceCivilianDao;
import com.boco.eoms.deviceManagement.busi.protectline.model.ArmyPoliceCivilian;
import com.boco.eoms.deviceManagement.busi.protectline.service.ArmyPoliceCivilianService;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

public class ArmyPoliceCivilianServiceImpl
// extends CommonGenericServiceImpl<ArmyPoliceCivilian>
		implements ArmyPoliceCivilianService {
	private ArmyPoliceCivilianDao armyPoliceCiviliandao;

	public void setArmyPoliceCiviliandao(
			ArmyPoliceCivilianDao armyPoliceCiviliandao) {
		this.armyPoliceCiviliandao = armyPoliceCiviliandao;
	}

	public boolean add(ArmyPoliceCivilian armyPoliceCivilian) {
		boolean ooo = armyPoliceCiviliandao.save(armyPoliceCivilian);
		return ooo;
	}

	public boolean deleteWa(String id) {

		return armyPoliceCiviliandao.removeById(id);
	}

	public void deltetAll(String[] ids) {

		armyPoliceCiviliandao.removeByIds(ids);

	}

	public void edit(String id, String name, String lineType, String lineLevel,
			String segmentType, String startFlag, String endFlag) {
		// TODO Auto-generated method stub

	}

	public SearchResult<ArmyPoliceCivilian> find(String state,String approvingPerson,String projectName,
			String nameOfOrganization, String userid,int first, int max) {
		Search search = new Search();
		search.setFirstResult(first);
		search.setMaxResults(max);
		search.addSort("writeTime", true);
		//String state = request.getParameter("state");
		if (null != state
				&& !"".equals(state)) {
			if("1".equals(state)){
				search.addFilterEqual("state", 
						 "待审核" );
			}else if("2".equals(state)){
				search.addFilterEqual("state", 
				 "驳回" );
			}else if("3".equals(state)){
				search.addFilterEqual("state", 
				 "归档" );
			}
			
		}
	//	String approvingPerson = request.getParameter("approvingPerson");
		if (null != approvingPerson
				&& !"".equals(approvingPerson)) {
			search.addFilterLike("approvingPerson", "%"
					+ approvingPerson + "%");
		}
		if (null != projectName && !"".equals(projectName)) {
			search.addFilterLike("projectName", "%" + projectName + "%");
		}
		if (null != nameOfOrganization && !"".equals(nameOfOrganization)) {
			search.addFilterLike("nameOfOrganization", "%" + nameOfOrganization
					+ "%");
		}
         search.addFilterEqual("writePerson", userid);
		SearchResult<ArmyPoliceCivilian> searchresult = armyPoliceCiviliandao
				.searchAndCount(search);

		return searchresult;
	}

	public SearchResult<ArmyPoliceCivilian> approvingfind(String projectName,
			String nameOfOrganization,String userid, int first, int max) {
		Search search = new Search();
		search.setFirstResult(first);
		search.setMaxResults(max);
		search.addSort("writeTime", true);
		if (null != projectName && !"".equals(projectName)) {
			search.addFilterLike("projectName", "%" + projectName + "%");
		}
		if (null != nameOfOrganization && !"".equals(nameOfOrganization)) {
			search.addFilterLike("nameOfOrganization", "%" + nameOfOrganization
					+ "%");
		}
		search.addFilterEqual("state", "待审核");
		search.addFilterEqual("approvingPerson", userid);
		SearchResult<ArmyPoliceCivilian> searchresult = armyPoliceCiviliandao
				.searchAndCount(search);

		return searchresult;
	}

	public SearchResult<ArmyPoliceCivilian> findByCondition(String projectName,
			String nameOfOrganization, String state, int first, int max) {
		Search search = new Search();
		search.setFirstResult(first);
		search.setMaxResults(max);
		search.addSort("writeTime", true);
		if (null != projectName && !"".equals(projectName)) {
			search.addFilterLike("projectName", "%" + projectName + "%");
		}
		if (null != nameOfOrganization && !"".equals(nameOfOrganization)) {
			search.addFilterLike("nameOfOrganization", "%" + nameOfOrganization
					+ "%");
		}
		search.addFilterEqual("state", state);

		SearchResult<ArmyPoliceCivilian> searchresult = armyPoliceCiviliandao
				.searchAndCount(search);

		return searchresult;
	}

	// public SearchResult<ArmyPoliceCivilian> listAll(int first, int max) {
	// Search search = new Search();
	// search.setFirstResult(first);
	// search.setMaxResults(max);
	// return armyPoliceCiviliandao.searchAndCount(search);
	// }
	public ArmyPoliceCivilian findDetail(String id) {
		Search search = new Search();
		search.addFilterEqual("id", id);
		ArmyPoliceCivilian armyPoliceCivilian = armyPoliceCiviliandao.find(id);
		return armyPoliceCivilian;
	}

	public SearchResult<ArmyPoliceCivilian> findArchied(String projectName,
			String nameOfOrganization, int first, int max) {
		Search search = new Search();
		search.setFirstResult(first);
		search.setMaxResults(max);
		search.addSort("writeTime", true);
		if (null != projectName && !"".equals(projectName)) {
			search.addFilterLike("projectName", "%" + projectName + "%");
		}
		if (null != nameOfOrganization && !"".equals(nameOfOrganization)) {
			search.addFilterLike("nameOfOrganization", "%" + nameOfOrganization
					+ "%");
		}

		search.addFilterEqual("state", "归档");

		SearchResult<ArmyPoliceCivilian> searchresult = armyPoliceCiviliandao
				.searchAndCount(search);
		return searchresult;
	}

	public SearchResult<ArmyPoliceCivilian> approvedfind(String projectName,
			String nameOfOrganization, int first, int max) {
		Search search = new Search();
		search.setFirstResult(first);
		search.setMaxResults(max);
		search.addSort("approvingtime", true);
		if (null != projectName && !"".equals(projectName)) {
			search.addFilterLike("projectName", "%" + projectName + "%");
		}
		if (null != nameOfOrganization && !"".equals(nameOfOrganization)) {
			search.addFilterLike("nameOfOrganization", "%" + nameOfOrganization
					+ "%");
		}
		search.addFilterEqual("approvingresult", "驳回");
		SearchResult<ArmyPoliceCivilian> searchresult = armyPoliceCiviliandao
				.searchAndCount(search);

		return searchresult;
	}

	public SearchResult<ArmyPoliceCivilian> approvedFindSuccessed(
			String projectName, String nameOfOrganization,String approvingPerson, int first, int max) {
		Search search = new Search();
		search.setFirstResult(first);
		search.setMaxResults(max);
		search.addSort("approvingtime", true);
		if (null != projectName && !"".equals(projectName)) {
			search.addFilterLike("projectName", "%" + projectName + "%");
		}
		if (null != approvingPerson && !"".equals(approvingPerson)) {
			search.addFilterLike("approvingPerson",  approvingPerson);
		}
		if (null != nameOfOrganization && !"".equals(nameOfOrganization)) {
			search.addFilterLike("nameOfOrganization", "%" + nameOfOrganization
					+ "%");
		}
		search.addFilterEqual("state", "归档");
		SearchResult<ArmyPoliceCivilian> searchresult = armyPoliceCiviliandao
				.searchAndCount(search);

		return searchresult;
	}

	public SearchResult<ArmyPoliceCivilian> staticsFindwa(String projectName,
			String nameOfOrganization, String belongTheLocal, int first, int max) {
		Search search = new Search();
		search.setFirstResult(first);
		search.setMaxResults(max);
		search.addSort("approvingtime", true);
		if (null != projectName && !"".equals(projectName)) {
			try {
				projectName = new String(projectName.toString().trim()
						.getBytes("ISO-8859-1"), "utf-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			search.addFilterLike("projectName", "%" + projectName + "%");
		}
		if (null != nameOfOrganization && !"".equals(nameOfOrganization)) {
			try {
				nameOfOrganization = new String(nameOfOrganization.toString()
						.trim().getBytes("ISO-8859-1"), "utf-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			search.addFilterLike("nameOfOrganization", "%" + nameOfOrganization
					+ "%");
		}
		if (null != belongTheLocal && !"".equals(belongTheLocal)) {
			try {
				belongTheLocal = new String(belongTheLocal.toString().trim()
						.getBytes("ISO-8859-1"), "utf-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			search.addFilterLike("belongTheLocal", "%" + belongTheLocal + "%");
		}
		search.addFilterEqual("state", "归档");
		SearchResult<ArmyPoliceCivilian> searchresult = armyPoliceCiviliandao
				.searchAndCount(search);

		return searchresult;
	}

}
