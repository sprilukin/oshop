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
    'common/context',
    'common/dropDownWithSearch',
    'text',
    'text!templates/orders/orderProducts.html',
    'bootstrap'
], function ($, _, Backbone, Mustache, imageGallery, messages, context, DropDownWithSearch, text, listEntityTemplate) {

    return Backbone.View.extend({

        events: {
            "click a.delete": "delete",
            "click #addProduct": "add"
        },

        initialize: function(options) {
            this.model.on("sync", function() {
                this.render();
            }, this);
        },

        render: function () {
            var model = _.extend({context: context}, this.model.attributes, messages);
            this.$el.html(Mustache.render(listEntityTemplate, model));

            this.productsSelect && this.productsSelect.destroy();
            this.productsSelect = new DropDownWithSearch({
                element: $("#field_products"),
                placeholder: "Select product",
                allowClear: true,
                urlTemplate: context + "/api/products/filter;name={{term}};/sort;",
                formatResult: function(data) {
                    return data.id + "&nbsp;&nbsp;" + data.text;
                },
                resultParser: function(data) {
                    return data ? _.map(data.values, function (item) {
                        return {id: item.id, text: item.name}
                    }) : [];
                }
            });
        },

        delete: function(event) {
            console.log("delete/" + $(event.currentTarget).attr("data-id"));
            this.trigger("delete", {id: $(event.currentTarget).attr("data-id")});
            event.preventDefault();
        },

        add: function(event) {

        }
    });
});