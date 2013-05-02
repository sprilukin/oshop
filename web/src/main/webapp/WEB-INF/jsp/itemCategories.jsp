<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<html>
<head>
    <title><spring:message code="item.categories.title"/></title>
</head>
<body>

<h1><spring:message code="item.categories.title"/></h1>

<div class="row-fluid span12 entity-controls">
    <div class="search inline span3"></div>

    <div class="inline span3 offset6">
        <a href="#add" role="button" class="btn btn-primary addItemCategory">
            <i class="icon-plus icon-white"> </i>
            <spring:message code="item.category.add.category"/>
        </a>
    </div>
</div>

<div id="listItemCategories" class="scrollable-table row-fluid span12"></div>
<div class="row-fluid span12 forPagination"></div>
<div id="editItemCategories"></div>

<script data-main="itemCategories/main" src="<c:url value='/resources/js/lib/require.js'/>"></script>
</body>
</html>
