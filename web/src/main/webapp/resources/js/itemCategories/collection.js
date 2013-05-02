/**
 * Item Categories module
 */
define([
    'jquery',
    'underscore',
    'backbone',
    'mustache'
], function ($, _, Backbone, Mustache) {

    var ItemCategories = Backbone.Collection.extend({

        total: null,

        url: function () {
            return Mustache.render("api/itemCategories/filter;{{filter}}/sort;{{sort}}", {
                filter: this.filter
            })
        },

        parse: function(json) {
            if (json) {
                this.total = json.size;
                return json.values;
            } else {
                this.total = 0;
                return [];
            }
        }
    });

    return ItemCategories;
});