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
    'text!templates/itemCategories.html',
    'text!templates/editItemCategory.html',
    'bootstrap'
], function ($, _, Backbone, Mustache, messages, Collection, text, itemCategoryTemplate) {

    var collection = new Collection();

    var ItemCategoriesView = Backbone.View.extend({

        el: '.appContainer',

        render: function () {
            var that = this;
            collection.fetch({
                success: function (collection) {
                    var model = _.extend({}, collection, messages);
                    that.$el.html(Mustache.render(itemCategoryTemplate, model));
                }
            });
        }
    });

    return ItemCategoriesView;
});