<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<html>
<head>
    <title>jquery upload</title>
</head>
<body>


<h1>Update files</h1>

<form class="" action="/api/images/batch;ids=6,7/update?width=150" method="post" enctype="multipart/form-data">
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
    <img src="/api/images/6">
    <img src="/api/images/7">
</div>

<h1>jquery upload</h1>

<button id="enableUpload" class="btn btn-info">Enable</button>
<button id="disableUpload" class="btn btn-danger">Disable</button>

<div>
    <form id="fileUpload" action="/api/images/?width=150" method="post">
        <input name="files[0]" type="file" multiple="multiple" />
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
    </div>
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
        $('#fileUpload').fileupload({
            dataType: 'json',
            done: function (e, data) {
                $('.fileupload-progress').removeClass("in");
                _.each(data.result, function (id) {
                    $('<img>').attr("src", "/api/images/" + id)/*.attr("width", "50px")*/.appendTo($("#images"));
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

        $("#enableUpload").on("click", function() {
            $('#fileUpload').fileupload('enable');
        });
        $("#disableUpload").on("click", function() {
            $('#fileUpload').fileupload('disable');
        });
    })
})
</script>
</body>
</html>
