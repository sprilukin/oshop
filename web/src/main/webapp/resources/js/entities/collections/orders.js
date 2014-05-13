/**
 * Orders collection
 */
define([
    'backbone',
    'mustache',
    '../models/order'
], function (Backbone, Mustache, Order) {

    return BaseCollection.extend({
        model: Order,

        url: "api/orders"
    });
});