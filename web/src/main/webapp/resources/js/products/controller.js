/**
 * Item Categories module
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
    'common/warningView',
    'common/paginationView',
    'common/searchView',
    'common/filter',
    'common/sorter'
], function ($, _, Backbone, Mustache, ProductCategoryModel, Model, Collection, ListView, EditView, WarningView, PaginationView, SearchView, Filter, Sorter) {

    var Router = Backbone.Router.extend({

        routes: {
            '': 'list',
            'list/filter;:filter/sort;:sort/:page': 'list',
            'add': 'edit',
            'edit/:id': 'edit',
            'delete/:id': 'remove'
        },

        initialize: function (options) {
            this.controller = options.controller;
        },

        list: function (filter, sort, page) {
            this.controller.list(filter, sort, page);
        },

        remove: function (id) {
            this.controller.remove(id);
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

    var ProductCategoriesController = function() {
        this.page = 1;
        this.itemsPerPage = 10;
        this.filter = new Filter();
        this.sorter = new Sorter();
        this.productCategoryId = getProductCategoryId();

        this.collection = new Collection({productCategoryId: this.productCategoryId});
        this.listView = new ListView({collection: this.collection, sorter: this.sorter});
        this.editView = new EditView();
        this.paginationView = new PaginationView({collection: this.collection});
        this.searchView = new SearchView({collection: this.collection, filter: this.filter, fieldName: "name"});
        this.router = new Router({controller: this});

        this.initialize();
    };

    _.extend(ProductCategoriesController.prototype, {
        initialize: function() {
            this.listView.on("delete",function (data) {
                this.router.navigate(Mustache.render("delete/{{id}}", {id: data.id}), {trigger: true, replace: true});
            }, this);

            this.editView.on("close",function () {
                this.router.navigate(this.getListUrl(), {trigger: true});
            }, this);

            this.paginationView.on("page:change",function (page) {
                this.page = parseInt(page, 10);
                this.router.navigate(this.getListUrl(), {trigger: true});
            }, this);

            this.filter.on("filter:change", function() {
                this.page = 1;
                this.router.navigate(this.getListUrl(), {trigger: true});
            }, this);

            this.sorter.on("sort:change", function() {
                this.page = 1;
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

        getListUrl: function() {
            return Mustache.render("list/filter;{{filter}}/sort;{{sort}}/{{page}}",
                {filter: this.filter.format(), sort: this.sorter.format(), page: this.page});
        },

        list: function (filter, sort, page) {
            this.page = parseInt(page, 10) || 1;
            this.filter.parse(filter, {silent:true});
            this.sorter.parse(sort, {silent:true});

            this.collection.limit = this.itemsPerPage;
            this.collection.page = this.page;
            this.collection.filter = this.filter.format();
            this.collection.sorter = this.sorter.format();
            this.collection.reset({silent: true});
            this.collection.fetch({data: {limit: this.itemsPerPage, offset: this.page - 1}});
        },

        remove: function (id) {
            var that = this;

            var model = new Model({"id": id});
            model.destroy({
                wait: true,
                success: function () {
                    var maxPageCount = Math.ceil((that.collection.total - 1) / that.collection.limit);
                    that.page = Math.min(that.page, maxPageCount);

                    that.router.navigate(that.getListUrl(), {trigger: true});
                },
                error: function (model, xhr) {
                    new WarningView({model: JSON.parse(xhr.responseText)}).render();
                    that.router.navigate(that.getListUrl(), {trigger: true});
                }
            });
        },

        edit: function (id) {
            var that = this;
            var model = new Model();

            if (id) {
                model.set("id", id);
                model.fetch({
                    wait: true,
                    success: function (model) {
                        that.editView.render(model);
                    }
                });
            } else {
                if (this.productCategoryId) {
                    this.loadProductCategory(function(productCategory) {
                        model.set("category", productCategory);
                        that.editView.render(model);
                    })
                } else {
                    this.editView.render(model);
                }
            }
        }
    });

    return ProductCategoriesController;
});