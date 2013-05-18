<%@ page language="java" errorPage="/exception.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Cache-Control" content="no-store"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="contextPath" content="${pageContext.request.contextPath}">
    <link rel="icon" href="<c:url value='/favicon.ico'/>"/>

    <title><decorator:title/> | <spring:message code="webapp.name"/></title>

    <link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/resources/css/select2.css" />
    <link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/resources/css/bootstrap.css" />
    <link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/<spring:theme code='css'/>"/>
    <link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/resources/css/bootstrap-responsive.css" />
    <link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/resources/css/bootstrap-image-gallery.css" />
    <%--<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/resources/css/jquery-ui-1.10.1.custom.css" />--%>
    <link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/resources/css/main.css" />

    <script language="JavaScript">
        var require = {
            baseUrl: "${pageContext.request.contextPath}/resources/js",

            paths: {
                // Major libraries
                jquery: 'lib/jquery-1.9.1',
                /*jqueryui: 'lib/jquery-ui-1.10.1.custom',*/
                underscore: 'lib/underscore',
                backbone: 'lib/backbone',
                mustache: 'lib/mustache',
                bootstrap: 'lib/bootstrap',
                select2: 'lib/select2',
                jqueryCookie: 'lib/jquery.cookie',
                //fileupload
                fileupload: 'lib/fileup/jquery.fileupload',
                iframeTransport: 'lib/fileup/jquery.iframe-transport',
                jqueryUiWidget: 'lib/fileup/jquery.ui-widget',
                //image-gallery
                "load-image": 'lib/load-image',
                "image-gallery": 'lib/bootstrap-image-gallery',
                // Require.js plugins
                text: 'lib/require.text',
                templates: '../../templates',
                messages: '../../i18n/messages'
            },

            shim: {
                bootstrap: {
                    deps: ["jquery"]
                },
                select2: {
                    deps: ["jquery"]
                },
                underscore: {
                    exports: '_'
                },
                backbone: {
                    deps: ["underscore", "jquery"],
                    exports: "Backbone"
                }
            }
        };
    </script>

    <decorator:head/>
</head>
<body>

<div class="container">
    <div class="navbarPlaceholder"></div>

    <%-- placeholder for warnings --%>
    <div class="warning" style="padding-top: 40px"></div>

    <decorator:body/>
</div>
</body>
</html>
