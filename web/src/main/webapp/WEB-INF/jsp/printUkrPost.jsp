<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<html>
<head>
    <title><spring:message code="invoice.print.ukrPost.title"/></title>
</head>
<body>

<div class="invoicesPlaceholder"></div>

<script data-main="invoicePrint/mainUkrPost" src="${pageContext.request.contextPath}/resources/js/lib/require.js"></script>
</body>
</html>
