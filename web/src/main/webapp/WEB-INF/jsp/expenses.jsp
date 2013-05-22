<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<html>
<head>
    <meta name="activePage" content="expenses">
    <title><spring:message code="expenses.title"/></title>
</head>
<body>

<h1><spring:message code="expenses.title"/></h1>

<div class="row-fluid span12 entity-controls">
    <div class="search inline span3"></div>

    <div class="inline span3 offset6">
        <a href="#add" role="button" class="btn btn-primary">
            <i class="icon-plus icon-white"> </i>
            <spring:message code="expenses.add.expense"/>
        </a>
    </div>
</div>

<div class="row-fluid span12 listEntities"></div>
<div class="row-fluid span12 forPagination"></div>
<div class="editEntity"></div>

<script data-main="expenses/main" src="<c:url value='/resources/js/lib/require.js'/>"></script>
</body>
</html>
