<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<html>
<head>
    <meta name="activePage" content="orders">
    <title><spring:message code="orders.edit.order"/></title>
</head>
<body>

<h1><spring:message code="orders.edit.order"/></h1>

<div class="content"></div>

<script data-main="orders/editOrder/edit" src="${scriptSources}/lib/require.js"></script>
</body>
</html>
