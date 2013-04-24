/**
 * Item Categories module
 */
define([
    'jquery',
    'underscore',
    'backbone',
    'mustache',
    'itemCategories/messages',
    'itemCategories/collection',
    'text',
    'text!templates/itemCategories/itemCategories.html',
    'text!templates/itemCategories/editItemCategory.html',
    'bootstrap'
], function ($, _, Backbone, Mustache, messages, Collection, text, itemCategoryTemplate) {

    var collection = new Collection();

    var ItemCategoriesView = Backbone.View.extend({

        el: '.appContainer',

        events: {
            "click a.addItemCategory": "addItemCategory",
            "click a.editItemCategory": "editItemCategory",
            "click a.deleteItemCategory": "deleteItemCategory"
        },

        render: function () {
            var that = this;
            collection.fetch({
                success: function (collection) {
                    var model = _.extend({}, collection, messages);
                    that.$el.html(Mustache.render(itemCategoryTemplate, model));
                }
            });
        },

        addItemCategory: function(event) {
            this.trigger("itemCategory:add");
            event.preventDefault();
        },

        editItemCategory: function(event) {
            this.trigger("itemCategory:edit", {id: $(event.currentTarget).attr("data-id")});
            event.preventDefault();
        },

        deleteItemCategory: function(event) {
            this.trigger("itemCategory:delete", {id: $(event.currentTarget).attr("data-id")});
            event.preventDefault();
        }
    });

    return ItemCategoriesView;
});