<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<html>
<head>
    <meta name="activePage" content="products">
    <title><spring:message code="products.title"/></title>
    <c:set var="moduleName" scope="request" value="products/main"/>
</head>
<body>

<h1><spring:message code="products.title"/></h1>

<div class="row-fluid span12 entity-controls">
    <div class="search inline span3"></div>
    <div class="inline span3 offset2">
        <a href="#add" role="button" class="btn btn-primary">
            <i class="icon-plus icon-white"> </i>
            <spring:message code="products.add.product"/>
        </a>
    </div>
</div>

<div class="row-fluid span12">
    <div class="orderStatus inline span3"></div>
    <div class="row-fluid span5 offset4 forPagination"></div>
</div>
<div class="row-fluid span12 listEntities"></div>
<div class="editEntity"></div>

</body>
</html>
