/**
 * Invoices print View
 */
define([
    'jquery',
    'underscore',
    'backbone',
    'mustache',
    'text!templates/invoicePrint/front.html',
    'text!templates/invoicePrint/back.html',
    'text!templates/invoicePrint/address.html',
    'bootstrap'
], function ($, _, Backbone, Mustache, frontTemplate, backTemplate, addressTemplate) {


    return Backbone.View.extend({

        el: '.invoicesPlaceholder',

        initialize: function(options) {
            this.collection.on("sync", function() {
                this.render();
            }, this);
        },

        render: function () {
            this.$el.html(
                Mustache.render(frontTemplate, this.collection) +
                    Mustache.render(backTemplate, this.collection) +
                    Mustache.render(addressTemplate, this.collection)
            );
        }
    });
});