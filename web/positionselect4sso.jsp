
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="java.util.*"%>
<%@page import="com.boco.eoms.base.webapp.form.SaveSessionBeanForm"%>
<%@ page import="com.boco.eoms.duty.dao.*"%>

<%
	SaveSessionBeanForm saveSessionBeanForm = (SaveSessionBeanForm) request
			.getSession().getAttribute("saveSessionBeanForm");
            request.getSession().setAttribute("portalLogin","1");
			try {
				//上面操作的结果是:纪录deptId,deptName,userid,userName,userpwd,roomId
				//dbtype=0,isDutyMaster=false,realpath="",workserial=0
				//如果属于多机房，roomId=-1
				int roomId = saveSessionBeanForm.getWrf_RoomID();
				System.out.println("+++++++++++++++++ roomId = "+roomId);
				if (roomId < 0) {
					//属于多机房
					//取得userId,password，其实没有用
					String userId = saveSessionBeanForm.getWrf_UserID();
					String password = saveSessionBeanForm.getWrf_UserPwd();
					request.setAttribute("userid", userId);
					request.setAttribute("password", password);
					//取得此人（userId）所属的机房信息
					com.boco.eoms.db.util.ConnectionPool ds = com.boco.eoms.db.util.ConnectionPool.getInstance();
					//TawUserRoomDAO tawUserRoomDAO = new TawUserRoomDAO(ds);
					Vector vecId = new Vector();
					System.out.println("+++++++++++++++++ portal +++++++ userId = "+userId);
					//vecId = tawUserRoomDAO.retrieveRoom(userId);
					vecId =  (Vector)request.getSession().getAttribute("portalLoginDutyVecId");
					System.out.println("+++++++++++++++++ portal +++++++ vecId.size() = "+vecId.size());
					request.setAttribute("vecId", vecId);
					request.getSession().setAttribute("potalVecId",vecId);
					Vector vecName = new Vector();
					//vecName = tawUserRoomDAO.retrieveRoomName(userId);
					vecName = (Vector)request.getSession().getAttribute("portalLoginDutyVecName");
					request.setAttribute("vecName", vecName);
					

                     request.getRequestDispatcher("/roomSelect.do?method=roomSelect").forward(request,response);
					//response.sendRedirect("http://"
					//				+ request.getServerName()
					//				+ ":"+request.getServerPort()+request.getContextPath()+"/roomselect.jsp");

									
				} else if (roomId > 0) {//个人理解单机房
					response.sendRedirect("http://"
									+ request.getServerName()
									+ ":"+request.getServerPort()+request.getContextPath()+"/workserialselect.do?method=workserialselect");
				}
			}

			catch (Exception e) {
				e.printStackTrace();
				response.sendRedirect("http://"
						+ request.getServerName()
						+ ":"+request.getServerPort()+request.getContextPath()+"/common/failure.jsp");
			}
			//不属于任何机房roomId = 0
		//	response.sendRedirect("http://"
		//				+ request.getServerName()
		//				+ ":"+request.getServerPort()+"/"+request.getContextPath()+"/TawRmRecord/record.do");
						

%>
