<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<html>
<head>
    <title>Item Categories</title>
    <script type="text/javascript">
        $(function () {
            var applyTemplate = function (templateId, model) {
                return Mustache.to_html($("#" + templateId).html(), model);
            };

            $("#addItemCategoryButton").on("click", function () {
                $("#addItemCategoryGroup").removeClass("error").find(".help-inline").html("");

                oshop.itemCategories.add(
                        JSON.stringify({"name": $("#addItemCategory").val()}),
                        function(obj) {
                            console.log(obj);

                            $("#itemCategoriesTable").append(applyTemplate("itemCategoryTemplate", obj));
                            $("#addItemCategory").val("");
                        },
                        function(json, statusCode) {
                            console.log(arguments);

                            $("#addItemCategoryGroup").addClass("error").find(".help-inline").html(json.fields.name);
                        });
            });

            $("#modalB").on("click", function () {
                $("#myModal").modal();
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

    <form class="form-search">
        <div class="input-append">
            <input type="text" class="span10 search-query">
            <button type="submit" class="btn">Search</button>
        </div>
    </form>

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
        </tbody>
    </table>

    <div class="control-group" id="addItemCategoryGroup">
        <label class="control-label" for="addItemCategory"><spring:message code="item.category.name"/></label>
        <div class="controls">
            <input class="input-large" id="addItemCategory" type="text" placeholder="category name" required="required">
            <span class="help-inline"> </span>
        </div>
    </div>

    <button id="addItemCategoryButton" class="btn" type="submit">Add</button>

    <a href="#myModal" id="modalB" role="button" class="btn">Launch demo modal</a>

    <!-- Modal -->
    <div id="myModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
         aria-hidden="true">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
            <h3 id="myModalLabel">Modal header</h3>
        </div>
        <div class="modal-body">
            <p>One fine body…</p>
        </div>
        <div class="modal-footer">
            <button class="btn" data-dismiss="modal" aria-hidden="true">Close</button>
            <button class="btn btn-primary">Save changes</button>
        </div>
    </div>
</div>
</body>
</html>
