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
    'text!templates/shippingTypes/edit.html',
    'bootstrap'
], function ($, _, Backbone, Mustache, messages, text, editEntityTemplate) {

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
                title: this.mode === "add" ? messages["shipping_type_add_type"] : messages["shipping_type_edit_type"],
                submit: this.mode === "add" ? messages["shipping_type_add"] : messages["shipping_type_edit"],
                model: this.model.attributes
            }, messages)));

            this.dialog = this.$(".editEntityModal");
            this.dialog.modal({show: true});
            this.$("#field_name").focus();
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
            this.trigger("close");
        },

        onKeyPress: function(event) {
            var code = (event.keyCode ? event.keyCode : event.which);
            if (code == 13) {
                this.onSubmit();
            }
        },

        onSubmit: function() {
            var that = this;

            this.hideValidation();

            this.model.save({"name": this.$("#field_name").val()}, {
                wait: true,
                silent: true,
                success: function() {
                    that.dialog.modal("hide");
                }
            });
        }
    });
});