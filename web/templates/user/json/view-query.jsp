<%@ page language="java" contentType="application/json; charset=UTF-8"%><%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json"%>
<json:array var="item" items="${list}" >
	<json:array items="${item}"/>
</json:array>
