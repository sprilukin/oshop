/**
 * Item Categories module
 */
define([
    'jquery',
    'underscore',
    'backbone',
    'mustache',
    'orders/model',
    'orders/collection',
    'orders/listOrders/listView',
    'common/warningView',
    'common/paginationView',
    'common/searchView',
    'common/filter',
    'common/sorter'
], function ($, _, Backbone, Mustache, Model, Collection, ListView, WarningView, PaginationView, SearchView, Filter, Sorter) {

    var Router = Backbone.Router.extend({

        routes: {
            '': 'list',
            'list/filter;:filter/sort;:sort/:page': 'list',
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
        }
    });

    var ProductCategoriesController = function() {
        this.page = 1;
        this.itemsPerPage = 10;
        this.filter = new Filter();
        this.sorter = new Sorter();

        this.collection = new Collection();
        this.listView = new ListView({collection: this.collection, sorter: this.sorter});
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
        }
    });

    return ProductCategoriesController;
});