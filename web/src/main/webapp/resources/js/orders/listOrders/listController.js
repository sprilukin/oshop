/**
 * Item Categories module
 */
define([
    'jquery',
    'underscore',
    'backbone',
    'mustache',
    'orders/model',
    'orders/collection',
    'orders/listOrders/listView',
    'common/baseListController',
    'common/baseListRouter'
], function ($, _, Backbone, Mustache, Model, Collection, ListView, BaseListController) {

    var OrdersController = function() {
        this.initialize({
            Model: Model,
            collection: new Collection(),
            View: ListView,
            search: "name"
        });
    };

    _.extend(OrdersController.prototype, BaseListController.prototype);

    return OrdersController;
});