<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<html>
<head>
    <meta name="activePage" content="orders">
    <title><spring:message code="orders.edit.order"/></title>
    <c:set var="moduleName" scope="request" value="orders/editOrder/edit"/>
</head>
<body>

<h1><spring:message code="orders.edit.order"/></h1>

<div class="content"></div>

</body>
</html>
