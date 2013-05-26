/**
 * Bootstrap alert wrapped in backbone view
 */
define([
    'jquery',
    'backbone',
    'mustache',
    'text!templates/warning.html',
    'bootstrap'
], function ($, Backbone, Mustache, warningTemplate) {

    var WarningView = Backbone.View.extend({
        el: '.warning',

        events: {
            "closed .alert": "onClosed",
            "click .alert": "close"
        },

        render: function () {
            this.$el.html(Mustache.render(warningTemplate, {warning: this.model}));
            this.$el.find(".alert").alert();
        },

        close: function() {
            this.$el.find(".alert").alert("close");
        },

        onClosed: function() {
            this.undelegateEvents();
        }
    });

    return WarningView;
});