/**
 * Context path
 */
define([
    'jquery'
], function ($) {

    return $('meta[name=contextPath]').attr("content");
});