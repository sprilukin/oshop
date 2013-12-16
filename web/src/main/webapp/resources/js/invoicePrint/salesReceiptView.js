/**
 * Invoices print View
 */
define([
    'jquery',
    'underscore',
    'backbone',
    'mustache',
    'text!templates/invoicePrint/salesReceipt.html',
], function ($, _, Backbone, Mustache, template) {


    return Backbone.View.extend({

        el: '.invoicesPlaceholder',

        initialize: function(options) {
            this.returnAddress = options.returnAddress;
            this.collection.on("sync", function() {
                this.render();
            }, this);
        },

        render: function () {
            this.$el.html(Mustache.render(template, this.collection));
        }
    });
});