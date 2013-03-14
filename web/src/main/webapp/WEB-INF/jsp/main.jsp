<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<html>
<head>
    <title>Item Categories</title>
    <script type="text/javascript">
        $(function () {
            var applyTemplate = function(templateId, model) {
                return Mustache.to_html($("#" + templateId).html(), model);
            };

            $("#addItemCategoryButton").on("click", function () {
                $.ajax("api/itemCategories/add?_method=PUT", {
                    type: "POST",
                    contentType: "application/json",
                    dataType: "json",
                    data: JSON.stringify({"name": $("#addItemCategory").val()}),
                    success: function (obj) {
                        console.log(obj);

                        $("#itemCategoriesTable").append(applyTemplate("itemCategoryTemplate", obj));
                    }
                });

                return false;
            });
        })
    </script>
    <script type="template/mustache" id="itemCategoryTemplate">
        <tr>
            <td>{{id}}</td>
            <td>{{name}}</td>
        </tr>
    </script>
    <script type="template/mustache" id="itemCategoriesTemplate">
        {{#itemCategories}}
        <tr>
            <td>{{id}}</td>
            <td>{{name}}</td>
        </tr>
        {{itemCategories}}
    </script>
</head>
<body>

<div class="container">
    <h1>Item Categories</h1>
    <table id="itemCategoriesTable" class="table table-bordered">
        <thead>
        <tr>
            <th class="span1">#</th>
            <th>Category Name</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>1</td>
            <td>Mark</td>
        </tr>
        <tr>
            <td>1</td>
            <td>Mark</td>
        </tr>
        <tr>
            <td>1</td>
            <td>Mark</td>
        </tr>
        <tr>
            <td>1</td>
            <td>Mark</td>
        </tr>
        <tr>
            <td>1</td>
            <td>Mark</td>
        </tr>
        </tbody>
    </table>

    <div class="input-append">
        <input class="input-large" id="addItemCategory" type="text" placeholder="category name">
        <button id="addItemCategoryButton" class="btn" type="submit">Add</button>
    </div>
</div>
</body>
</html>
