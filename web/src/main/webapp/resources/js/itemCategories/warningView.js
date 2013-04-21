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
        render: function () {
            this.$el.html(Mustache.render(warningTemplate, {warning: this.model}));
            this.$el.find(".alert").alert();
        }
    });

    return WarningView;
});