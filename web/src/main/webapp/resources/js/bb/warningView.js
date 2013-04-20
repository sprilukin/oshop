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
        render: function (warning) {
            this.$el.html(Mustache.render(warningTemplate, {warning: warning}));
            this.$el.find(".alert").alert();
        }
    });

    return new WarningView();
});