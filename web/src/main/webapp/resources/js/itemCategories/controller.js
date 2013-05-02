/**
 * Item Categories module
 */
define([
    'jquery',
    'underscore',
    'backbone',
    'mustache',
    'itemCategories/model',
    'itemCategories/collection',
    'itemCategories/listView',
    'itemCategories/editView',
    'common/warningView',
    'common/paginationView',
    'common/searchView',
    'common/filter'
], function ($, _, Backbone, Mustache, Model, Collection, ListView, EditView, WarningView, PaginationView, SearchView, Filter) {

    var ItemCategoriesRouter = Backbone.Router.extend({

        routes: {
            '': 'list',
            'list/filter;:filter/:page': 'list',
            'add': 'edit',
            'edit/:id': 'edit',
            'delete/:id': 'remove'
        },

        initialize: function (options) {
            this.controller = options.controller;
        },

        list: function (filter, page) {
            this.controller.list(filter, page);
        },

        remove: function (id) {
            this.controller.remove(id);
        },

        edit: function (id) {
            this.controller.edit(id);
        }
    });

    var ItemCategoriesController = function() {
        this.collection = new Collection();
        this.listView = new ListView({collection: this.collection});
        this.editView = new EditView();
        this.paginationView = new PaginationView({collection: this.collection});
        this.searchView = new SearchView({collection: this.collection});
        this.router = new ItemCategoriesRouter({controller: this});

        this.initialize();
    };

    _.extend(ItemCategoriesController.prototype, {
        initialize: function() {
            var that = this;
            this.page = 1;
            this.filter = new Filter();

            this.listView.on("delete",function (data) {
                this.router.navigate(Mustache.render("delete/{{id}}", {id: data.id}), {trigger: true, replace: true});
            }, this);

            this.editView.on("close",function () {
                this.router.navigate(this.getListUrl(), {trigger: true});
            }, this);

            this.searchView.on("search",function (query) {
                this.filter.set("name", query);
                this.router.navigate(this.getListUrl(), {trigger: true});
            }, this);
        },

        getListUrl: function() {
            return Mustache.render("list/filter;{{filter}}/{{page}}", {filter: this.filter.format(), page: this.page})
        },

        list: function (filter, page) {
            var itemsPerPage = 10;
            this.page = parseInt(page, 10) || this.page;

            this.collection.limit = itemsPerPage;
            this.collection.page = this.page;
            this.collection.filter = this.filter.format();
            this.collection.fetch({data: {limit: itemsPerPage, offset: this.page - 1}});
        },

        remove: function (id) {
            var that = this;

            var itemCategory = new Model({"id": id});
            itemCategory.destroy({
                wait: true,
                success: function () {
                    that.router.navigate(that.getListUrl(), {trigger: true});
                },
                error: function (model, xhr) {
                    new WarningView({model: JSON.parse(xhr.responseText)}).render();
                    that.router.navigate(that.getListUrl(), {trigger: true});
                }
            });
        },

        edit: function (id) {
            var model = new Model();

            if (id) {
                var that = this;

                model.set("id", id);
                model.fetch({
                    wait: true,
                    success: function (model) {
                        that.editView.render(model);
                    }
                });
            } else {
                this.editView.render(model);
            }
        }
    });

    return ItemCategoriesController;
});