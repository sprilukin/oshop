<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<html>
<head>
    <meta name="activePage" content="dashboard">
    <title><spring:message code="dashboard.title"/></title>
    <c:set var="moduleName" scope="request" value="dashboard/main"/>
</head>
<body>

<h1><spring:message code="dashboard.title"/></h1>

<div class="row-fluid span12">
    <div class="input-daterange inline span3" id="datepicker" style="float: none">
        <input type="text" class="input-small" name="start"/>
        <span class="add-on">to</span>
        <input type="text" class="input-small" name="end"/>
    </div>

    <div class="row">
        <div id="expensesIncomesSumChart" class="span6" style="height:400px;margin-top: 50px;float:left"></div>
        <div id="expensesIncomesPieChart" class="span6" style="height:400px;margin-top: 50px;float: left"></div>
    </div>
    <div id="expensesAndIncomesDailyChart" class="row span12" style="height:400px;margin-top: 50px"></div>
    <div id="incomesMinusExpensesCumulativeChart" class="row span12" style="height:400px;margin-top: 50px"></div>
    <div id="googlemap" class="row span12" style="height:800px;width:100%;margin-top: 50px;"></div>
</div>

</body>
</html>
