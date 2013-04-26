<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<html>
<head>
    <title><spring:message code="item.categories.title"/></title>
</head>
<body>

<h1><spring:message code="item.categories.title"/></h1>

<div id="listItemCategories"></div>
<div id="editItemCategories"></div>

<script data-main="itemCategories/main" src="<c:url value='/resources/js/lib/require.js'/>"></script>
</body>
</html>
