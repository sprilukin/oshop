/**
 * Item Categories module
 */
define([
    'backbone',
    'mustache'
], function (Backbone, Mustache) {

    var ItemCategories = Backbone.Collection.extend({

        total: null,

        url: function () {
            return Mustache.render("api/productCategories/filter;{{filter}}/sort;{{sort}}", {
                filter: this.filter,
                sort: this.sort
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