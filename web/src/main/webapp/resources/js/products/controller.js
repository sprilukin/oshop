/**
 * Products controller
 */
define([
    'jquery',
    'underscore',
    'backbone',
    'mustache',
    'productCategories/model',
    'products/model',
    'products/collection',
    'products/listView',
    'products/editView',
    'common/baseListRouter',
    'common/baseListController'
], function ($, _, Backbone, Mustache, ProductCategoryModel, Model, Collection, ListView, EditView, BaseListRouter, BaseListController) {

    var Router = BaseListRouter.extend({

        routes: {
            '': 'list',
            'list/filter;:filter/sort;:sort/:page': 'list',
            'add': 'edit',
            'edit/:id': 'edit',
            'delete/:id': 'remove'
        },

        edit: function (id) {
            this.controller.edit(id);
        }
    });

    var getProductCategoryId = function() {
        var matches = window.location.pathname.match(/productCategories\/([\d]+)([\/#\?].*)?$/);
        if (matches && matches.length > 0) {
            return matches[1];
        } else {
            return null;
        }
    };

    var ProductsController = function() {
        this.productCategoryId = getProductCategoryId();
        this.editView = new EditView({model: new Model()});

        this.initialize({
            Model: Model,
            collection: new Collection({productCategoryId: this.productCategoryId}),
            View: ListView,
            search: "name",
            Router: Router
        });
    };

    _.extend(ProductsController.prototype, BaseListController.prototype, {
        initEventListeners: function() {
            BaseListController.prototype.initEventListeners.call(this);

            this.editView.on("close",function () {
                this.router.navigate(this.getListUrl(), {trigger: true});
            }, this);
        },

        loadProductCategory: function(callback) {
            if (this.productCategoryId) {
                if (!this.productCategory) {
                    this.productCategory = new ProductCategoryModel({id: this.productCategoryId});
                    this.productCategory.fetch({
                        wait: true,
                        success: function (model) {
                            callback && callback(model.attributes);
                        }
                    });
                }
            } else {
                callback && callback(null);
            }
        },

        edit: function (id) {
            var that = this;

            if (id) {
                this.editView.model.clear({silent: true});
                this.editView.model.set("id", id, {silent: true});
                this.editView.model.fetch({wait: true});
            } else {
                if (this.productCategoryId) {
                    this.loadProductCategory(function(productCategory) {
                        that.editView.model.clear({silent: true});
                        that.editView.model.set("category", productCategory);
                    });
                } else {
                    this.editView.model.clear({silent: true});
                    this.editView.model.trigger("change");
                }
            }
        }
    });

    return ProductsController;
});