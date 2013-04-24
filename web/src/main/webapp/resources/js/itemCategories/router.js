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
            'itemCategory/list': 'list',
            'itemCategory/add': 'edit',
            'itemCategory/edit/:id': 'edit',
            'itemCategory/delete/:id': 'remove'
        },

        defineRoute: function() {
            this.navigate("itemCategory/list", {trigger: true});
        },

        list: function(options) {
            var that = this;

            var listView = new ListView(options);
            listView.on("itemCategory:add", function() {
                that.navigate("itemCategory/add", {trigger: true});
            });
            listView.on("itemCategory:edit", function(data) {
                that.navigate("itemCategory/edit/" + data.id, {trigger: true});
            });
            listView.on("itemCategory:delete", function(data) {
                that.navigate("itemCategory/delete/" + data.id, {trigger: true});
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
                    that.navigate("itemCategory/list", {trigger: true});
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
                    that.navigate("itemCategory/list", {trigger: true});
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