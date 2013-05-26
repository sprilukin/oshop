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
    'common/baseControllerWithListAndEdit'
], function (_, ProductCategoryModel, Model, Collection, ListView, EditView, BaseControllerWithListAndEdit) {

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

        this.initialize({
            EditView: EditView,
            Model: Model,
            collection: new Collection({productCategoryId: this.productCategoryId}),
            View: ListView,
            search: "name"
        });
    };

    _.extend(ProductsController.prototype, BaseControllerWithListAndEdit.prototype, {
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