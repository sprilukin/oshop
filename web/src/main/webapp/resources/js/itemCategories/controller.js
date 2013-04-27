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
    'common/warningView'
], function ($, _, Backbone, Mustache, Model, Collection, ListView, EditView, WarningView) {

    var ItemCategoriesRouter = Backbone.Router.extend({

        routes: {
            '': 'list',
            'add': 'edit',
            'edit/:id': 'edit',
            'delete/:id': 'remove'
        },

        initialize: function (options) {
            this.controller = options.controller;
        },

        list: function (options) {
            this.controller.list();
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
        this.router = new ItemCategoriesRouter({controller: this});

        this.initialize();
    };

    _.extend(ItemCategoriesController.prototype, {
        initialize: function() {
            var that = this;

            this.listView.on("add",function () {
                that.router.navigate("add", {trigger: true});
            }).on("edit",function (data) {
                that.router.navigate(Mustache.render("edit/{{id}}", {id: data.id}), {trigger: true});
            }).on("delete",function (data) {
                that.router.navigate(Mustache.render("delete/{{id}}", {id: data.id}), {trigger: true, replace: true});
            });

            this.editView.on("close",function () {
                that.router.navigate("", {trigger: true});
            });

            this.router.on("all", function() {
                console.log(arguments);
            })
        },

        list: function () {
            this.collection.fetch();
        },

        remove: function (id) {
            var that = this;

            var itemCategory = new Model();
            itemCategory.set("id", id);
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