/**
 * Products controller
 */
define([
    'underscore',
    'products/model',
    'addProductsToOrder/collection',
    'addProductsToOrder/listView',
    'addProductsToOrder/addProductsButtonView',
    'products/filterByOrderStatusesView',
    'orders/model',
    'common/selectedModel',
    'common/messages',
    'common/advancedSearchView',
    'common/advancedSearchFilters',
    'common/filter',
    'common/sorter',
    'common/baseListRouter',
    'common/baseListController'
], function (_, Model, Collection, ListView, AddProductsButtonView, FilterByOrderStatusesView, OrderModel, SelectedModel, messages, AdvancedSearchView, AdvancedFilters, Filter, Sorter, BaseListRouter, BaseListController) {

    var getOrderId = function() {
        var matches = window.location.pathname.match(/orders\/([\d]+)\/addProducts([\/#\?].*)?$/);
        if (matches && matches.length > 0) {
            return matches[1];
        } else {
            return null;
        }
    };

    var Router = BaseListRouter.extend({

        routes: {
            '': 'list',
            'list/filter;:filter/sort;:sort/:page': 'list'
        }
    });

    var ProductsController = function() {
        this.orderId = getOrderId();
        this.collection = new Collection({orderId: this.orderId});

        var filter = new Filter();
        var sorter = new Sorter();

        this.selectedModel = new SelectedModel();
        var listView = new ListView({
            collection: this.collection,
            sorter: sorter,
            selectedModel: this.selectedModel
        });

        this.initialize({
            Model: Model,
            collection: this.collection,
            view: listView,
            filter: filter,
            sorter: sorter,
            router: new Router({controller: this}),
            searchView: new AdvancedSearchView({collection: this.collection, filter: filter,
                search: new AdvancedFilters([
                    new AdvancedFilters.Filter("id", messages["products_filter_id"], AdvancedFilters.NUMBER),
                    new AdvancedFilters.Filter("name", messages["products_filter_name"], AdvancedFilters.STRING),
                    new AdvancedFilters.Filter("description", messages["products_filter_description"], AdvancedFilters.STRING),
                    new AdvancedFilters.Filter("category", messages["products_filter_category"], AdvancedFilters.STRING),
                    new AdvancedFilters.Filter("price", messages["products_filter_price"], AdvancedFilters.NUMBER)
                ])
            })
        });
    };

    _.extend(ProductsController.prototype, BaseListController.prototype, {
        initialize: function(options) {
            BaseListController.prototype.initialize.call(this, options);

            this.orderModel = new OrderModel({id: this.orderId});
            this.orderModel.on("change", function() {
                this.collection.fetch();
            }, this);

            this.addProductsButtonView = new AddProductsButtonView();
            this.addProductsButtonView.on("add:products", function() {
                this.addProducts();
            }, this);

            this.filterByOrderStatusesView = new FilterByOrderStatusesView({
                collection: this.collection,
                filter: this.filter
            })
        },

        addProducts: function() {
            var ids = this.selectedModel.items();
            this.selectedModel.clear();
            this.orderModel.addProducts.apply(this.orderModel, ids);
        }
    });

    return ProductsController;
});
