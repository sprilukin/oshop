/**
 * Ajax loader and global error handling
 */
define([
    'jquery',
    'common/warningView'
], function ($, WarningView) {

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

    $(document).ajaxError(function(event, jqxhr, settings, exception) {
        loader.hide();

        var responseText = null;

        try {
            responseText = JSON.parse(jqxhr.responseText);
        } catch (e) {}

        new WarningView({model: responseText ? responseText : exception}).render();
    });

    return $.ajax;
});