/**
 * Product Categories module
 */
define([
    'jquery',
    'underscore',
    'backbone',
    'mustache',
    "bundle!messages",
    'common/settingsStorage',
    'common/dateFormatter',
    'text!expenses/templates/edit.html',
    'bootstrap',
    'datePickerRu'
], function ($, _, Backbone, Mustache, messages, settingsStorage, dateFormatter, editEntityTemplate) {

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
                title: this.mode === "add" ? messages["expenses_add_expense"] : messages["expenses_edit_expense"],
                submit: this.mode === "add" ? messages["expenses_add"] : messages["expenses_edit"],
                model: _.extend({}, this.model.attributes, {date: dateFormatter(this.model.attributes.date).format(messages["common_dateFormat"])})
            }, messages)));

            $('#field_date_container').datepicker({
                format: messages["common_dateFormat"].toLowerCase(),
                weekStart: 1,
                autoclose: true,
                language: settingsStorage.get("lang")
            });

            this.dialog = this.$(".editEntityModal");
            this.dialog.modal({show: true});
            this.$("#field_name").focus();
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

            this.model.save(
                {
                    "date": dateFormatter(this.$("#field_date").val(), messages["common_dateFormat"]).toDate().getTime(),
                    "description": this.$("#field_description").val(),
                    "amount": this.$("#field_amount").val()
                },
                {
                wait: true,
                silent: true,
                success: function() {
                    that.dialog.modal("hide");
                }
            });
        }
    });
});