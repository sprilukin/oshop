/**
 * Item Categories module
 */
define([
    'underscore',
    'orders/model',
    'orders/collection',
    'orders/listOrders/listView',
    'common/baseListController',
    'common/advancedSearchView',
    'common/filter'
], function (_, Model, Collection, ListView, BaseListController, AdvancedSearchView, Filter) {

    var OrdersController = function() {
        var collection = new Collection();
        var filter = new Filter();

        this.initialize({
            Model: Model,
            collection: collection,
            View: ListView,
            filter: filter,
            searchView: new AdvancedSearchView({collection: collection, filter: filter, search: [
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
            ]})
        });
    };

    _.extend(OrdersController.prototype, BaseListController.prototype);

    return OrdersController;
});