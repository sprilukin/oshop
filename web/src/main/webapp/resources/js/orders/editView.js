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
    'orders/orderProductsView',
    'orders/orderStatesView',
    'customers/model',
    'text',
    'text!templates/orders/edit.html',
    'text!templates/orders/dropDownWithSearchFormat.html',
    'bootstrap',
    'select2'
], function ($, _, Backbone, Mustache, messages, context, DropDownWithSearch, dateFormatter, OrderProductsView, OrderStatesView, CustomersModel, text, editTemplate, dropDownWithSearchFormat) {

    var formatCustomerSelection = function(data) {
        return Mustache.render("{{id}}&nbsp;&nbsp;{{text}}", data);
    };

    var formatShippingAddressSelection = function(data) {
        if (data.text) {
            //initial value
            return data.text;
        } else {
            return Mustache.render("{{type}} | {{address}}", data);
        }
    };

    return Backbone.View.extend({

        el: '.content',

        events: {
            "click .editEntitySubmitButton": "onSubmit",
            "hidden .editEntityModal": "onHidden",
            "keypress .editEntityModal input": "onKeyPress"
        },

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
            this.renderOrderProducts();
            this.renderOrderSates();
        },

        renderCustomerSelect: function() {
            var that = this;
            this.customerSelect && this.customerSelect.destroy();
            this.customerSelect = new DropDownWithSearch({
                element: $("#field_customer"),
                placeholder: "Select customer",
                allowClear: false,
                urlTemplate: context + "/api/customers/filter;name={{term}};/sort;",
                formatResult: formatCustomerSelection,
                resultParser: function(data) {
                    return data ? _.map(data.values, function (item) {
                        return {id: item.id, text: item.name}
                    }) : [];
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
            this.shippingAddressSelect && this.shippingAddressSelect.destroy();
            this.shippingAddressSelect = new DropDownWithSearch({
                element: $("#field_shippingAddress"),
                placeholder: "Select address",
                allowClear: false,
                urlTemplate: Mustache.render(
                    "{{&context}}/api/customers/{{id}}/shippingAddresses/filter;address={{term}};/sort;",
                    {context: context, id: this.model.get("customer").id, term: "{{term}}"}),
                formatResult: formatShippingAddressSelection,
                formatSelection: formatShippingAddressSelection,
                resultParser: function(data) {
                    return data ? _.map(data.values, function (item) {
                        return {id: item.id, address: item.address, type: item.shippingType.name}
                    }) : [];
                },
                change: function(event) {
                    that.model.set("shippingAddress", {id: event.currentTarget.value}, {silent: true});
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
        },

        hideValidation: function() {
            this.$(".control-group").removeClass("error").find(".help-inline").html("");
        },

        renderValidation: function(errors) {
            _.each(errors, function(message, field) {
                this.$(".control-group.field-" + field).addClass("error").find(".help-inline").html(message[0]);
            }, this);
        },

        onIvalid: function(model, error, attrs) {
            this.renderValidation(error);
        },

        onError: function(model, xhr) {
            var validation = JSON.parse(xhr.responseText);
            this.renderValidation(validation.fields);
        },

        onSubmit: function() {
            var that = this;

            this.hideValidation();

            var imageId = _.find(this.fileUpload.getImageIds(), function(id) {
                return true;
            }) || null;

            this.model.save(
                {
                    name: this.$("#field_name").val(),
                    description: this.$("#field_description").val(),
                    imageId: imageId,
                    price: this.$("#field_price").val(),
                    category: {id: $("#field_product_category").val()}
                },

                {wait: true,
                success: function() {
                    that.submitted = true;
                    that.dialog.modal("hide");}
            });
        }
    });
});