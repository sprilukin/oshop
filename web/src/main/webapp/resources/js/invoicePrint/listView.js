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
    'bootstrap'
], function ($, _, Backbone, Mustache, frontTemplate, backTemplate) {

    return Backbone.View.extend({

        el: '.invoicesPlaceholder',

        initialize: function(options) {
            this.collection.on("sync", function() {
                this.render();
            }, this);
        },

        render: function () {
            this.$el.html(Mustache.render(frontTemplate, this.collection) + Mustache.render(backTemplate, this.collection));
        }
    });
});