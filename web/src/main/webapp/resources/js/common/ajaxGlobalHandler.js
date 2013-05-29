/**
 * Ajax loader and global error handling
 */
define([
    'jquery'
], function ($) {

    var ajaxLoaderMovedToTitle = false;
    var loader = $(".ajaxLoading");

    $(document).ajaxStart(function() {
        if (!ajaxLoaderMovedToTitle) {
            $("h1").append(loader.get(0).outerHTML);
            loader.remove();
            loader = $(".ajaxLoading");
            ajaxLoaderMovedToTitle = true;
        }

        loader.show();
    });

    $(document).ajaxStop(function() {
        loader.hide();
    });

    return $.ajax;
});