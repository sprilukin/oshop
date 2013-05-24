/**
 * Product Categories module
 */
define([
    'jquery',
    'underscore',
    'backbone',
    'mustache',
    'common/messages',
    'common/regions',
    'text',
    'text!templates/cities/edit.html',
    'select2',
    'bootstrap'
], function ($, _, Backbone, Mustache, messages, regions, text, editEntityTemplate) {

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
                title: this.mode === "add" ? messages["cities_add_city"] : messages["cities_edit_city"],
                submit: this.mode === "add" ? messages["cities_add"] : messages["cities_edit"],
                model: this.model.attributes
            }, messages)));

            this.dialog = this.$(".editEntityModal");
            this.dialog.modal({show: true});
            this.$("#field_name").focus();

            this.regionSelect = this.$("#field_region").select2({
                placeholder: messages["cities_select_region"],
                allowClear: false,
                data: _.map(regions, function(value) {
                    return {id: value, text: value};
                }),
                initSelection: function(element, callback) {
                    var el = $(element);
                    var initialValue = {id: el.val(), text: el.attr("data-text")};
                    callback(initialValue)
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
            this.regionSelect.select2("destroy");
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
                {"name": this.$("#field_name").val(),
                    "region": this.$("#field_region").val(),
                    "latitude": this.$("#field_latitude").val(),
                    "longitude": this.$("#field_longitude").val()
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