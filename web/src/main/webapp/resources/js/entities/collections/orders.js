/**
 * Orders collection
 */
define([
    'backbone',
    '../models/order'
], function (Backbone, Order) {

    return Backbone.Collection.extend({
        model: Order,

        url: "api/v2/orders"
    });
});