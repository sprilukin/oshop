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
    'common/filter',
    'common/baseControllerWithListAndEdit'
], function (_, ProductCategoryModel, Model, Collection, ListView, EditView, FilterByOrderStatusesView, messages, AdvancedSearchView, Filter, BaseControllerWithListAndEdit) {

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
        var filter = new Filter();

        this.initialize({
            Model: Model,
            collection: collection,
            View: ListView,
            EditView: EditView,
            filter: filter,
            searchView: new AdvancedSearchView({collection: collection, filter: filter, search: [
                {field: "id", label: messages["products_filter_id_in"]},
                {field: "name", label: messages["products_filter_name_like"]},
                {field: "description", label: messages["products_description_like"]},
                {field: "category", label: messages["products_category_like"]}
            ]})
        });
    };

    _.extend(ProductsController.prototype, BaseControllerWithListAndEdit.prototype, {
        initialize: function(options) {
            BaseControllerWithListAndEdit.prototype.initialize.call(this, options);

            this.filterByOrderStatusesView = new FilterByOrderStatusesView({
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