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

    var collection = new Collection();
    var listView = new ListView({collection: collection});
    var editView = new EditView();

    var ItemCategoriesRouter = Backbone.Router.extend({

        routes: {
            '': 'defineRoute',
            'list': 'list',
            'add': 'edit',
            'edit/:id': 'edit',
            'delete/:id': 'remove'
        },

        initialize: function () {
            var that = this;

            listView.on("add",function () {
                that.navigate("add", {trigger: true});
            }).on("edit",function (data) {
                that.navigate(Mustache.render("edit/{{id}}", {id: data.id}), {trigger: true});
            }).on("delete",function (data) {
                that.navigate(Mustache.render("delete/{{id}}", {id: data.id}));
            });

            editView.on("close",function () {
                that.navigate("list");
                collection.fetch();
            });
        },

        defineRoute: function () {
            var that = this;

            that.navigate("list");
            collection.fetch();
        },

        list: function (options) {
            listView.render();
        },

        remove: function (id) {
            var that = this;

            var itemCategory = new Model();
            itemCategory.set("id", id);
            itemCategory.destroy({
                wait: true,
                success: function () {
                    that.navigate("list");
                    collection.fetch();
                },
                error: function (model, xhr) {
                    new WarningView({model: JSON.parse(xhr.responseText)}).render();
                    that.navigate("list");
                }
            });
        },

        edit: function (id) {
            var that = this;

            var model = new Model();

            if (typeof id !== "undefined") {
                model.set("id", id);
                model.fetch({
                    wait: true,
                    success: function (model) {
                        editView.render(model);
                    }
                });
            } else {
                editView.render(model);
            }
        }
    });

    return ItemCategoriesRouter;
});