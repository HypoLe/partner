<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<table class="formTable">
<caption><div class="header center">人员基本信息及其技能信息详细列表</div></caption>
</table>
<fieldset>
	<legend ><b>人员基本信息</b></legend>
	<table class="formTable">
			<c:forEach items="${userinfoList}" var="user">
			<tr>
				<td class="label">姓名</td>
				<td class="content">${user['name']}</td>
				<td class="label">用户ID</td>
					<td class="content">${user['user_id']}</td>
			</tr>
			<tr>
				<td class="label">身份证</td>
				<td class="content">${user['person_card_no']}</td>
				<td class="label">代维公司</td>
				<td class="content">${user['dept_name']}</td>
			</tr>
			<tr>
				<td class="label">员工编码</td>
				<td class="content">${user['userno']}</td>
				<td class="label">所属区域</td>
				<td class="content">${user['area_name']}</td>
			</tr>
			<tr>
				<td class="label">籍贯</td>
				<td class="content">${user['nativeplace']}</td>
				<td class="label">民族</td>
				<td class="content">
					<eoms:id2nameDB id="${user['nationality']}" beanId="ItawSystemDictTypeDao"/>
				</td>
			</tr>
			<tr>
				<td class="label">性别</td>
				<td class="content">
					<eoms:id2nameDB id="${user['sex']}" beanId="ItawSystemDictTypeDao" />
				</td>
				<td class="label">出生日期</td>
				<td class="content">${user['birthdey']}</td>
			</tr>
			<tr>
				<td class="label">年龄</td>
				<td class="content">${user['age']}</td>
				<td class="label">手机号码</td>
				<td class="content">${user['mobile_phone']}</td>
			</tr>
			<tr>
				<td class="label">Email</td>
				<td class="content">${user['email']}</td>
				<td class="label">集团短号</td>
				<td class="content">${user['groupphone']}</td>
			</tr>
			<tr>
				<td class="label">毕业院校</td>
				<td class="content">${user['graduateschool']}</td>
				<td class="label">所学专业</td>
				<td class="content">${user['learnspecialty']}</td>
			</tr>
			<tr>
				<td class="label">学历</td>
				<td class="content">
					<eoms:id2nameDB id="${user['diploma']}" beanId="ItawSystemDictTypeDao" />
				</td>
				<td class="label">开始工作时间</td>
				<td class="content">${user['work_time']}</td>
			</tr>
			<tr>
				<td class="label">在职状态</td>
				<td class="content">
					<eoms:id2nameDB id="${user['post_state']}" beanId="ItawSystemDictTypeDao"/>
				</td>
				<td class="label">黑名单标示
				</td>
				<td class="content">
					<c:if test="${user['blacklist']=='1'}">
					是
					</c:if>
					<c:if test="${user['blacklist']=='0'}">
					否
					</c:if>
				</td>
			</tr>
			</c:forEach>
		</table>
		<table><br></table>
	</fieldset>
	<fieldset>
		<legend ><b>工作经历信息</b></legend>
			<c:if test="${!empty workinfoList}">
				<table class="formTable">
					<tr>
						<td align="center" class="label">系统编号</td>
						<td align="center" class="label">工作单位</td>
						<td align="center" class="label">工作职务</td>
						<td align="center" class="label">入职时间</td>
						<td align="center" class="label">离职时间</td>
						<td align="center" class="label">查看详细</td>
					</tr>
					<c:forEach items="${workinfoList}" var="work">
						<tr>
							<td align="center">${work['sysno']}</td>
							<td align="center">${work['company']}</td>
							<td align="center">${work['duty']}</td>
							<td align="center">${work['entrytime']}</td>
							<td align="center">${work['leavetime']}</td>
							<td align="center">
								<a target="_blank" style="" style="text-decoration:none" href="${app}/personnel/workExperience.do?method=getDetail&id=${work['id']}&hasRightGoBack=''">
									<img src="${app }/images/icons/table.gif">
								</a>
							</td>
						</tr>
					</c:forEach>
				</table>
				<table><br></table>
			</c:if>
			<c:if test="${empty workinfoList}">
					<br>&nbsp;&nbsp;&nbsp;无工作经历信息<br><br>
			</c:if>
	</fieldset>
	<fieldset>
		<legend ><b>证书信息</b></legend>
		<c:if test="${!empty certinfoList}">
			<table class="formTable">
				<tr>
					<td align="center" class="label">系统编号</td>
					<td align="center" class="label">证书类别</td>
					<td align="center" class="label">证书等级</td>
					<td align="center" class="label">颁发时间</td>
					<td align="center" class="label">有效期</td>
					<td align="center" class="label">查看详细</td>
				</tr>
				<c:forEach items="${certinfoList}" var="cert">
					<tr>
						<td align="center">${cert['sysno']}</td>
						<td align="center">${cert['type1']}</td>
						<td align="center">${cert['level1']}</td>
						<td align="center">${cert['issuetime']}</td>
						<td align="center">${cert['validity']}</td>
						<td align="center">
							<a target="_blank" style="" style="text-decoration:none" href="${app}/personnel/certificate.do?method=getDetail&id=${cert['id']}&hasRightGoBack=''">
								<img src="${app }/images/icons/table.gif">
							</a>
						</td>
					</tr>
				</c:forEach>
			</table>
			<table><br></table>
		</c:if>
		<c:if test="${empty certinfoList}">
			<br>&nbsp;&nbsp;&nbsp;无证书信息<br><br>
		</c:if>
	</fieldset>
	<fieldset>
		<legend ><b>技能信息</b></legend>
			<c:if test="${!empty workinfoList}">
				<table class="formTable">
					<tr>
						<td align="center" class="label">系统编号</td>
						<td align="center" class="label">巡检专业</td>
						<td align="center" class="label">技能等级</td>
						<td align="center" class="label">拥有的系统账号</td>
						<td align="center" class="label">岗位</td>
						<td align="center" class="label">查看详细</td>
					</tr>
					<c:forEach items="${dwinfoList}" var="dwinfo">
						<tr>
							<td align="center">${dwinfo['sysno']}</td>
							<td align="center"><eoms:id2nameDB id="${dwinfo['professional']}" beanId="ItawSystemDictTypeDao"/></td>
							<td align="center"><eoms:id2nameDB id="${dwinfo['skilllevel']}" beanId="ItawSystemDictTypeDao"/></td>
							<td align="center"><eoms:id2nameDB id="${dwinfo['accountno']}" beanId="ItawSystemDictTypeDao"/></td>
							<td align="center">${dwinfo['duty']}</td>
							<td align="center">
								<a target="_blank"  style="text-decoration:none" href="${app}/personnel/dwInfo.do?method=getDetail&id=${dwinfo['id']}&hasRightGoBack=''">
									<img src="${app }/images/icons/table.gif">
								</a>
							</td>
						</tr>
					</c:forEach>
				</table>
				<table><br></table>
			</c:if>
			<c:if test="${empty workinfoList}">
					<br>&nbsp;&nbsp;&nbsp;无技能信息<br><br>
			</c:if>
	</fieldset>
	<fieldset>
		<legend ><b>培训经历信息</b></legend>
		<c:if test="${!empty pxinfoList}">
			<table class="formTable">
				<tr>
					<td align="center" class="label">系统编号</td>
					<td align="center" class="label">培训起始时间</td>
					<td align="center" class="label">培训结束时间</td>
					<td align="center" class="label">培训成绩</td>
					<td align="center" class="label">培训内容</td>
					<td align="center" class="label">查看详细</td>
				</tr>
				<c:forEach items="${pxinfoList}" var="px">
					<tr>
						<td align="center">${px['sysno']}</td>
						<td align="center">${px['starttime']}</td>
						<td align="center">${px['endtime']}</td>
						<td align="center">${px['score']}</td>
						<td align="center">${px['content']}</td>
						<td align="center">
							<a   target="_blank" href="${app}/personnel/pxExperience.do?method=getDetail&id=${px['id']}&hasRightGoBack=''">
								<img src="${app }/images/icons/table.gif">
							</a>	
						</td>
					</tr>
				</c:forEach>
			</table>
			<table><br></table>
		</c:if>
		<c:if test="${empty pxinfoList}">
				<br>&nbsp;&nbsp;&nbsp;无培训经历信息<br><br>
		</c:if>
	</fieldset>
	<fieldset>
		<legend ><b>奖励信息</b></legend>
			<c:if test="${!empty rewardinfoList}">
				<table class="formTable">
					<tr>
						<td align="center" class="label">系统编号</td>
						<td align="center" class="label">曾获奖励名称</td>
						<td align="center" class="label">获奖时间</td>
						<td align="center" class="label">授奖单位</td>
						<td align="center" class="label">查看详细</td>
					</tr>
				<c:forEach items="${rewardinfoList}" var="reward">
						<tr>
							<td align="center">${reward['sysno']}</td>
							<td align="center">${reward['reward']}</td>
							<td align="center">${reward['awardtime']}</td>
							<td align="center">${reward['awarddept']}</td>
							<td align="center">
								<a target="_blank" style="" style="text-decoration:none" href="${app}/personnel/reward.do?method=getDetail&id=${reward['id']}&hasRightGoBack=''">
									<img src="${app }/images/icons/table.gif">
								</a>
							</td>
						</tr>
					</c:forEach>
				</table>
				<table><br></table>
			</c:if>
			<c:if test="${empty rewardinfoList}">
				<br>&nbsp;&nbsp;&nbsp;无奖励信息<br><br>
			</c:if>
	</fieldset>
	
<%@ include file="/common/footer_eoms.jsp"%>
