/**
 * Item Categories module
 */
define([
    'jquery',
    'underscore',
    'backbone',
    'mustache',
    'common/messages',
    'common/sortView',
    'common/context',
    'text',
    'text!templates/shippingAddresses/list.html',
    'bootstrap'
], function ($, _, Backbone, Mustache, messages, SortView, context, text, listEntityTemplate) {

    return Backbone.View.extend({

        el: '.listEntities',

        events: {
            "click a.delete": "delete"
        },

        initialize: function(options) {
            this.collection.on("sync", this.render, this);

            this.sorterViews = [];

            _.each(["id", "city", "address", "phone"], function(column) {
                this.sorterViews.push(new SortView({
                    column: column,
                    sorter: options.sorter
                }))
            }, this);
        },

        render: function () {
            var model = _.extend({context: context}, this.collection, messages);
            this.$el.html(Mustache.render(listEntityTemplate, model));

            _.each(this.sorterViews, function(view) {
                view.render();
            });
        },

        delete: function(event) {
            this.trigger("delete", {id: $(event.currentTarget).attr("data-id")});
            event.preventDefault();
        }
    });
});