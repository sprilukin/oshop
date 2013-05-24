/**
 * Product Categories module
 */
define([
    'jquery',
    'underscore',
    'backbone',
    'mustache',
    'common/messages',
    'common/context',
    'common/dropDownWithSearch',
    'common/dateFormatter',
    'orders/editOrder/orderProductsView',
    'orders/editOrder/orderStatesView',
    'text',
    'text!templates/orders/edit.html',
    'bootstrap',
    'select2'
], function ($, _, Backbone, Mustache, messages, context, DropDownWithSearch, dateFormatter, OrderProductsView, OrderStatesView, text, editTemplate) {

    return Backbone.View.extend({

        el: '.content',

        initialize: function() {
            _.bindAll(this, ["render"]);

            this.model.on("change", this.render, this);
            this.model.on("invalid", this.onIvalid, this);
            this.model.on("error", this.onError, this);

            this.orderProductsView = new OrderProductsView({model: this.model});
            this.orderStatesView = new OrderStatesView({model: this.model});
        },

        render: function () {
            var formattedDate = dateFormatter.format(this.model.get("date"));
            this.$el.html(Mustache.render(editTemplate, _.extend({
                model: _.extend({}, this.model.attributes, {date: formattedDate})
            }, messages)));

            this.renderCustomerSelect();
            this.renderShippingAddressSelect();
            this.renderAdditionalPaymentSelect();
            this.renderDiscountSelect();
            this.renderOrderProducts();
            this.renderOrderSates();
        },

        renderCustomerSelect: function() {
            var that = this;

            var formatCustomerResult = function(data) {
                return Mustache.render("{{id}}&nbsp;&nbsp;{{name}}", data);
            };

            var formatCustomerSelection = function(data) {
                return data.name;
            };

            this.customerSelect && this.customerSelect.destroy();
            this.customerSelect = new DropDownWithSearch({
                element: $("#field_customer"),
                placeholder: messages["order_select_customer_placeholder"],
                allowClear: false,
                urlTemplate: context + "/api/customers/filter;name={{term}};/sort;",
                formatResult: formatCustomerResult,
                formatSelection: formatCustomerSelection,
                resultParser: function(data) {
                    return data ? data.values : [];
                },
                change: function(event) {
                    that.model.set(
                        {"customer": {id: event.currentTarget.value}, "shippingAddress": null},
                        {silent: true});
                    that.model.save();
                }
            });
        },

        renderShippingAddressSelect: function() {
            var that = this;

            var formatSelection = function(data) {
                return Mustache.render("{{type}} | {{city}} | {{address}}", data);
            };

            this.shippingAddressSelect && this.shippingAddressSelect.destroy();
            this.shippingAddressSelect = new DropDownWithSearch({
                element: $("#field_shippingAddress"),
                placeholder: messages["order_select_shippingAddress_placeholder"],
                allowClear: true,
                urlTemplate: Mustache.render(
                    "{{&context}}/api/customers/{{id}}/shippingAddresses/filter;address={{term}};/sort;",
                    {context: context, id: this.model.get("customer").id, term: "{{term}}"}),
                formatResult: formatSelection,
                formatSelection: formatSelection,
                resultParser: function(data) {
                    return data ? _.map(data.values, function (item) {
                        return {id: item.id, city: item.city.name, address: item.address, type: item.shippingType.name}
                    }) : [];
                },
                change: function(event) {
                    var value = event.currentTarget.value;
                    that.model.set("shippingAddress", value ? {id: value} : null, {silent: true});
                    that.model.save();
                }
            });
        },

        renderAdditionalPaymentSelect: function() {
            var that = this;

            var formatSelection = function(data) {
                return Mustache.render("{{amount}} ₴ | {{description}}", data);
            };

            this.additionalPaymentSelect && this.additionalPaymentSelect.destroy();
            this.additionalPaymentSelect = new DropDownWithSearch({
                element: $("#field_additionalPayment"),
                placeholder: messages["order_select_additional_payment_placeholder"],
                allowClear: true,
                urlTemplate: context + "/api/additionalPayments/filter;description={{term}};/sort;",
                formatResult: formatSelection,
                formatSelection: formatSelection,
                resultParser: function(data) {
                    return data ? data.values : [];
                },
                change: function(event) {
                    var value = event.currentTarget.value;
                    that.model.set("additionalPayment", value ? {id: value} : null, {silent: true});
                    that.model.save();
                }
            });
        },

        renderDiscountSelect: function() {
            var that = this;

            var formatSelection = function(data) {
                return Mustache.render("{{amount}} {{typeAsString}} | {{description}}", data);
            };

            this.discountSelect && this.discountSelect.destroy();
            this.discountSelect = new DropDownWithSearch({
                element: $("#field_discount"),
                placeholder: messages["order_select_discount_placeholder"],
                allowClear: true,
                urlTemplate: context + "/api/discounts/filter;description={{term}};/sort;",
                formatResult: formatSelection,
                formatSelection: formatSelection,
                resultParser: function(data) {
                    return data ? data.values : [];
                },
                change: function(event) {
                    var value = event.currentTarget.value;
                    that.model.set("discount", value ? {id: value} : null, {silent: true});
                    that.model.save();
                }
            });
        },

        renderOrderProducts: function() {
            this.orderProductsView.setElement(this.$(".orderProducts"));
            this.orderProductsView.render();
        },

        renderOrderSates: function() {
            this.orderStatesView.setElement(this.$(".orderStates"));
            this.orderStatesView.render();
        }
    });
});