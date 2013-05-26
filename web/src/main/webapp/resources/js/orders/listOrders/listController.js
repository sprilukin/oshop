/**
 * Item Categories module
 */
define([
    'underscore',
    'orders/model',
    'orders/collection',
    'orders/listOrders/listView',
    'common/baseListController',
    'common/baseListRouter'
], function (_, Model, Collection, ListView, BaseListController) {

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