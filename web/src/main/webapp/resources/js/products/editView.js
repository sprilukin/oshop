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
    'text!templates/products/edit.html',
    'bootstrap'
], function ($, _, Backbone, Mustache, messages, text, editEntityTemplate) {

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

            this.$el.html(Mustache.render(editEntityTemplate, {
                title: this.mode === "add" ? messages["products_add_category"] : messages["products_edit_category"],
                nameTitle: messages["products_column_name"],
                close: messages["general_close"],
                submit: this.mode === "add" ? messages["products_add"] : messages["products_edit"],
                model: this.model.attributes
            }));

            this.dialog = this.$(".editEntityModal");
            this.dialog.modal({show: true});
            this.$("#field_name").focus();
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

            this.model.save({"name": this.$("#field_name").val()}, {
                wait: true,
                success: function() {
                    that.submitted = true;
                    that.dialog.modal("hide");
                }
            });
        }
    });
});