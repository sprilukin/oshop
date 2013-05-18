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

        initialize: function() {
            this.model.on("error:addProduct", this.errorAddingProduct, this);
        },

        render: function () {
            var model = _.extend({context: context}, this.model.attributes, messages);
            this.$el.html(Mustache.render(listEntityTemplate, model));
            this.errorPlaceHolder = $("#addProductContainer").find(".help-inline");

            this.productsSelect && this.productsSelect.destroy();
            this.productsSelect = new DropDownWithSearch({
                element: $("#field_products"),
                placeholder: messages["order_select_product_placeholder"],
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
            this.model.deleteProducts($(event.currentTarget).attr("data-id"));
            event.preventDefault();
        },

        add: function(event) {
            this.errorPlaceHolder.text("");

            var productId = $("#field_products").val();
            if (!productId) {
                this.errorPlaceHolder.text(messages["ui_validation_select_product"]);
                return;
            }

            this.model.addProducts(productId);
        },

        errorAddingProduct: function(model, xhr) {
            this.errorPlaceHolder.text(messages["ui_validation_select_product"]);
        }
    });
});