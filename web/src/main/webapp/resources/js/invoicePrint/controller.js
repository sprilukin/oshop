/**
 * Base List Controller
 */
define([
    'underscore',
    'common/filter',
    'orders/collection',
    'invoicePrint/ukrPostView',
], function (_, Filter, Collection, View) {

    var getOrderIds = function() {
        var matches = window.location.search.match(/\?id=([\d,]+)$/);
        if (matches && matches.length > 0) {
            return matches[1];
        } else {
            return null;
        }
    };

    var Controller = function(options) {
        return this.initialize(options);
    };

    _.extend(Controller.prototype, {

        initialize: function(options) {
            this.collection = new Collection({customerId: null});
            this.filter = new Filter();
            this.listView = new View({collection: this.collection});

            this.filter.set("idEQ", getOrderIds());
            this.collection.setFilterString(this.filter.format());

            return this;
        },

        list: function () {
            this.collection.fetch();
        }
    });

    return Controller;
});