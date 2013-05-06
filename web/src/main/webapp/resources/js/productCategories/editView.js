/**
 * Product Categories module
 */
define([
    'jquery',
    'underscore',
    'backbone',
    'mustache',
    'common/messages',
    'text',
    'text!templates/productCategories/editProductCategory.html',
    'bootstrap'
], function ($, _, Backbone, Mustache, messages, text, editProductCategoryTemplate) {

    var EditProductCategoryView = Backbone.View.extend({

        el: '#editProductCategories',

        events: {
            "click .editProductCategoryButton": "onSubmit",
            "hidden .editProductCategory": "onHidden",
            "keypress .editProductCategory input": "onKeyPress"
        },

        render: function (model) {
            this.submitted = false;
            this.model = model;
            this.mode = typeof model.id !== "undefined" ? "edit" : "add";

            this.model.on("invalid", this.onIvalid, this);
            this.model.on("error", this.onError, this);

            this.$el.html(Mustache.render(editProductCategoryTemplate, {
                title: this.mode === "add" ? messages["product_category_add_category"] : messages["product_category_edit_category"],
                nameTitle: messages["product_category_column_name"],
                close: messages["general_close"],
                submit: this.mode === "add" ? messages["product_category_add"] : messages["product_category_edit"],
                model: this.model.attributes
            }));

            this.dialog = this.$(".editProductCategory");
            this.dialog.modal({show: true});
            this.$("#productCategoryName").focus();
        },

        hideValidation: function() {
            this.$el.find(".editProductCategoryGroup").removeClass("error").find(".help-inline").html();
        },

        renderValidation: function(errors) {
            this.$el.find(".editProductCategoryGroup").addClass("error").find(".help-inline").html(errors.name);
        },

        onIvalid: function(model, error, attrs) {
            this.renderValidation({name: error});
        },

        onError: function(model, xhr) {
            var validation = JSON.parse(xhr.responseText);
            this.renderValidation({name: validation.fields.name[0]});
        },

        onHidden: function() {
            this.model = null;

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

            this.model.save({"name": this.$("#productCategoryName").val()}, {
                wait: true,
                success: function() {
                    that.submitted = true;
                    that.dialog.modal("hide");
                }
            });
        }
    });

    return EditProductCategoryView;
});