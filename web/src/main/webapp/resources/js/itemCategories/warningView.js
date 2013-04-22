/**
 * Bootstrap alert wrapped in backbone view
 */
define([
    'jquery',
    'backbone',
    'mustache',
    'text',
    'text!templates/warning.html',
    'bootstrap'
], function ($, Backbone, Mustache, text, warningTemplate) {

    var WarningView = Backbone.View.extend({
        el: '.warning',

        events: {
            "closed .alert": "onClosed"
        },

        render: function () {
            this.$el.html(Mustache.render(warningTemplate, {warning: this.model}));
            this.$el.find(".alert").alert();
        },

        onClosed: function() {
            this.undelegateEvents();
        }
    });

    return WarningView;
});