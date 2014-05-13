define(function(require){
    var Orders = require("entities/collections/orders"),
        _ = require("underscore"),
        Backbone = require("backbone");

    var Start = function() {
        this.initialize();
    };

    _.extend(Start.prototype, {
        initialize: function() {
            this.orders = new Orders();
            this.orders.on("sync", this.render, this);
            this.orders.fetch();
        },

        render: function() {
            console.log(this.orders);
        }
    });

    return Start;
});
