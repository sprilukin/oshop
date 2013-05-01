<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<html>
<head>
    <title><spring:message code="item.categories.title"/></title>
</head>
<body>

<h1><spring:message code="item.categories.title"/></h1>

<form class="form-search">
    <div class="input-append">
        <input type="text" class="span2 search-query">
        <button type="submit" class="btn"><i class="icon-search"></i></button>
    </div>
</form>

<a href="#add" role="button" class="btn btn-primary addItemCategory">
    <i class="icon-plus icon-white"> </i>
    <spring:message code="item.category.add.category"/>
</a>

<div id="listItemCategories" class="scrollable-table"></div>
<div class="forPagination"></div>
<div id="editItemCategories"></div>

<script data-main="itemCategories/main" src="<c:url value='/resources/js/lib/require.js'/>"></script>
</body>
</html>
