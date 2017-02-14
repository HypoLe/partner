<%@ page language="java"   pageEncoding="UTF-8" %>
<%@page import="java.util.*,java.io.*" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms.jsp"%>
<%
        if(request.getParameter("filename")!=null)   
        {
           String filename = request.getParameter("filename");
           String encode = request.getCharacterEncoding();
         // filename = new String(filename.getBytes("gbk"),encode==null?"ISO8859-1":encode);
           
          // System.out.println(filename);
           
           String base = pageContext.getServletContext().getRealPath("/");
           base += "download";            
           
           File file = new File(base+"/"+filename);
           
           //设置请求头
           String type = application.getMimeType(base+"/"+filename);    
           response.setContentType("APPLICATION/OCTET-STREAM");
           
          // filename = new String(filename.getBytes(encode==null?"GB18030":encode),"ISO8859-1");
           response.setHeader("Content-Disposition","attachment;filename="+filename);
           
           response.setHeader("Content-Length",file.length()+"");
           
           InputStream in = new FileInputStream(file);
           OutputStream outp = response.getOutputStream();
           
           BufferedInputStream bufin = new BufferedInputStream(in);
           BufferedOutputStream bufout = new BufferedOutputStream(outp);
           
           byte[] temp = new byte[1024];
           while(bufin.read(temp)>0){
                bufout.write(temp); 
                bufout.flush();
           }
           bufout.close();
           bufin.close();
           outp.close();
           in.close();        
      
           out.clear();
           out = pageContext.pushBody();
           }
       %>

<div class="welcome">

  <div >
  <div><h1>智 能 认 证 平 台 操 作 手 册</h1></div>
    <div class="clear"></div>
    <div class="content">
         智能认证平台是为了提升及检测员工学习业务各种厂家业务知识的平台,本手册主要提供操作方法和导入试题文件劳格式要求

    </div>
    <div class="foot"></div>
  </div>
  
 
  <table  border="0">
   <tr>
    <td width="63" rowspan="2"> <img src="${app}/styles/default/images/wel-flow-img.gif"/></td>
    <td width="935" height="2"><strong><span lang="EN-US" xml:lang="EN-US">第一条、</span></strong></td>
  </tr>
  <tr>
    <td><strong>为了充分监控值班工作的监控、组织、协调、管理等工作职能，进一步强化监控值班管理工作，严格执行重大情况和突发事件报告制度，提高监控值班的管控能力，特制定《贵州移动网管中心监控管理规程》，请各位监控人员认真学习，并参照管理制度制定本单位的值班管理办法，强化执行和落实工作<span lang="EN-US" xml:lang="EN-US"></span></strong></td>
  </tr>
  <tr>
    <td rowspan="2"> <img src="${app}/styles/default/images/wel-flow-img.gif"/></td>
    <td><strong><span lang="EN-US" xml:lang="EN-US">第二条、</span></strong></td>
  </tr>
  <tr>
    <td><strong>本规程适用于山东联通网管中心监控维护管理工作；<span lang="EN-US" xml:lang="EN-US"></span></strong></td>
  </tr>
  <tr>
    <td rowspan="2"> <img src="${app}/styles/default/images/wel-flow-img.gif"/></td>
    <td><strong><span lang="EN-US" xml:lang="EN-US">第三条、</span></strong></td>
  </tr>
  <tr>
    <td><strong>专家职责包括故障、工单、投诉的处理和质检以及技术性工程随工；</strong></td>
  </tr>
  <tr>
    <td rowspan="2"> <img src="${app}/styles/default/images/wel-flow-img.gif"/></td>
    <td><strong><span lang="EN-US" xml:lang="EN-US">第四条、</span></strong></td>
  </tr>
  <tr>
    <td><strong>监控值班流程包括<span lang="EN-US" xml:lang="EN-US">7*24</span></strong><strong>小时不间断监控、系统检查、故障通告、日志填写以及交接班制度；</strong></td>
  </tr>
  <tr>
    <td rowspan="2"> <img src="${app}/styles/default/images/wel-flow-img.gif"/></td>
    <td><strong><span lang="EN-US" xml:lang="EN-US">第五条、</span></strong></td>
  </tr>
  <tr>
    <td><strong>本规程详细制定了休假、换班、加班、补休的管理制度；<span lang="EN-US" xml:lang="EN-US"></span></strong></td>
  </tr>
  <tr>
    <td rowspan="2"> <img src="${app}/styles/default/images/wel-flow-img.gif"/></td>
    <td><strong><span lang="EN-US" xml:lang="EN-US">第六条、</span></strong></td>
  </tr>
  <tr>
    <td><strong>项目经理职责包括领导监控工作、检查值班计划和维护作业计划、检查系统运行情况及人员出勤；</strong></td>
  </tr>
   <tr>
    <td  rowspan="2"> <img src="${app}/styles/default/images/wel-flow-img.gif"/></td>
    <td><strong><span lang="EN-US" xml:lang="EN-US">第七条、</span></strong></td>
  </tr>
  <tr>
    <td><strong>本规程详细制定了故障处理流程。</strong></td>
  </tr>
   <tr>
    <td rowspan="2"> <img src="${app}/styles/default/images/wel-flow-img.gif"/></td>
    <td ><strong><span lang="EN-US" xml:lang="EN-US">第八条、</span></strong></td>
  </tr>
  <tr>
    <td><strong>值班人员要严格落实值班制度，有特殊情况换班须向部门带班领导反映，经批准后方可换班。不得无故缺岗、迟到或早退。因故不能值班者，须事先向所在部门带班领导请假，获准后方可离开，否则视为缺岗。</strong></td>
  </tr>
</table>

  <a href="${app}/welcome.jsp?filename=guanlizangceng.doc">山东联通网管中心监控管理规程.doc</a>
      <span style="width: 200">     </span>                                                                                                
  <a href="${app}/welcome.jsp?filename=gongzuoxize.xls">监控班组工作细则.xls</a>
    
  
  
</div>

<%@ include file="/common/footer_eoms.jsp"%>
