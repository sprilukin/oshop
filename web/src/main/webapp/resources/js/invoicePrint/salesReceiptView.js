/**
 * Invoices print View
 */
define([
    'jquery',
    'underscore',
    'backbone',
    'mustache',
    'common/dateFormatter',
    'text!templates/invoicePrint/salesReceipt.html',
], function ($, _, Backbone, Mustache, dateFormatter, template) {


    return Backbone.View.extend({

        el: '.invoicesPlaceholder',

        initialize: function(options) {
            this.returnAddress = options.returnAddress;
            this.collection.on("sync", function() {
                this.render();
            }, this);
        },

        render: function () {
            this.$el.html(Mustache.render(template, this._getModel()));
        },

        _getModel: function() {
            var currentDate = dateFormatter(new Date().getTime()).format();

            this.collection.each(function(model) {
                _.each(model.attributes.products, function(product, index) {
                    product.index = index + 1;
                });
            });

            var model = {
                models: this.collection.models,
                currentDate: currentDate
            };

            return model;
        }
    });
});