/**
 * Edit Shipping Address View
 */
define([
    'jquery',
    'underscore',
    'backbone',
    'mustache',
    'common/messages',
    'common/context',
    'common/dropDownWithSearch',
    'text!templates/shippingAddresses/edit.html',
    'bootstrap',
    'select2'
], function ($, _, Backbone, Mustache, messages, context, DropDownWithSearch, editEntityTemplate) {

    return Backbone.View.extend({

        el: '.editEntity',

        events: {
            "click .editEntitySubmitButton": "onSubmit",
            "hidden .editEntityModal": "onHidden",
            "keypress .editEntityModal input": "onKeyPress"
        },

        initialize: function() {
            this.model.on("change", this.render, this);
        },

        render: function () {
            this.mode = typeof this.model.id !== "undefined" ? "edit" : "add";

            this.model.on("invalid", this.onIvalid, this);
            this.model.on("error", this.onError, this);

            this.$el.html(Mustache.render(editEntityTemplate, _.extend({
                title: this.mode === "add" ? messages["shipping_addresses_add_address"] : messages["shipping_addresses_edit_address"],
                submit: this.mode === "add" ? messages["shipping_addresses_add"] : messages["shipping_addresses_edit"],
                model: this.model.attributes
            }, messages)));

            this.dialog = this.$(".editEntityModal");
            this.dialog.modal({show: true});
            this.$("#field_city").focus();

            this.renderCustomerSelect();
            this.renderShippingType();
            this.renderCitySelect();
        },

        renderCustomerSelect: function() {
            this.customerSelect = new DropDownWithSearch({
                element: $("#field_customer"),
                placeholder: messages["shipping_addresses_select_customer"],
                allowClear: false,
                urlTemplate: context + "/api/customers/filter;name={{term}};/sort;",
                resultParser: function(data) {
                    return data ? _.map(data.values, function (item) {
                        return {id: item.id, text: item.name}
                    }) : [];
                }
            });
        },

        renderShippingType: function() {
            this.shippingTypeSelect = new DropDownWithSearch({
                element: $("#field_shipping_type"),
                placeholder: messages["shipping_addresses_select_shipping_type"],
                allowClear: false,
                urlTemplate: context + "/api/shippingTypes/filter;name={{term}};/sort;",
                resultParser: function(data) {
                    return data ? _.map(data.values, function (item) {
                        return {id: item.id, text: item.name}
                    }) : [];
                }
            });
        },

        renderCitySelect: function() {
            var formatSelection = function(data) {
                return Mustache.render("{{name}} | {{region}}", data);
            };

            this.citySelect = new DropDownWithSearch({
                element: $("#field_city"),
                placeholder: messages["shipping_addresses_select_city"],
                allowClear: false,
                urlTemplate: context + "/api/cities/filter;name={{term}};/sort;",
                formatResult: formatSelection,
                formatSelection: formatSelection,
                resultParser: function(data) {
                    return data ? data.values: [];
                }
            });
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

        onHidden: function() {
            this.customerSelect.destroy();
            this.shippingTypeSelect.destroy();
            this.citySelect.destroy();

            this.trigger("close");
        },

        onKeyPress: function(event) {
            var code = (event.keyCode ? event.keyCode : event.which);
            if(code == 13) {
                this.onSubmit();
            }
        },

        onSubmit: function() {
            var that = this;

            this.hideValidation();

            this.model.save(
                {
                    city: {id: this.$("#field_city").val()},
                    address: this.$("#field_address").val(),
                    phone: this.$("#field_phone").val(),
                    shippingType: {id: this.$("#field_shipping_type").val()},
                    customer: {id: $("#field_customer").val()}
                },

                {wait: true,
                 silent: true,
                 success: function() {
                    that.dialog.modal("hide");
                }
            });
        }
    });
});