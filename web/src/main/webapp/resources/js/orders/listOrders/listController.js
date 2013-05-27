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
            search: [
                {field: "date", label: "Date equals"},
                {field: "customer", label: "Customer like"},
                {field: "productsCount", label: "Products count ="},
                {field: "productsCountGE", label: "Products count >="},
                {field: "productsCountLE", label: "Products count <="},
                {field: "productsPrice", label: "Products price ="},
                {field: "productsPriceGE", label: "Products price >="},
                {field: "productsPriceLE", label: "Products price <="},
                {field: "totalPrice", label: "Total price ="},
                {field: "totalPriceGE", label: "Total price >="},
                {field: "totalPriceLE", label: "Total price <="},
                {field: "currentOrderStateName", label: "Status like"}
            ]
        });
    };

    _.extend(OrdersController.prototype, BaseListController.prototype);

    return OrdersController;
});