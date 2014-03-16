<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<html>
<head>
    <meta name="activePage" content="orders">
    <title><spring:message code="orders.title"/></title>
</head>
<body>

<h1><spring:message code="orders.title"/></h1>

<div class="row-fluid span12 entity-controls">
    <div class="search inline span3"></div>
    <div class="inline span3 offset5">
        <c:if test="${customerId eq null}">
            <c:set var="addPath" value="${pageContext.request.contextPath}/orders/add"/>
        </c:if>
        <c:if test="${customerId ne null}">
            <c:set var="addPath" value="${pageContext.request.contextPath}/customers/${customerId}/orders/add"/>
        </c:if>
        <a href="${addPath}" role="button" class="btn btn-primary">
            <i class="icon-plus icon-white"> </i>
            <spring:message code="orders.add.order"/>
        </a>
        <div class="printUkrPostInvoices" style="margin-top: 5px">
            <a href="#" role="button" class="btn btn-primary">
                <i class="icon-plus icon-white"> </i>
                <spring:message code="orders.print.ukrPost.invoice"/>
            </a>
        </div>
        <div class="printSalesReceipt" style="margin-top: 5px">
            <a href="#" role="button" class="btn btn-primary">
                <i class="icon-plus icon-white"> </i>
                <spring:message code="orders.print.salesReceipt"/>
            </a>
        </div>
    </div>
</div>

<div class="row-fluid span12">
    <div class="orderStatus inline span3"></div>
    <div class="row-fluid span5 offset4 forPagination"></div>
</div>
<div class="row-fluid span12 listEntities"></div>

<script data-main="orders/listOrders/list" src="${scriptSources}/lib/require.js"></script>
</body>
</html>
