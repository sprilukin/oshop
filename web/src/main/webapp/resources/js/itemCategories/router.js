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

    var listView = new ListView();
    var warningView = new WarningView();

    var ItemCategoriesRouter = Backbone.Router.extend({

        routes: {
            '': 'list',
            'add': 'add',
            'edit/:id': 'edit',
            'delete/:id': 'remove'
        },

        list: function() {
            listView.render();
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
                    warningView.render(JSON.parse(xhr.responseText));
                    that.navigate("", {trigger: true});
                }
            });
        },

        add: function() {
            var that = this;
            var editView = new EditView({model: new Model()});
            editView.render();
            editView.on("close", function() {
                that.navigate("", {trigger: true});
            });
        }
    });

    return ItemCategoriesRouter;
});