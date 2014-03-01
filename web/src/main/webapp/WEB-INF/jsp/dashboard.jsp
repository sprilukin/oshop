<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<html>
<head>
    <meta name="activePage" content="dashboard">
    <title><spring:message code="dashboard.title"/></title>
</head>
<body>

<h1><spring:message code="dashboard.title"/></h1>

<div class="row-fluid span12">
    <div class="input-daterange inline span3" id="datepicker" style="float: none">
        <input type="text" class="input-small" name="start"/>
        <span class="add-on">to</span>
        <input type="text" class="input-small" name="end"/>
    </div>

    <div id="expensesIncomesSumChart" style="width:100%;height:400px;margin-top: 50px"></div>
    <div id="expensesIncomesPieChart" style="width:100%;height:400px;margin-top: 50px"></div>
    <div id="expenseChart" style="width:100%;height:400px;margin-top: 50px"></div>
    <div id="expenseChart3" style="width:100%;height:400px;margin-top: 50px"></div>
</div>

<script data-main="dashboard/main" src="${pageContext.request.contextPath}/resources/js/lib/require.js"></script>
</body>
</html>
