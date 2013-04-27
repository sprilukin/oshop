<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<html>
<head>
    <title><spring:message code="item.categories.title"/></title>
</head>
<body>

<h1><spring:message code="item.categories.title"/></h1>

<form id="ttt" action="/api/images/" method="post">
    <label for="file" title="File:"></label>
    <input id="file" name="file" type="file" />
    <input id="submit" name="submitName" type="submit" title="submmitTitle"/>
</form>
</body>
</html>
