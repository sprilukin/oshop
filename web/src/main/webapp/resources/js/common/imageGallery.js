/**
 * Bootstrap alert wrapped in backbone view
 */
require([
    'jquery',
    'mustache',
    'text',
    'text!templates/imageGallery.html',
    'bootstrap',
    'image-gallery'
], function ($, Mustache, text, imageGalleryTemplate) {

    var imageGallery = $("#imageGallery");

    var removeStyles = function (elem, attrs) {
        var style = elem.attr("style");

        _.each(attrs, function(attr) {
            style = style.replace(new RegExp(attr + "[^;]+;?", "g"), '');
        });

        elem.attr("style", style);
    };

    imageGallery.html(Mustache.render(imageGalleryTemplate, {}));
    imageGallery.on("displayed", function() {
        removeStyles($("#modal-gallery"), ["margin-top", "margin-left"]);
    });
});