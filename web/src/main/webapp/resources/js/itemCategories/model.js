/**
 * Item Categories module
 */
define([
    'jquery',
    'underscore',
    'backbone',
    'mustache'
], function ($, _, Backbone, Mustache) {

    var ItemCategory = Backbone.Model.extend({

        url: function() {
            return Mustache.render("api/itemCategories/{{id}}", {id: this.id});
        },

        validate: function(attributes) {
            console.log(attributes);
        }
    });

    return ItemCategory;
});