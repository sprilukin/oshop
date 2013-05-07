/**
 * Item Categories module
 */
define([
    'backbone',
    'mustache',
    'common/context'
], function (Backbone, Mustache, context) {

    return Backbone.Collection.extend({

        total: null,
        template: null,

        initialize: function(options) {
            if (options.productCategoryId) {
                this.template = context + Mustache.render("/api/productCategories/{{id}}", {id: options.productCategoryId})
                    + "/products/filter;{{filter}}/sort;{{sort}}";
            } else {
                this.template = context + "/api/products/filter;{{filter}}/sort;{{sort}}";
            }
        },

        url: function () {
            return Mustache.render(this.template, {
                filter: this.filter,
                sort: this.sorter
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