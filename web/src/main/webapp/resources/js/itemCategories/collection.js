/**
 * Item Categories module
 */
define([
    'jquery',
    'underscore',
    'backbone',
    'mustache'
], function ($, _, Backbone) {

    var ItemCategories = Backbone.Collection.extend({

        total: null,

        url: function () {
            return "api/itemCategories/"
        },

        parse: function(json) {
            this.total = json.size;
            return json.values;
        }
    });

    return ItemCategories;
});