<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<html>
<head>
    <meta name="activePage" content="incomes">
    <title><spring:message code="incomes.title"/></title>
</head>
<body>

<h1><spring:message code="incomes.title"/></h1>

<div class="row-fluid span12 entity-controls">
    <div class="input-daterange inline span3" id="datepicker">
        <input type="text" class="input-small" name="start" />
        <span class="add-on">to</span>
        <input type="text" class="input-small" name="end" />
    </div>

    <div class="inline span3 offset6">
        <a href="#add" role="button" class="btn btn-primary">
            <i class="icon-plus icon-white"> </i>
            <spring:message code="incomes.add.income"/>
        </a>
    </div>
</div>

<div class="row-fluid span12">
    <canvas id="expenseChart" width="1000" height="400"></canvas>
</div>

<div class="row-fluid span12 forPagination"></div>
<div class="row-fluid span12 listEntities"></div>
<div class="editEntity"></div>

<script data-main="incomes/main" src="${pageContext.request.contextPath}/resources/js/lib/require.js"></script>
</body>
</html>
