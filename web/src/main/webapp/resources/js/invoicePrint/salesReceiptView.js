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
            this.returnAddress = options.returnAddress;
            this.collection.on("sync", function() {
                this.render();
            }, this);
        },

        render: function () {
            this.$el.html(
                Mustache.render(frontTemplate, this._getFrontModel()) +
                    Mustache.render(backTemplate, this._getBackModel()) +
                    Mustache.render(addressTemplate, {models: this.collection.models, returnAddress: this.returnAddress})
            );
        },

        _getFrontModel: function() {
            var front = {models: _.filter(this.collection.models, function() {
                return true
            }), returnAddress: this.returnAddress};

            var size = this.collection.size();
            if (size % 4 > 0) {
                for (var i = 0; i < 4 - size % 4; i++) {
                    front.models[front.models.length] = {fake: true}
                }
            }

            return front;
        },

        _getBackModel: function() {
            var back = {models: _.filter(this.collection.models, function() {
                return true
            })};

            var size = this.collection.size();
            if (size % 4 == 1 || size % 4 == 3) {
                back.models[back.models.length - 1] = {fake: true}
                back.models[back.models.length] = this.collection.last();
            }

            return back;
        }
    });
});