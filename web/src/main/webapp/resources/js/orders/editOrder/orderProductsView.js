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
    'text!templates/orders/orderProducts.html',
    'text!templates/orders/orderProductsSelectProduct.html',
    'bootstrap'
], function ($, _, Backbone, Mustache, imageGallery, messages, context, DropDownWithSearch, listEntityTemplate, selectProductTemplate) {

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

            this.renderProductsSelect();
        },

        renderProductsSelect: function() {
            this.productsSelect && this.productsSelect.destroy();
            this.productsSelect = new DropDownWithSearch({
                element: $("#field_products"),
                placeholder: messages["order_select_product_placeholder"],
                allowClear: true,
                urlTemplate: context + "/api/products/filter;name={{term}};/sort;",
                formatResult: function(data) {
                    return Mustache.render(selectProductTemplate, _.extend({context: context}, data));
                },
                formatSelection: function(data) {
                    return Mustache.render("{{id}} | {{name}}", data);
                },
                resultParser: function(data) {
                    return data ? data.values : [];
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