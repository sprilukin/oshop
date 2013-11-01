/**
 * Item Categories module
 */
define([
    'underscore',
    'orders/model',
    'orders/collection',
    'orders/listOrders/listView',
    'orders/listOrders/filterByOrderStatusesView',
    'orders/listOrders/printInvoicesButtonView',
    'common/selectedModel',
    'common/baseListController',
    'common/advancedSearchView',
    'common/advancedSearchFilters',
    'common/filter',
    'common/sorter',
    'common/messages',
    'common/context'
], function (_, Model, Collection, ListView, FilterByOrderStatusesView, PrintInvoicesButtonView, SelectedModel, BaseListController, AdvancedSearchView, AdvancedFilters, Filter, Sorter, messages, context) {

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
        var sorter = new Sorter();

        this.selectedModel = new SelectedModel();
        var view = new ListView({collection: collection, sorter: sorter, selectedModel: this.selectedModel});

        this.initialize({
            Model: Model,
            collection: collection,
            sorter: sorter,
            view: view,
            filter: filter,
            searchView: new AdvancedSearchView({collection: collection, filter: filter,
                search: new AdvancedFilters([
                    new AdvancedFilters.Filter("date", messages["order_filter_date"], AdvancedFilters.DATE),
                    new AdvancedFilters.Filter("customer", messages["order_filter_customer"], AdvancedFilters.STRING),
                    new AdvancedFilters.Filter("productsCount", messages["order_filter_productsCount"], AdvancedFilters.NUMBER),
                    new AdvancedFilters.Filter("productsPrice", messages["order_filter_productsPrice"], AdvancedFilters.NUMBER),
                    new AdvancedFilters.Filter("totalPrice", messages["order_filter_totalPrice"], AdvancedFilters.NUMBER),
                    new AdvancedFilters.Filter("currentOrderStateName", messages["order_filter_currentOrderStateName"], AdvancedFilters.STRING),
                    new AdvancedFilters.Filter("currentOrderStateDate", messages["order_filter_currentOrderStateDate"], AdvancedFilters.DATE)
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

            this.printInvoicesButtonView = new PrintInvoicesButtonView();
            this.printInvoicesButtonView.on("print:invoices", function() {
                this.printInvoices();
            }, this);
        },

        printInvoices: function() {
            var ids = this.selectedModel.items();
            this.selectedModel.clear();

            if (!ids || ids.length == 0) {
                ids = _.map(this.collection.models, function(model) {
                    return model.attributes.id;
                });
            }

            //TODO: move to profile config
            var defaultReturnAddress = ["Прилукина М. И.", "Капушанская 27/29", "Ужгород", "Закарпатская обл", "88018"];
            window.open(context + "/invoicePrint?id=" + ids.join(",") + "&returnAddress=" + defaultReturnAddress.join("|"), "_blank");
        }
    });

    return OrdersController;
});