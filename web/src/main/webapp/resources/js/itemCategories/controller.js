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
    'common/paginationView'
], function ($, _, Backbone, Mustache, Model, Collection, ListView, EditView, WarningView, PaginationView) {

    var ItemCategoriesRouter = Backbone.Router.extend({

        routes: {
            '': 'list',
            'list/:page': 'list',
            'add': 'edit',
            'edit/:id': 'edit',
            'delete/:id': 'remove'
        },

        initialize: function (options) {
            this.controller = options.controller;
        },

        list: function (page) {
            this.controller.list(parseInt(page) || 1);
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
        this.router = new ItemCategoriesRouter({controller: this});

        this.initialize();
    };

    _.extend(ItemCategoriesController.prototype, {
        initialize: function() {
            var that = this;
            this.page = 1;

            this.listView.on("edit",function (data) {
                that.router.navigate(Mustache.render("edit/{{id}}", {id: data.id}), {trigger: true});
            }).on("delete",function (data) {
                that.router.navigate(Mustache.render("delete/{{id}}", {id: data.id}), {trigger: true, replace: true});
            });

            this.editView.on("close",function () {
                that.router.navigate("", {trigger: true});
            });
        },

        list: function (page) {
            var that = this;
            var itemsPerPage = 5;
            this.page = page;

            this.collection.fetch(
                {data: {limit: itemsPerPage, offset: that.page - 1},
                    success: function() {
                        that.collection.limit = itemsPerPage;
                        that.collection.page = that.page;
                    }
                });
        },

        remove: function (id) {
            var that = this;

            var itemCategory = new Model({"id": id});
            itemCategory.destroy({
                wait: true,
                success: function () {
                    that.router.navigate("", {trigger: true});
                },
                error: function (model, xhr) {
                    new WarningView({model: JSON.parse(xhr.responseText)}).render();
                    that.router.navigate("", {trigger: true});
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