/**
 * Item Categories module
 */
define([
    'jquery',
    'underscore',
    'backbone',
    'mustache',
    'common/messages',
    'common/fileUploadView',
    'itemCategories/model',
    'text',
    'text!templates/itemCategories/editItemCategory.html',
    'bootstrap'
], function ($, _, Backbone, Mustache, messages, FileUploadView, Model, text, editItemCategoryTemplate) {

    var EditItemCategoryView = Backbone.View.extend({

        el: '#editItemCategories',

        events: {
            "click .editItemCategoryButton": "onSubmit",
            "hidden .editItemCategory": "onHidden",
            "keypress .editItemCategory input": "onKeyPress"
        },

        render: function (model) {
            this.model = model;
            this.mode = typeof model.id !== "undefined" ? "edit" : "add";

            this.model.on("invalid", this.onIvalid, this);
            this.model.on("error", this.onError, this);

            this.$el.html(Mustache.render(editItemCategoryTemplate, {
                title: this.mode === "add" ? messages["item_category_add_category"] : messages["item_category_edit_category"],
                nameTitle: messages["item_category_column_name"],
                close: messages["general_close"],
                submit: this.mode === "add" ? messages["item_category_add"] : messages["item_category_edit"],
                model: this.model.attributes
            }));

            this.dialog = this.$el.find(".editItemCategory");
            this.dialog.modal({show: true});
            this.$el.find("#itemCategoryName").focus();

            this.fileUpload = new FileUploadView({
                element: this.$el.find(".fileUploadGroup .controls"),
                width: "150",
                multiple: "true",
                images: this.model.get("images")
            });
            this.fileUpload.render();
        },

        hideValidation: function() {
            this.$el.find(".editItemCategoryGroup").removeClass("error").find(".help-inline").html();
        },

        renderValidation: function(errors) {
            this.$el.find(".editItemCategoryGroup").addClass("error").find(".help-inline").html(errors.name);
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

            if (!this.submitted) {
                this.fileUpload.cancel();
            }
            this.fileUpload.destroy();

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

            this.model.save({"name": this.$el.find("#itemCategoryName").val()}, {
                wait: true,
                success: function() {
                    that.submitted = true;
                    that.dialog.modal("hide");
                }
            });
        }
    });

    return EditItemCategoryView;
});