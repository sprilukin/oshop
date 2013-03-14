<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Cache-Control" content="no-store"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="icon" href="<c:url value='/favicon.ico'/>"/>

    <title><decorator:title/> | <spring:message code="webapp.name"/></title>

    <link rel="stylesheet" type="text/css" media="all" href="<c:url value='/resources/css/bootstrap.css'/>" />
    <link rel="stylesheet" type="text/css" media="all" href="<c:url value='/resources/css/bootstrap-responsive.css'/>" />
    <link rel="stylesheet" type="text/css" media="all" href="<c:url value='/resources/css/jquery-ui-1.10.1.custom.css'/>" />
    <link rel="stylesheet" type="text/css" media="all" href="<c:url value='/resources/css/main.css'/>" />
    <script type="text/javascript" src="<c:url value='/resources/js/jquery-1.9.1.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/resources/js/jquery-ui-1.10.1.custom.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/resources/js/bootstrap.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/resources/js/mustache.js'/>"></script>
    <script type="text/javascript">
        window.oshop = {
            contextPath: "${pageContext.request.contextPath}"
        }
    </script>


    <decorator:head/>
</head>
<body>
<%--<c:set var="currentMenu" scope="request"><decorator:getProperty property="meta.menu"/></c:set>--%>

<%--<%@include file="/common/header.jsp"%>--%>

<div class="container-fluid">
    <%--<%@ include file="/common/messages.jsp" %>--%>
    <div class="row-fluid">
        <decorator:body/>
    </div>
</div>

<%--<%@include file="/common/footer.jsp"%>--%>
</body>
</html>
