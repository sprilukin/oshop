/**
 * Item Categories module
 */
define([
    'jquery',
    'underscore',
    'backbone'
], function ($, _, Backbone) {

    return Backbone.View.extend({

        el: '.printSalesReceipt',

        events: {
            "click a": "printSalesReceipt"
        },

        initialize: function(options) {
        },

        printSalesReceipt: function() {
            this.trigger("print:salesReceipt");
        }
    });
});
