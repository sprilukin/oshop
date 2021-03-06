<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<html>
<head>
    <meta name="activePage" content="products">
    <title><spring:message code="products.title"/></title>
    <c:set var="moduleName" scope="request" value="addProductsToOrder/main"/>
</head>
<body>

<h1><spring:message code="products.title"/></h1>

<div class="row-fluid span12 entity-controls">
    <div class="search inline span3"></div>
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

<div class="row-fluid span12">
    <div class="orderStatus inline span3"></div>
    <div class="row-fluid span5 offset4 forPagination"></div>
</div>
<div class="row-fluid span12 listEntities"></div>

</body>
</html>
