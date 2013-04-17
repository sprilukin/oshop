/**
 * Ajax Utility which contains following methods:
 *  - restCall(url, params): perform call to RESTful service
 */
define([
    'jquery',
    'underscore'
], function ($, _) {

    var context = $('meta[name=contextPath]').attr("content");

    var addFakeHttpMetodParam = function(url, params) {
        if (_.indexOf(["GET", "POST"], params.method.toUpperCase()) == -1) {
            url += ((url.indexOf("?") > 0) ? "&" : "?") + "_method=" + params.method;
            params.method = "POST";
        }

        return url;
    };

    var restCall = function (url, params) {
        params = _.extend({
            method: "GET",
            data: {},
            success: function () {},
            fail: function (json, status) {
                console.log("Ajax request failed with status: " + status + " and returns following data:");
                console.log(json);
            }
        }, params);

        //Adding _method to url
        url = addFakeHttpMetodParam(url, params);

        $.ajax(context + "/" + url, {
            type: params.method,
            contentType: "application/json",
            dataType: "json",
            data: params.data
        }).done(function (json, statusAsText, xhr) {
            if (params.success) {
                params.success.call(this, json, xhr.status);
            }
        }).fail(function (xhr, error, statusText) {
            if (params.fail) {
                params.fail.call(this, JSON.parse(xhr.responseText), xhr.status);
            }
        });
    };

    return {
        restCall: restCall
    }
});