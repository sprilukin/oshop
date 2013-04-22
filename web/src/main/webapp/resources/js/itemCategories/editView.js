/**
 * Item Categories module
 */
define([
    'jquery',
    'underscore',
    'backbone',
    'mustache',
    'itemCategories/model',
    'text',
    'text!templates/editItemCategory.html',
    'bootstrap'
], function ($, _, Backbone, Mustache, Model, text, editItemCategoryTemplate) {

    var EditItemCategoryView = Backbone.View.extend({

        el: '.appContainer',

        events: {
            "click #editItemCategoryButton": "submit",
            "hidden .editItemCategory": "onHidden"
        },

        initialize: function(options) {
            this.model = options.model || new Model();
            this.mode = typeof options.model.id !== "undefined" ? "edit" : "add"
        },

        render: function () {
            var that = this;

            this.$el.html(Mustache.render(editItemCategoryTemplate, {
                title: this.mode,
                nameTitle: "Name",
                close: "Close",
                submit: this.mode,
                model: this.model.attributes
            }));

            var dialog = $(this.$el).find(".editItemCategory");
            dialog.modal({show: true});
        },

        onHidden: function() {
            this.undelegateEvents();
            this.trigger("close");
        },

        submit: function() {
            $(this.$el).find(".editItemCategory").modal("hide");
        }
    });

    return EditItemCategoryView;
});