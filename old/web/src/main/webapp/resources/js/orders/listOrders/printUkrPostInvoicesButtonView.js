/**
 * Item Categories module
 */
define([
    'jquery',
    'underscore',
    'backbone'
], function ($, _, Backbone) {

    return Backbone.View.extend({

        el: '.printUkrPostInvoices',

        events: {
            "click a": "printInvoices"
        },

        initialize: function(options) {
        },

        printInvoices: function() {
            this.trigger("print:invoices");
        }
    });
});
