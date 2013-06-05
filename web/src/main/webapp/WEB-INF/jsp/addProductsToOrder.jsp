<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<html>
<head>
    <meta name="activePage" content="products">
    <title><spring:message code="products.title"/></title>
</head>
<body>

<h1><spring:message code="products.title"/></h1>

<div class="row-fluid span12 entity-controls">
    <div class="search inline span3"></div>
    <div class="orderStatus inline span2 offset2"></div>
    <div class="inline span3 offset2">
        <div class="addProducts">
            <a href="#" role="button" class="btn btn-primary">
                <i class="icon-plus icon-white"> </i>
                <spring:message code="products.for.order.add"/>
            </a>
        </div>
        <div style="margin-top: 5px">
            <a href="${pageContext.request.contextPath}/orders/${orderId}" role="button" class="btn btn-primary">
                <i class="icon-plus icon-white"> </i>
                <spring:message code="products.for.order.back"/>
            </a>
        </div>
    </div>
</div>

<div class="row-fluid span12 listEntities"></div>
<div class="row-fluid span12 forPagination"></div>

<script data-main="addProductsToOrder/main" src="${pageContext.request.contextPath}/resources/js/lib/require.js"></script>
</body>
</html>
