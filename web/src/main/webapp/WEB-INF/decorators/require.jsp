<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:if test="${param['_js'] != null}">
    <c:set var="scriptSources" scope="session" value="${pageContext.request.contextPath}/resources/${param['_js']}"/>
</c:if>
<c:if test="${scriptSources == null}">
    <c:set var="scriptSources" scope="session" value="${pageContext.request.contextPath}/resources/js"/>
</c:if>

<script src="${scriptSources}/requirejs.config.js"></script>
<script language="JavaScript">
    require.baseUrl = "${scriptSources}";
</script>
<script src="${scriptSources}/lib/require-2.1.5.js"></script>