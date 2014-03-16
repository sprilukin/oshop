/**
 * Product Categories module
 */
define([
    'jquery',
    'underscore',
    'backbone',
    'mustache',
    'common/messages',
    'common/fileUploadView',
    'common/context',
    'common/dropDownWithSearch',
    'productCategories/collection',
    'text!products/templates/edit.html',
    'bootstrap',
    'select2'
], function ($, _, Backbone, Mustache, messages, FileUploadView, context, DropDownWithSearch, ProductCategoriesCollection, editEntityTemplate) {

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
            this.submitted = false;
            this.mode = typeof this.model.id !== "undefined" ? "edit" : "add";

            this.model.on("invalid", this.onIvalid, this);
            this.model.on("error", this.onError, this);

            this.$el.html(Mustache.render(editEntityTemplate, _.extend({
                title: this.mode === "add" ? messages["products_add_product"] : messages["products_edit_product"],
                submit: this.mode === "add" ? messages["products_add"] : messages["products_edit"],
                model: this.model.attributes
            }, messages)));

            this.dialog = this.$(".editEntityModal");
            this.dialog.modal({show: true});
            this.$("#field_name").focus();

            this.fileUpload = new FileUploadView({
                element: this.$el.find(".fileUploadGroup .controls"),
                width: "500",
                multiple: false,
                images: this.model.get("imageId") ? [this.model.get("imageId")] : []
            });
            this.fileUpload.render();

            this.productCategorySelect = new DropDownWithSearch({
                element: $("#field_product_category"),
                placeholder: messages["product_category_select_category"],
                allowClear: false,
                urlTemplate: context + "/api/productCategories/filter;name={{term}};/sort;",
                resultParser: function(data) {
                    return data ? _.map(data, function (item) {
                        return {id: item.id, text: item.name}
                    }) : [];
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
            this.productCategorySelect.destroy();

            this.submitted ? this.fileUpload.submit() : this.fileUpload.cancel();

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
                 silent: true,
                 success: function() {
                    that.submitted = true;
                    that.dialog.modal("hide");
                }
            });
        }
    });
});