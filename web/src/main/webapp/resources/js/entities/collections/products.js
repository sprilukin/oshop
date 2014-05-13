/**
 * Products collection
 */
define([
    'backbone',
    'mustache',
    '../models/product'
], function (Backbone, Product) {

    return Backbone.Collection.extend({
        model: Product,

        url: "api/v2/products"
    });
});