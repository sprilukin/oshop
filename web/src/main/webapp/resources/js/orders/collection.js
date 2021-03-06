/**
 * Orders collection
 */
define([
    'backbone',
    'mustache',
    'common/baseCollection'
], function (Backbone, Mustache, BaseCollection) {

    return BaseCollection.extend({
        initialize: function(options) {
            if (options.customerId) {
                this.URL_TEMPLATE = Mustache.render("/api/customers/{{id}}", {id: options.customerId})
                    + "/orders/filter;{{filter}}/sort;{{sort}}";
            } else {
                this.URL_TEMPLATE = "/api/orders/filter;{{filter}}/sort;{{sort}}";
            }

            BaseCollection.prototype.initialize.call(this);
        }
    });
});