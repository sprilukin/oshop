/**
 * Item Categories module
 */
define([
    'jquery',
    'underscore',
    'backbone',
    'mustache',
    'common/imageGallery',
    'common/messages',
    'common/paginationView',
    'text',
    'text!templates/itemCategories/itemCategories.html',
    'text!templates/itemCategories/editItemCategory.html',
    'bootstrap'
], function ($, _, Backbone, Mustache, imageGallery, messages, PaginationView, text, itemCategoryTemplate) {

    var ItemCategoriesView = Backbone.View.extend({

        el: '#listItemCategories',

        events: {
            "click a.addItemCategory": "addItemCategory",
            "click a.editItemCategory": "editItemCategory",
            "click a.deleteItemCategory": "deleteItemCategory"
        },

        initialize: function() {
            this.collection.on("sync", function() {
                this.render();
            }, this);
        },

        render: function () {
            var model = _.extend({}, this.collection, messages);
            this.$el.html(Mustache.render(itemCategoryTemplate, model));

            new PaginationView(this.collection).render();
        },

        addItemCategory: function(event) {
            this.trigger("add");
            event.preventDefault();
        },

        editItemCategory: function(event) {
            this.trigger("edit", {id: $(event.currentTarget).attr("data-id")});
            event.preventDefault();
        },

        deleteItemCategory: function(event) {
            this.trigger("delete", {id: $(event.currentTarget).attr("data-id")});
            event.preventDefault();
        }
    });

    return ItemCategoriesView;
});