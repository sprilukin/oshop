/**
 * Item Categories module
 */
define([
    'jquery',
    'underscore',
    'backbone',
    'mustache',
    'common/messages'
], function ($, _, Backbone, Mustache, messages) {

    var ProductCategory = Backbone.Model.extend({

        url: function() {
            return Mustache.render("api/productCategories/{{id}}", {id: this.id});
        },

        validate: function(attributes) {
            if (!attributes.name) {
                return messages["ui_validation_not_blank"];
            }
        }
    });

    return ProductCategory;
});