<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<html>
<head>
    <title><spring:message code="item.categories.title"/></title>
</head>
<body>

<div id="itemCategoriesTableContainer">
    <%-- item categories table will be placed here --%>
</div>

<script data-main="resources/js/bb/main" src="<c:url value='/resources/js/lib/require.js'/>"></script>
</body>
</html>
