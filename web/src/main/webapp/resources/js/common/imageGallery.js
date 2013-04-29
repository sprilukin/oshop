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

    $("#imageGallery").html(Mustache.render(imageGalleryTemplate, {}));
});