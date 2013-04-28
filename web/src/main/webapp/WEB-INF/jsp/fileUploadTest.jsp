<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<html>
<head>
    <title><spring:message code="item.categories.title"/></title>
</head>
<body>

<h1><spring:message code="item.categories.title"/></h1>

<form class="" action="/api/images/" method="post" enctype="multipart/form-data">
    <label for="file" title="File:"></label>

    <div class="control-group">
        <label class="control-label" for="file">File</label>
        <div class="controls">
            <input id="file" name="files[0]" type="file" />
            <input id="file2" name="files[1]" type="file" />
            <button type="submit" class="btn">Submit</button>
        </div>
    </div>
</form>

<div>
    <img src="/api/images/1" />
    <img src="/api/images/2" />
    <img src="/api/images/3" />
    <img src="/api/images/4" />
</div>
</body>
</html>
