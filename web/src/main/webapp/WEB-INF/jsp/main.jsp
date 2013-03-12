<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<html>
<head>
    <title>YYY</title>
    <script type="text/javascript" src="resources/js/jquery-1.8.2.js"></script>
    <script type="text/javascript">
        $(function() {
            $("#fff").on("submit", function() {
                $.ajax("api/itemCategories/add?_method=PUT", {
                    type: "POST",
                    contentType: "application/json",
                    dataType: "json",
                    data: JSON.stringify({"name": "ttt"}),
                    success: function(obj) {
                        console.log(obj)
                    }
                });

                return false;
            });
        })
    </script>
</head>
<body>
    IT WORKS

<form id="fff" action="api/itemCategory/test" method="post">
    <input type="text" name="ttt" value="ddfdfdf">
    <input type="submit" name="submit" value="submit">
</form>
</body>
</html>
