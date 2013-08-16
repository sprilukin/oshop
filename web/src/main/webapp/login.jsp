<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<html>
<head>
    <title>Title</title>
</head>
<body>

<div class="hero-unit">
    <c:if test="${not empty param.error}">
        <div class="alert alert-error">
                ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
        </div>
    </c:if>
    <form class="form-horizontal" method="POST" action="<c:url value="/j_spring_security_check" />" >
        <div class="control-group">
            <label class="control-label" for="inputUsername">Username</label>
            <div class="controls">
                <input type="text" id="inputUsername" name="j_username" placeholder="Username">
            </div>
        </div>
        <div class="control-group">
            <label class="control-label" for="inputPassword">Password</label>
            <div class="controls">
                <input type="password" id="inputPassword" name="j_password" placeholder="Password">
            </div>
        </div>
        <div class="control-group">
            <div class="controls">
                <label class="checkbox">
                    <input type="checkbox" name="_spring_security_remember_me"> Remember me
                </label>
                <button type="submit" class="btn btn-primary">Sign in</button>
            </div>
        </div>
    </form>
</div>
</body>
</html>
