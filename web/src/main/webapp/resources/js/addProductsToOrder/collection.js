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
            this.URL_TEMPLATE =
                Mustache.render("/api/orders/{{id}}", {id: options.orderId}) + "/products/withoutOrder/filter;{{filter}}/sort;{{sort}}";

            BaseCollection.prototype.initialize.call(this);
        }
    });
});
