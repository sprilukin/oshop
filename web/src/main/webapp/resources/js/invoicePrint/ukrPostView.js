/**
 * Invoices print View
 */
define([
    'jquery',
    'underscore',
    'backbone',
    'mustache',
    'text!invoicePrint/templates/front.html',
    'text!invoicePrint/templates/back.html',
    'text!invoicePrint/templates/address.html',
], function ($, _, Backbone, Mustache, frontTemplate, backTemplate, addressTemplate) {


    return Backbone.View.extend({

        el: '.invoicesPlaceholder',

        initialize: function(options) {
            this.returnAddress = this.formatReturnAddress();
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

        getReturnAddress: function() {
            var matches = window.location.search.match(/(\?|&)returnAddress=([^&]+)/);
            if (matches && matches.length > 0) {
                return matches[2];
            } else {
                return null;
            }
        },

        formatReturnAddress: function() {
            var returnAddress = this.getReturnAddress();
            if (!returnAddress) {
                return null;
            }

            var arr = returnAddress.split("|");
            return {
                recipient: decodeURIComponent(arr[0]),
                address: decodeURIComponent(arr[1]),
                city: decodeURIComponent(arr[2]),
                region: decodeURIComponent(arr[3]),
                postalCode: decodeURIComponent(arr[4])
            }
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