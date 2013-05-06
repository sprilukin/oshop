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
    'text',
    'text!templates/products/edit.html',
    'bootstrap'
], function ($, _, Backbone, Mustache, messages, FileUploadView, text, editEntityTemplate) {

    return Backbone.View.extend({

        el: '.editEntity',

        events: {
            "click .editEntitySubmitButton": "onSubmit",
            "hidden .editEntityModal": "onHidden",
            "keypress .editEntityModal input": "onKeyPress"
        },

        render: function (model) {
            this.submitted = false;
            this.model = model;
            this.mode = typeof model.id !== "undefined" ? "edit" : "add";

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
                width: "150",
                multiple: false,
                images: this.model.get("imageId") ? [this.model.get("imageId")] : []
            });
            this.fileUpload.render();
        },

        hideValidation: function() {
            this.$(".editEntityGroup").removeClass("error").find(".help-inline").html();
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
            this.model = null;

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
                    "name": this.$("#field_name").val(),
                    imageId: imageId,
                    price: this.$("#field_price").val()
                },

                {wait: true,
                success: function() {
                    that.submitted = true;
                    that.dialog.modal("hide");}
            });
        }
    });
});