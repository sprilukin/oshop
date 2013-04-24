/**
 * Item Categories module
 */
define([
    'jquery',
    'underscore',
    'backbone',
    'mustache',
    'itemCategories/model',
    'itemCategories/listView',
    'itemCategories/editView',
    'common/warningView'
], function ($, _, Backbone, Mustache, Model, ListView, EditView, WarningView) {

    var ItemCategoriesRouter = Backbone.Router.extend({

        routes: {
            '': 'defineRoute',
            'list': 'list',
            'add': 'edit',
            'edit/:id': 'edit',
            'delete/:id': 'remove'
        },

        defineRoute: function() {
            this.navigate("list", {trigger: true});
        },

        list: function(options) {
            var that = this;

            var listView = new ListView(options);
            listView.on("itemCategory:add", function() {
                that.navigate("add", {trigger: true});
            });
            listView.on("itemCategory:edit", function(data) {
                that.navigate(Mustache.render("edit/{{id}}", {id: data.id}), {trigger: true});
            });
            listView.on("itemCategory:delete", function(data) {
                that.navigate(Mustache.render("delete/{{id}}", {id: data.id}), {trigger: true});
            });
            listView.render();
        },

        remove: function(id) {
            var that = this;

            var itemCategory = new Model();
            itemCategory.set("id", id);
            itemCategory.destroy({
                wait: true,
                success: function() {
                    that.navigate("list", {trigger: true});
                },
                error: function(model, xhr) {
                    new WarningView({model: JSON.parse(xhr.responseText)}).render();
                }
            });
        },

        edit: function(id) {
            var that = this;

            var renderEditView = function(model) {
                var editView = new EditView({model: model});
                editView.render();
                editView.on("close", function() {
                    that.navigate("list", {trigger: true});
                });
            };

            var model = new Model();
            if (typeof id !== "undefined") {
                model.set("id", id);
                model.fetch({
                    wait: true,
                    success: function (model) {
                        renderEditView(model);
                    }
                });
            } else {
                renderEditView(model);
            }
        }
    });

    return ItemCategoriesRouter;
});