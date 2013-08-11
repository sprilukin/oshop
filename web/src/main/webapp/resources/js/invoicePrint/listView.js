/**
 * Invoices print View
 */
define([
    'jquery',
    'underscore',
    'backbone',
    'mustache',
    'text!templates/invoicePrint/list.html',
    'bootstrap'
], function ($, _, Backbone, Mustache, listTemplate) {

    return Backbone.View.extend({

        el: '.invoicesPlaceholder',

        initialize: function(options) {
            this.collection.on("sync", function() {
                this.render();
            }, this);
        },

        render: function () {
            this.$el.html(Mustache.render(listTemplate, this.collection));
        }
    });
});