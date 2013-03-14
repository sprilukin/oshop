<%@ page language="java" isErrorPage="true" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
<head>
    <title><spring:message code="errorPage.title"/></title>
    <link rel="stylesheet" type="text/css" media="all" href="<c:url value='/resources/styles/${appConfig["csstheme"]}/theme.css'/>" />
</head>

<body id="error">
<div id="page">
    <div id="content" class="clearfix">
        <div id="main">
            <h1><spring:message code="errorPage.heading"/></h1>
            <% if (exception != null) { %>
            <pre><% exception.printStackTrace(new java.io.PrintWriter(out)); %></pre>
            <% } else if ((Exception)request.getAttribute("javax.servlet.error.exception") != null) { %>
                    <pre><% ((Exception)request.getAttribute("javax.servlet.error.exception"))
                            .printStackTrace(new java.io.PrintWriter(out)); %></pre>
            <% } %>
        </div>
    </div>
</div>
</body>
</html>
