/**
 * Orders collection
 */
define([
    'backbone',
    'mustache',
    '../models/order'
], function (Backbone, Mustache, Order) {

    return Backbone.Collection.extend({
        model: Order,

        url: "api/v2/orders/"
    });
});