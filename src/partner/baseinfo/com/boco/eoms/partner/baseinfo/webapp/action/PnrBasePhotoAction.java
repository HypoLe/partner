package com.boco.eoms.partner.baseinfo.webapp.action;

import java.awt.Rectangle;
import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.partner.baseinfo.util.FileUploadProcessor;
import com.boco.eoms.partner.baseinfo.util.PnrPhotoConstants;
import com.boco.eoms.partner.baseinfo.webapp.form.TawPartnerCarForm;
import com.boco.eoms.base.util.StaticMethod;

/**
 * 
 * Title:缩略图
 * 
 * Description:车辆缩略图 行驶证缩略图
 * 
 * 2009-12-23
 * wangjunfeng
 * 1.0
 */
public final class PnrBasePhotoAction extends BaseAction {
 
	/**
	 * 未指定方法时默认调用的方法
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return uploadPhoto(mapping, form, request, response);
	}
	

	/**
	 * 弹出上传图片页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward uploadPhoto(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		String picNo = request.getParameter("picNo");
		String picIDImage = request.getParameter("picIDImage");
		String hdId = request.getParameter("hdId");
		request.setAttribute("picNo", picNo);
		request.setAttribute("picIDImage", picIDImage);
		request.setAttribute("hdId", hdId);
		
		//formNo 区分表单 在截取缩略图页面回传时候区别返回的缩略图
		String formNo = request.getParameter("formNo");
		request.setAttribute("formNo", formNo);


		request.setAttribute("step", "1");
		return mapping.findForward("uploadPhotoJ");
	}
	
	/**
	 * 点击上传按钮
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward uploadPhotoDo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		TawPartnerCarForm  tawPartnerCarForm =(TawPartnerCarForm) form;
		FormFile formFile = tawPartnerCarForm.getThumbnailFile();
		
		String picNo = request.getParameter("picNo");
		request.setAttribute("picNo", picNo);
		
		//formNo 区分表单 在截取缩略图页面回传时候区别返回的缩略图
		String formNo = request.getParameter("formNo");
		request.setAttribute("formNo", formNo);
		
		String picID = request.getParameter("picID");
		request.setAttribute("picID", picID);
		
		String picNameID = request.getParameter("picNameID");
		request.setAttribute("picNameID", picNameID);
		
		if(formFile == null){
			if(picID == null){
				return uploadPhoto(mapping, form, request, response);
			}else{
				return uploadPhotoJ(mapping, form, request, response);
			}
		}

		String upFileName = formFile.getFileName();
		String uploadTime = StaticMethod.getCurrentDateTime("yyMMddHHmmssS");
		String expand = upFileName.substring(upFileName.lastIndexOf('.') + 1);// 文件扩展名
		String photoName = uploadTime + "." + expand; // 映射文件名

		String filePath =  "/images/pnr_thumbnail/photo/" + photoName;

		String newPath = PnrPhotoConstants.getPhysicalPath(filePath, 1, this.servlet.getServletContext());
		FileUploadProcessor.processUploadedFile(formFile, newPath);
		
		request.setAttribute("photoName", photoName);
		request.setAttribute("filePath", filePath);
		request.setAttribute("step", "2");

		return mapping.findForward("uploadPhotoJ");
	}
	
	
	
	
	/**
	 * 剪裁图片 保存到数据库
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward zoomPhotoDo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

	    int imageWidth = Integer.parseInt(request.getParameter("txt_width"));
	    int imageHeight = Integer.parseInt(request.getParameter("txt_height"));
	    int cutTop = Integer.parseInt(request.getParameter("txt_top"));
	    int cutLeft = Integer.parseInt(request.getParameter("txt_left"));
	    int dropWidth = Integer.parseInt(request.getParameter("txt_DropWidth"));
	    int dropHeight = Integer.parseInt(request.getParameter("txt_DropHeight"));
	    String picture = request.getParameter("picture");
	    
		String picNo = request.getParameter("picNo");
		request.setAttribute("picNo", picNo);
		
		//formNo 区分表单 在截取缩略图页面回传时候区别返回的缩略图
		String formNo = request.getParameter("formNo");
		request.setAttribute("formNo", formNo);
		
		String picID = request.getParameter("picID");
		request.setAttribute("picID", picID);
		
		String picNameID = request.getParameter("picNameID");
		request.setAttribute("picNameID", picNameID);

		String fromPath =  "/images/pnr_thumbnail/photo/" + picture;
		String toPath =  "/images/pnr_thumbnail/photo/zoom/" + picture;
		String newFromPath = PnrPhotoConstants.getPhysicalPath(fromPath, 1, this.servlet.getServletContext());
		String newToPath = PnrPhotoConstants.getPhysicalPath(toPath, 1, this.servlet.getServletContext());

	    File inputFile = new File(newFromPath);
	    File outputFile = new File(newToPath);

	    Rectangle rect = new Rectangle(cutLeft, cutTop, dropWidth, dropHeight);
	    PnrPhotoConstants.cut(inputFile, outputFile, imageWidth, imageHeight, rect);
	    

		request.setAttribute("photoName", picture);
		request.setAttribute("filePath", picture);
		request.setAttribute("step", "3");

		return mapping.findForward("uploadPhotoJ");
	}

	
	/**
	 * 弹出上传图片页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward uploadPhotoJ(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		String picNo = request.getParameter("picNo");
		request.setAttribute("picNo", picNo);
		
		String picID = request.getParameter("picID");
		request.setAttribute("picID", picID);
		
		String picNameID = request.getParameter("picNameID");
		request.setAttribute("picNameID", picNameID);
		
		//formNo 区分表单 在截取缩略图页面回传时候区别返回的缩略图
		String formNo = request.getParameter("formNo");
		request.setAttribute("formNo", formNo);


		request.setAttribute("step", "1");
		return mapping.findForward("uploadPhotoJ");
	}
	
	/**
	 * 点击上传按钮
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward uploadPhotoJDo(ActionMapping mapping, ActionForm form,	
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		TawPartnerCarForm  tawPartnerCarForm =(TawPartnerCarForm) form;
		FormFile formFile = tawPartnerCarForm.getThumbnailFile();
		String picNo = StaticMethod.nullObject2String(request.getParameter("picNo"));
		String picIDImage = StaticMethod.nullObject2String(request.getParameter("picIDImage"));
		String hdId = StaticMethod.nullObject2String(request.getParameter("hdId"));
		request.setAttribute("picNo", picNo);
		request.setAttribute("hdId", hdId);
		request.setAttribute("picNo", picNo);
		request.setAttribute("picIDImage", picIDImage);
		
		//formNo 区分表单 在截取缩略图页面回传时候区别返回的缩略图
		String formNo = request.getParameter("formNo");
		request.setAttribute("formNo", formNo);
		
		String picID = request.getParameter("picID");
		request.setAttribute("picID", picID);
		
		String picNameID = request.getParameter("picNameID");
		request.setAttribute("picNameID", picNameID);
		
		if(formFile == null){			
			return uploadPhoto(mapping, form, request, response);
		}

		String upFileName = formFile.getFileName();
		String uploadTime = StaticMethod.getCurrentDateTime("yyMMddHHmmssS");
		String expand = upFileName.substring(upFileName.lastIndexOf('.') + 1);// 文件扩展名
		String photoName = uploadTime + "." + expand; // 映射文件名

		String filePath =  "/images/pnr_thumbnail/photo/" + photoName;

		String newPath = PnrPhotoConstants.getPhysicalPath(filePath, 1, this.servlet.getServletContext());
		FileUploadProcessor.processUploadedFile(formFile, newPath);
		
		request.setAttribute("photoName", photoName);
		request.setAttribute("filePath", filePath);
		request.setAttribute("step", "2");

		return mapping.findForward("uploadPhotoJ");
	}
	
	
	
	
	/**
	 * 剪裁图片 保存到数据库
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward zoomPhotoJDo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String picNo = StaticMethod.nullObject2String(request.getParameter("picNo"));
		String picIDImage = StaticMethod.nullObject2String(request.getParameter("picIDImage"));
		String hdId = StaticMethod.nullObject2String(request.getParameter("hdId"));
		request.setAttribute("picIDImage", picIDImage);
		request.setAttribute("hdId", hdId);
		request.setAttribute("picNo", picNo);
		
		
	    int imageWidth = Integer.parseInt(request.getParameter("txt_width"));
	    int imageHeight = Integer.parseInt(request.getParameter("txt_height"));
	    int cutTop = Integer.parseInt(request.getParameter("txt_top"));
	    int cutLeft = Integer.parseInt(request.getParameter("txt_left"));
	    int dropWidth = Integer.parseInt(request.getParameter("txt_DropWidth"));
	    int dropHeight = Integer.parseInt(request.getParameter("txt_DropHeight"));
	    String picture = request.getParameter("picture");
	    
		request.setAttribute("picNo", picNo);
		
		//formNo 区分表单 在截取缩略图页面回传时候区别返回的缩略图
		String formNo = request.getParameter("formNo");
		request.setAttribute("formNo", formNo);
		
		String picID = request.getParameter("picID");
		request.setAttribute("picID", picID);
		
		String picNameID = request.getParameter("picNameID");
		request.setAttribute("picNameID", picNameID);

		String fromPath =  "/images/pnr_thumbnail/photo/" + picture;
		String toPath =  "/images/pnr_thumbnail/photo/zoom/" + picture;
		String newFromPath = PnrPhotoConstants.getPhysicalPath(fromPath, 1, this.servlet.getServletContext());
		String newToPath = PnrPhotoConstants.getPhysicalPath(toPath, 1, this.servlet.getServletContext());

	    File inputFile = new File(newFromPath);
	    File outputFile = new File(newToPath);

	    Rectangle rect = new Rectangle(cutLeft, cutTop, dropWidth, dropHeight);
	    PnrPhotoConstants.cut(inputFile, outputFile, imageWidth, imageHeight, rect);
	    

		request.setAttribute("photoName", picture);
		request.setAttribute("filePath", picture);
		request.setAttribute("step", "3");

		return mapping.findForward("uploadPhotoJ");
	}

	
	
}