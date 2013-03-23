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

    <!-- Add Category Modal -->
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
            <button id="addItemCategoryButton" class="btn btn-primary"><spring:message code="item.category.add"/></button>
        </div>
    </div>

    <!-- Edit Category Modal -->
    <div id="editCategoryModal" class="modal hide" tabindex="-1" role="dialog" aria-labelledby="editCategoryModalLabel" aria-hidden="true">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
            <h3 id="editCategoryModalLabel"><spring:message code="item.category.edit.category"/></h3>
        </div>
        <div class="modal-body">
            <p>
            <div class="control-group form-horizontal">
                <label class="control-label" for="itemCategoryId">#</label>

                <div class="controls">
                    <input class="input-large" id="itemCategoryId" type="text" required="required" disabled="disabled">
                    <span class="help-inline"> </span>
                </div>
            </div>
            <div id="editItemCategoryGroup" class="control-group form-horizontal">
                <label class="control-label" for="addItemCategory"><spring:message code="item.category.name"/></label>

                <div class="controls">
                    <input class="input-large" id="editItemCategory" type="text" required="required">
                    <span class="help-inline"> </span>
                </div>
            </div>
            </p>
        </div>
        <div class="modal-footer">
            <button class="btn" data-dismiss="modal" aria-hidden="true"><spring:message code="general.close"/></button>
            <button id="editItemCategoryButton" class="btn btn-primary"><spring:message code="item.category.edit"/></button>
        </div>
    </div>
</div>

<%-- templates --%>
<script type="template/mustache" id="fieldValidationTemplate">
    <%@include file="validationEntry.jsp"%>
</script>
<script type="template/mustache" id="itemCategoryTemplate">
    <%@include file="tableRow.jsp"%>
</script>
<script type="template/mustache" id="itemCategoriesTemplate">
    <table id="itemCategoriesTable" class="table table-striped table-hover">
        <thead>
        <tr>
            <th class="span1">#</th>
            <th><spring:message code="item.category.column.name"/></th>
            <th class="span1"></th>
        </tr>
        </thead>
        <tbody>
        {{#values}}
        <%@include file="tableRow.jsp"%>
        {{/values}}
        </tbody>
    </table>
</script>

</body>
</html>
