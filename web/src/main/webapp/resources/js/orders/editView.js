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
    'text',
    'text!templates/orders/edit.html',
    'text!templates/orders/dropDownWithSearchFormat.html',
    'bootstrap',
    'select2'
], function ($, _, Backbone, Mustache, messages, context, DropDownWithSearch, dateFormatter, OrderProductsView, text, editTemplate, dropDownWithSearchFormat) {

    var formatSelectionWithId = function(data) {
        return Mustache.render(dropDownWithSearchFormat, {id: data.id, text: data.text});
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

            this.orderProductsView = new OrderProductsView({model: this.model});
        },

        render: function () {
            this.submitted = false;
            this.mode = typeof this.model.id !== "undefined" ? "edit" : "add";

            this.model.on("invalid", this.onIvalid, this);
            this.model.on("error", this.onError, this);

            var formattedDate = dateFormatter.format(this.model.get("date"));
            this.$el.html(Mustache.render(editTemplate, _.extend({
                model: _.extend({}, this.model.attributes, {date: formattedDate})
            }, messages)));

            this.customerSelect && this.customerSelect.destroy();
            this.customerSelect = new DropDownWithSearch({
                element: $("#field_customer"),
                placeholder: "Select customer",
                allowClear: false,
                urlTemplate: context + "/api/customers/filter;name={{term}};/sort;",
                formatResult: formatSelectionWithId,
                resultParser: function(data) {
                    return data ? _.map(data.values, function (item) {
                        return {id: item.id, text: item.name}
                    }) : [];
                }
            });

            this.orderProductsView.setElement(this.$(".orderProducts"));
            this.orderProductsView.render();
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