<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<html>
<head>
    <title><spring:message code="item.categories.title"/></title>
</head>
<body>

<h1><spring:message code="item.categories.title"/></h1>

<form class="" action="/api/images/update?id=3&id=4" method="post" enctype="multipart/form-data">
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

<h1>jquery upload</h1>

<form id="fileupload" class="" action="/api/images/" method="post" enctype="multipart/form-data">
    <label for="file" title="File:"></label>

    <div class="control-group">
        <label class="control-label" for="file">File</label>
        <div class="controls">
            <input name="files[0]" type="file" />
            <button type="submit" class="btn">Submit</button>
        </div>
    </div>
</form>

<div class="fileupload-progress fade">
    <!-- The global progress bar -->
    <div class="progress progress-success progress-striped active" role="progressbar" aria-valuemin="0" aria-valuemax="100" aria-valuenow="0">
        <div class="bar" style="width: 100%;"></div>
    </div>
    <!-- The extended global progress information -->
    <div class="progress-extended">&nbsp;</div>
</div>

<div id="images">
    <%--<img src="/api/images/3" alt="ttt">
    <img src="/api/images/4" alt="ttt">--%>
</div>

<script src="<c:url value='/resources/js/lib/require.js'/>"></script>
<script>
require([
    "jquery",
    "fileupload",
    "underscore",
    "iframeTransport",
    "jqueryUiWidget"
], function($, fileupload, _) {

    $(function() {
        $('#fileupload').fileupload({
            dataType: 'json',
            done: function (e, data) {
                $('.fileupload-progress').removeClass("in");
                _.each(data.result, function (id) {
                    $('<img>').attr("src", "/api/images/" + id).appendTo($("#images"));
                });
            },
            add: function(e, data) {
                $('.fileupload-progress').addClass("in");
                data.submit();
            },
            progressall: function (e, data) {
                var progress = parseInt(data.loaded / data.total * 100, 10);
                $('.fileupload-progress').find('.bar').css('width', progress + '%');
            }
        });
    })
})
</script>
</body>
</html>
