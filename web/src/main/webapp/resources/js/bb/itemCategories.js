/**
 * Item Categories module
 */
define([
    'jquery',
    'underscore',
    'backbone',
    'mustache',
    'bb/warningView',
    'text',
    'text!templates/itemCategories.html',
    'text!templates/editItemCategory.html',
    'bootstrap'
], function ($, _, Backbone, Mustache, warningView, text, itemCategoryTemplate, editItemCategoryTemplate) {

    var ItemCategories = Backbone.Collection.extend({

        total: null,

        url: function () {
            return "api/itemCategories/"
        },

        parse: function(json) {
            this.total = json.size;
            return json.values;
        }
    });
    var collection = new ItemCategories();

    var AppView = Backbone.View.extend({

        el: '.appContainer',

        render: function () {
            var that = this;
            collection.fetch({
                success: function (collection) {
                    that.$el.html(Mustache.render(itemCategoryTemplate, collection));
                }
            });
        }
    });
    var view = new AppView();

    var EditItemCategoryView = Backbone.View.extend({

        el: '.appContainer',

        render: function (options) {
            this.$el.html(Mustache.render(editItemCategoryTemplate, _.extend({
                title: "Add/Edit",
                nameTitle: "Name",
                close: "Close",
                submit: "Add/Edit"
            }, options.model)));

            var dialog = $(this.$el).find(".editItemCategory");
            dialog.modal({show: true});
            dialog.on("hidden", function() {
                options.onClose && options.onClose();
            })
        }
    });
    var editItemCategoryView = new EditItemCategoryView();

    var ItemCategoriesRouter = Backbone.Router.extend({

        routes: {
            '': 'home',
            'add': 'add',
            'edit/:id': 'edit',
            'delete/:id': 'remove'
        },

        home: function() {
            view.render();
        },

        remove: function(id) {
            var that = this;

            var itemCategory = collection.get(id);
            if (itemCategory) {
                itemCategory.destroy({
                    wait: true,
                    success: function() {
                        that.navigate("", {trigger: true});
                    }
                });
            } else {
                warningView.render(Mustache.render("Item Category with id {{id}} not found", {id: id}));
                this.navigate("", {trigger: true});
            }
        },

        add: function() {
            var that = this;
            editItemCategoryView.render({mode: "add", onClose: function() {
                that.navigate("", {trigger: true});
            }});
        }
    });
    var router = new ItemCategoriesRouter();

    return {
        router: router,
        collection: collection,
        view: view
    };
});