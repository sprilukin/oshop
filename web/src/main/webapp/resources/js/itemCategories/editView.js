/**
 * Item Categories module
 */
define([
    'jquery',
    'underscore',
    'backbone',
    'mustache',
    'common/messages',
    'itemCategories/model',
    'text',
    'text!templates/itemCategories/editItemCategory.html',
    'bootstrap'
], function ($, _, Backbone, Mustache, messages, Model, text, editItemCategoryTemplate) {

    var EditItemCategoryView = Backbone.View.extend({

        el: '.appContainer',

        events: {
            "click #editItemCategoryButton": "onSubmit",
            "hidden .editItemCategory": "onHidden"
        },

        initialize: function(options) {
            this.model = options.model || new Model();
            this.mode = typeof options.model.id !== "undefined" ? "edit" : "add";

            this.model.on("invalid", this.onIvalid, this);
            this.model.on("error", this.onError, this);
        },

        render: function () {
            this.$el.html(Mustache.render(editItemCategoryTemplate, {
                title: this.mode === "add" ? messages["item_category_add_category"] : messages["item_category_edit_category"],
                nameTitle: messages["item_category_column_name"],
                close: messages["general_close"],
                submit: this.mode === "add" ? messages["item_category_add"] : messages["item_category_edit"],
                model: this.model.attributes
            }));

            this.dialog = $(this.$el).find(".editItemCategory");
            this.dialog.modal({show: true});
        },

        hideValidation: function() {
            $("#editItemCategoryGroup").removeClass("error").find(".help-inline").html();
        },

        renderValidation: function(errors) {
            $("#editItemCategoryGroup").addClass("error").find(".help-inline").html(errors.name);
        },

        onIvalid: function(model, error, attrs) {
            this.renderValidation({name: error});
        },

        onError: function(model, xhr) {
            var validation = JSON.parse(xhr.responseText);
            this.renderValidation({name: validation.fields.name[0]});
        },

        onHidden: function() {
            this.undelegateEvents();
            this.trigger("close");
        },

        onSubmit: function() {
            var that = this;

            this.hideValidation();

            this.model.save({"name": $("#editItemCategory").val()}, {
                wait: true,
                success: function() {
                    that.dialog.modal("hide");
                }
            });
        }
    });

    return EditItemCategoryView;
});