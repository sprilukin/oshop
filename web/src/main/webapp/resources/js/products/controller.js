/**
 * Products controller
 */
define([
    'underscore',
    'productCategories/model',
    'products/model',
    'products/collection',
    'products/listView',
    'products/editView',
    'products/filterByOrderStatusesView',
    'common/messages',
    'common/advancedSearchView',
    'common/advancedSearchFilters',
    'common/filter',
    'common/sorter',
    'common/baseControllerWithListAndEdit'
], function (_, ProductCategoryModel, Model, Collection, ListView, EditView, FilterByOrderStatusesView, messages, AdvancedSearchView, AdvancedFilters, Filter, Sorter, BaseControllerWithListAndEdit) {

    var getProductCategoryId = function() {
        var matches = window.location.pathname.match(/productCategories\/([\d]+)([\/#\?].*)?$/);
        if (matches && matches.length > 0) {
            return matches[1];
        } else {
            return null;
        }
    };

    var loadProductCategory = function(productCategoryId, callback) {
        if (productCategoryId) {
            var productCategory = new ProductCategoryModel({id: productCategoryId});
            productCategory.fetch({
                wait: true,
                success: function (model) {
                    callback && callback(model.attributes);
                }
            });
        } else {
            callback && callback(null);
        }
    };

    var ProductsController = function() {
        this.productCategoryId = getProductCategoryId();
        var collection = new Collection({productCategoryId: this.productCategoryId});

        var sorter = new Sorter({
            sorters: [{
                name: "id",
                type: "desc"
            }]
        });

        var filter = new Filter({
            filters: [{
                name: "orderStateIn",
                value: "Новый,Бронь,Оплачено,Возврат,Возвращено,Готов к отправке"
            }]
        });

        this.initialize({
            Model: Model,
            collection: collection,
            View: ListView,
            EditView: EditView,
            filter: filter,
            sorter: sorter,
            searchView: new AdvancedSearchView({collection: collection, filter: filter,
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

    _.extend(ProductsController.prototype, BaseControllerWithListAndEdit.prototype, {
        initialize: function(options) {
            BaseControllerWithListAndEdit.prototype.initialize.call(this, options);

            this.filterByOrderStatusesView = new FilterByOrderStatusesView({
                skipSent: true,
                skipRecieved: true,
                skipPostponed: true,
                collection: this.collection,
                filter: this.filter
            })
        },

        edit: function (id) {
            this.editView.model.clear({silent: true});

            if (id) {
                this.editView.model.set("id", id, {silent: true});
                this.editView.model.fetch({wait: true});
            } else {
                if (this.productCategoryId) {
                    var that = this;
                    loadProductCategory(this.productCategoryId, function(productCategory) {
                        that.editView.model.set("category", productCategory);
                    });
                } else {
                    this.editView.model.trigger("change");
                }
            }
        }
    });

    return ProductsController;
});