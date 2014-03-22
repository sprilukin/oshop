<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<html>
<head>
    <title><spring:message code="invoice.print.salesReceipt.title"/></title>
</head>
<body>

<div class="invoicesPlaceholder"></div>

<script type="text/javascript">
    require(["invoicePrint/mainSalesReceipt"]);
</script>
</body>
</html>
