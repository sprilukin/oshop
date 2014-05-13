<%@ page language="java" errorPage="/exception.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Cache-Control" content="no-store"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="contextPath" content="${pageContext.request.contextPath}">
    <link rel="icon" href="<c:url value='/favicon.ico'/>"/>

    <title><spring:message code="orders.title"/> | <spring:message code="webapp.name"/></title>

    <link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/resources/css/base.css" />
    <link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/<spring:theme code='css'/>"/>

    <script src="${pageContext.request.contextPath}/resources/js/requirejs.config.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/lib/require-2.1.5.js"></script>
    <script language="JavaScript">
        require.config({
            baseUrl: "${pageContext.request.contextPath}/resources/js"
        });
    </script>
</head>
<body>

<div class="container">
    <div class="navbarPlaceholder"></div>

    <%-- placeholder for warnings --%>
    <div class="warning" style="padding-top: 40px"></div>

    <div id="imageGallery" style="position: absolute"></div>

    <h1><spring:message code="orders.title"/></h1>

    <div class="row-fluid span12 listEntities"></div>

    <script type="text/javascript">
        require(["oshop"], function(Oshop) {
            new Oshop();
        });
    </script>
</div>
</body>
</html>

