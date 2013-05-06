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
    'text!templates/productCategories/productCategories.html',
    'bootstrap'
], function ($, _, Backbone, Mustache, imageGallery, messages, SortView, text, productCategoryTemplate) {

    var ProductCategoriesView = Backbone.View.extend({

        el: '#listProductCategories',

        events: {
            "click a.delete": "delete"
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
            this.$el.html(Mustache.render(productCategoryTemplate, model));

            _.each(this.sorterViews, function(view) {
                view.render();
            });
        },

        delete: function(event) {
            this.trigger("delete", {id: $(event.currentTarget).attr("data-id")});
            event.preventDefault();
        }
    });

    return ProductCategoriesView;
});