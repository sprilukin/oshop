<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<html>
<head>
    <title><spring:message code="item.categories.title"/></title>
    <script type="text/javascript" src="<c:url value='/resources/js/itemCategories.js'/>"></script>
    <script type="text/javascript">
        $(oshop.itemCategories.init);
    </script>
</head>
<body>

<div class="container">
    <h1><spring:message code="item.categories.title"/></h1>

    <form class="form-search">
        <input type="text" class="search-query" placeholder="<spring:message code="search.help.text"/>">
    </form>

    <div id="itemCategoriesTableContainer">
        <%-- item categories table will be placed here --%>
    </div>

    <a href="#addCategoryModal" role="button" class="btn btn-primary" data-toggle="modal">
        <i class="icon-plus icon-white"> </i>
        <spring:message code="item.category.add"/>
    </a>

    <!-- Modal -->
    <div id="addCategoryModal" class="modal hide" tabindex="-1" role="dialog" aria-labelledby="addCategoryModalLabel" aria-hidden="true">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
            <h3 id="addCategoryModalLabel"><spring:message code="item.category.add.category"/></h3>
        </div>
        <div class="modal-body">
            <p>
            <div id="addItemCategoryGroup" class="control-group form-horizontal">
                <label class="control-label" for="addItemCategory"><spring:message code="item.category.name"/></label>

                <div class="controls">
                    <input class="input-large" id="addItemCategory" type="text" required="required">
                    <span class="help-inline"> </span>
                </div>
            </div>
            </p>
        </div>
        <div class="modal-footer">
            <button class="btn" data-dismiss="modal" aria-hidden="true"><spring:message code="general.close"/></button>
            <button class="btn btn-primary"><spring:message code="item.category.add"/></button>
        </div>
    </div>
</div>

<%-- templates --%>
<script type="template/mustache" id="itemCategoryTemplate">
    <tr>
        <td>{{id}}</td>
        <td>{{name}}</td>
    </tr>
</script>
<script type="template/mustache" id="itemCategoriesTemplate">
    <table id="itemCategoriesTable" class="table table-bordered">
        <thead>
        <tr>
            <th class="span1">#</th>
            <th><spring:message code="item.category.column.name"/></th>
        </tr>
        </thead>
        <tbody>
        {{#values}}
        <tr>
            <td>{{id}}</td>
            <td>{{name}}</td>
        </tr>
        {{/values}}
        </tbody>
    </table>
</script>

</body>
</html>
