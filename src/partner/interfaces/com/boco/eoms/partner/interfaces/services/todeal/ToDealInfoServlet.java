/*package com.boco.eoms.partner.interfaces.services.todeal;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class ToDealInfoServlet extends HttpServlet {

	static Logger logger = Logger.getLogger(ToDealInfoServlet.class);

	*//**
	 * Constructor of the object.
	 *//*
	public ToDealInfoServlet() {
		super();
	}

	*//**
	 * Destruction of the servlet. <br>
	 *//*
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#service(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse)
	 
	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setCharacterEncoding("gb2312");
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();

		 
		PartnerUndoInfoDelegate partnerUndoInfoDelegate = new PartnerUndoInfoDelegate();
		String xml = partnerUndoInfoDelegate.service(PartnerUndoInfoDelegate.GET_TO_DEAL_INFO,
				request);
		logger.info("back xml:" + xml);
		out.print(xml);
		out.flush();
		out.close();
	}

}
*/