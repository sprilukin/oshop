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
    'common/filter',
    'common/messages'
], function (_, Model, Collection, ListView, BaseListController, AdvancedSearchView, Filter, messages) {

    var getCustomerId = function() {
        var matches = window.location.pathname.match(/customers\/([\d]+)([\/#\?].*)?$/);
        if (matches && matches.length > 0) {
            return matches[1];
        } else {
            return null;
        }
    };

    var OrdersController = function() {
        var collection = new Collection({customerId: getCustomerId()});
        var filter = new Filter();

        this.initialize({
            Model: Model,
            collection: collection,
            View: ListView,
            filter: filter,
            searchView: new AdvancedSearchView({collection: collection, filter: filter, search: [
                //{field: "date", label: messages["order_filter_date_eq"]},
                {field: "customer", label: messages["order_filter_customer_like"]},
                {field: "productsCount", label: messages["order_filter_productsCount_eq"]},
                {field: "productsCountGE", label: messages["order_filter_productsCount_ge"]},
                {field: "productsCountLE", label: messages["order_filter_productsCount_le"]},
                {field: "productsPrice", label: messages["order_filter_productsPrice_eq"]},
                {field: "productsPriceGE", label: messages["order_filter_productsPrice_ge"]},
                {field: "productsPriceLE", label: messages["order_filter_productsPrice_le"]},
                {field: "totalPrice", label: messages["order_filter_totalPrice_eq"]},
                {field: "totalPriceGE", label: messages["order_filter_totalPrice_ge"]},
                {field: "totalPriceLE", label: messages["order_filter_totalPrice_le"]},
                {field: "currentOrderStateName", label: messages["order_filter_currentOrderStateName_like"]}
            ]})
        });
    };

    _.extend(OrdersController.prototype, BaseListController.prototype);

    return OrdersController;
});