/**
 * Item Categories module
 */
define([
    'backbone',
    'mustache'
], function (Backbone, Mustache) {

    return Backbone.Collection.extend({

        total: null,
        template: null,

        initialize: function(options) {
            if (options.productCategoryId) {
                this.template = Mustache.render("api/productCategories/{{id}}", {id: options.productCategoryId})
                    + "/products/filter;{{filter}}/sort;{{sort}}";
            } else {
                this.template = "api/products/filter;{{filter}}/sort;{{sort}}";
            }
        },

        url: function () {
            return Mustache.render(this.template, {
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
});