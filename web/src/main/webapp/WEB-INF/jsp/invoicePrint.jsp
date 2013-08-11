<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<html>
<head>
    <title><spring:message code="invoice.print.title"/></title>
</head>
<body>

<div class="invoicesPlaceholder"></div>

<script src="resources/js/requirejs.config.js"></script>
<script data-main="invoicePrint/main" src="resources/js/lib/require.js"></script>
</body>
</html>
