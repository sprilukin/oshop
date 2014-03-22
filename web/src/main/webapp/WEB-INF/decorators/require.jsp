<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:if test="${param['_opt'] != null}">
    <c:set var="optimizeJavascript" scope="session" value="${param['_opt']}"/>
    <c:set var="scriptSources" scope="session" value="${null}"/>
</c:if>
<c:if test="${optimizeJavascript == null}">
    <c:set var="optimizeJavascript" scope="session" value="${true}"/>
    <c:set var="scriptSources" scope="session" value="${null}"/>
</c:if>

<c:if test="${scriptSources == null}">
    <c:if test="${optimizeJavascript == true}">
        <c:set var="scriptSources" scope="session" value="${pageContext.request.contextPath}/resources/opt-js"/>
    </c:if>
    <c:if test="${optimizeJavascript == false}">
        <c:set var="scriptSources" scope="session" value="${pageContext.request.contextPath}/resources/js"/>
    </c:if>
</c:if>

<script src="${scriptSources}/requirejs.config.js"></script>
<script language="JavaScript">
    require.baseUrl = "${scriptSources}";
</script>
<script src="${scriptSources}/lib/require-2.1.5.js"></script>