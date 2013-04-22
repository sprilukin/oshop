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
    'itemCategories/warningView'
], function ($, _, Backbone, Mustache, Model, ListView, EditView, WarningView) {

    var ItemCategoriesRouter = Backbone.Router.extend({

        routes: {
            '': 'list',
            'add': 'edit',
            'edit/:id': 'edit',
            'delete/:id': 'remove'
        },

        list: function() {
            new ListView().render();
        },

        remove: function(id) {
            var that = this;

            var itemCategory = new Model();
            itemCategory.set("id", id);
            itemCategory.destroy({
                wait: true,
                success: function() {
                    that.navigate("", {trigger: true});
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
                    that.navigate("", {trigger: true});
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