/**
 * Products collection
 */
define([
    'backbone',
    'mustache',
    'common/baseCollection'
], function (Backbone, Mustache, BaseCollection) {

    return BaseCollection.extend({

        initialize: function(options) {
            if (options.productCategoryId) {
                this.URL_TEMPLATE = Mustache.render("/api/productCategories/{{id}}", {id: options.productCategoryId})
                    + "/products/filter;{{filter}}/sort;{{sort}}";
            } else {
                this.URL_TEMPLATE = "/api/products/filter;{{filter}}/sort;{{sort}}";
            }

            BaseCollection.prototype.initialize.call(this);
        }
    });
});