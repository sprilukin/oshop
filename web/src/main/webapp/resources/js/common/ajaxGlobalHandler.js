/**
 * Ajax loader and global error handling
 */
define([
    'jquery',
    'common/warningView'
], function ($, WarningView) {

    var loader;

    $(document).ajaxStart(function() {
        if (!loader) {
            loader = $(".ajaxLoading");
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