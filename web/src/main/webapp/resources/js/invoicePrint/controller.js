/**
 * Base List Controller
 */
define([
    'underscore',
    'common/filter',
    'orders/collection'
], function (_, Filter, Collection) {

    var Controller = function(options) {
        return this.initialize(options);
    };

    _.extend(Controller.prototype, {

        initialize: function(options) {
            this.collection = new Collection({customerId: null});
            this.filter = new Filter();

            this.listView = new options.View({collection: this.collection, returnAddress: this.formatReturnAddress()});

            this.filter.set("idEQ", this.getOrderIds());
            this.collection.setFilterString(this.filter.format());

            return this;
        },

        getOrderIds: function() {
            var matches = window.location.search.match(/(\?|&)id=([\d,]+)/);
            if (matches && matches.length > 0) {
                return matches[2];
            } else {
                return null;
            }
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
            var arr = this.getReturnAddress().split("|");
            return {
                recipient: decodeURIComponent(arr[0]),
                address: decodeURIComponent(arr[1]),
                city: decodeURIComponent(arr[2]),
                region: decodeURIComponent(arr[3]),
                postalCode: decodeURIComponent(arr[4])
            }
        },

        list: function () {
            this.collection.fetch();
        }
    });

    return Controller;
});