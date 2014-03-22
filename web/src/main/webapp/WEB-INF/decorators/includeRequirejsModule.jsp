<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<c:if test="${optimizeJavascript == true}">
    <script type="text/javascript" src="${scriptSources}/${moduleName}.js"></script>
</c:if>
<script type="text/javascript">
    require(["${moduleName}"]);
</script>

