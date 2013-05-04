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
    'common/sortView',
    'text',
    'text!templates/itemCategories/itemCategories.html',
    'text!templates/itemCategories/editItemCategory.html',
    'bootstrap'
], function ($, _, Backbone, Mustache, imageGallery, messages, SortView, text, itemCategoryTemplate) {

    var ItemCategoriesView = Backbone.View.extend({

        el: '#listItemCategories',

        events: {
            "click a.deleteItemCategory": "deleteItemCategory"
        },

        initialize: function(options) {
            this.collection.on("sync", function() {
                this.render();
            }, this);

            this.sorterViews = [];

            _.each(["id", "name"], function(column) {
                this.sorterViews.push(new SortView({
                    column: column,
                    sorter: options.sorter
                }))
            }, this);
        },

        render: function () {
            var model = _.extend({}, this.collection, messages);
            this.$el.html(Mustache.render(itemCategoryTemplate, model));

            _.each(this.sorterViews, function(view) {
                view.render();
            });
        },

        deleteItemCategory: function(event) {
            this.trigger("delete", {id: $(event.currentTarget).attr("data-id")});
            event.preventDefault();
        }
    });

    return ItemCategoriesView;
});