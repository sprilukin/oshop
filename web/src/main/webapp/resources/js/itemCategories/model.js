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

    var ItemCategory = Backbone.Model.extend({

        url: function() {
            return Mustache.render("api/itemCategories/{{id}}", {id: this.id});
        },

        validate: function(attributes) {
            if (!attributes.name) {
                return messages["ui_validation_not_blank"];
            }
        }
    });

    return ItemCategory;
});