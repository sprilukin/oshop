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
    'text',
    'text!templates/orders/add.html',
    'bootstrap'
], function ($, _, Backbone, Mustache, messages, context, DropDownWithSearch, text, addEntityTemplate) {

    return Backbone.View.extend({

        el: '.content',

        events: {
            "click .editEntitySubmitButton": "onSubmit",
            "hidden .editEntityModal": "onHidden",
            "keypress .editEntityModal input": "onKeyPress"
        },

        render: function () {
            this.model.on("invalid", this.onIvalid, this);
            this.model.on("error", this.onError, this);

            this.$el.html(Mustache.render(addEntityTemplate, _.extend({
                title: messages["product_category_add_category"],
                submit: messages["product_category_add"],
                model: this.model.attributes
            }, messages)));

            this.renderCustomerSelect();

            this.dialog = this.$(".editEntityModal");
            this.dialog.modal({show: true});
            this.$("#field_customer").focus();
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
                placeholder: "Select customer",
                allowClear: false,
                urlTemplate: context + "/api/customers/filter;name={{term}};/sort;",
                formatResult: formatCustomerResult,
                formatSelection: formatCustomerSelection,
                resultParser: function(data) {
                    return data ? data.values : [];
                }
            });
        },

        hideValidation: function() {
            this.$(".editEntityGroup").removeClass("error").find(".help-inline").html("");
        },

        renderValidation: function(errors) {
            this.$(".editEntityGroup").addClass("error").find(".help-inline").html(errors.name);
        },

        onIvalid: function(model, error, attrs) {
            this.renderValidation({name: error});
        },

        onError: function(model, xhr) {
            var validation = JSON.parse(xhr.responseText);
            this.renderValidation({name: validation.fields.name[0]});
        },

        onHidden: function() {
            this.trigger("close:cancel");
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

            this.model.save({"customer": {id: this.$("#field_customer").val()}}, {
                wait: true,
                success: function() {
                    that.dialog.modal("hide");
                    that.trigger("close:submit");
                }
            }, {silent: true});
        }
    });
});