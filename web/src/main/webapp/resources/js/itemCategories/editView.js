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
    'text!templates/itemCategories.html',
    'text!templates/editItemCategory.html',
    'bootstrap'
], function ($, _, Backbone, Mustache, Model, text, itemCategoryTemplate, editItemCategoryTemplate) {

    var EditItemCategoryView = Backbone.View.extend({

        el: '.appContainer',

        events: {
            "click #editItemCategoryButton": "edit"
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

            this.dialog = $(this.$el).find(".editItemCategory");
            this.dialog.modal({show: true});
            this.dialog.on("hidden", function() {
                that.trigger("close");
            })
        },

        edit: function() {
            this.dialog.modal("hide");
        }
    });

    return EditItemCategoryView;
});