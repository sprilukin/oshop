/**
 * Item Categories module
 */
define([
    'underscore',
    'orders/model',
    'orders/collection',
    'orders/listOrders/listView',
    'orders/listOrders/filterByOrderStatusesView',
    'common/baseListController',
    'common/advancedSearchView',
    'common/advancedSearchFilters',
    'common/filter',
    'common/messages'
], function (_, Model, Collection, ListView, FilterByOrderStatusesView, BaseListController, AdvancedSearchView, AdvancedFilters, Filter, messages) {

    var getCustomerId = function() {
        var matches = window.location.pathname.match(/customers\/([\d]+)([\/#\?].*)?$/);
        if (matches && matches.length > 0) {
            return matches[1];
        } else {
            return null;
        }
    };

    var OrdersController = function () {
        var collection = new Collection({customerId: getCustomerId()});
        var filter = new Filter();

        this.initialize({
            Model: Model,
            collection: collection,
            View: ListView,
            filter: filter,
            searchView: new AdvancedSearchView({collection: collection, filter: filter,
                search: new AdvancedFilters([
                    new AdvancedFilters.Filter("date", messages["order_filter_date_eq"], AdvancedFilters.DATE),
                    new AdvancedFilters.Filter("customer", messages["order_filter_customer_like"], AdvancedFilters.STRING),
                    new AdvancedFilters.Filter("productsCount", messages["order_filter_productsCount_eq"], AdvancedFilters.NUMBER),
                    new AdvancedFilters.Filter("productsPrice", messages["order_filter_productsPrice_eq"], AdvancedFilters.NUMBER),
                    new AdvancedFilters.Filter("totalPrice", messages["order_filter_totalPrice_eq"], AdvancedFilters.NUMBER),
                    new AdvancedFilters.Filter("currentOrderStateName", messages["order_filter_currentOrderStateName_like"], AdvancedFilters.STRING),
                    new AdvancedFilters.Filter("currentOrderStateDate", messages["order_filter_currentOrderStateDate_eq"], AdvancedFilters.DATE)
                ])
            })
        });
    };

    _.extend(OrdersController.prototype, BaseListController.prototype, {
        initialize: function(options) {
            BaseListController.prototype.initialize.call(this, options);

            this.filterByOrderStatusesView = new FilterByOrderStatusesView({
                collection: this.collection,
                filter: this.filter
            })
        }
    });

    return OrdersController;
});