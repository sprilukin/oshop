require([
    'backbone',
    'orders/listOrders/listController'
], function (Backbone, Controller) {

    var controller = new Controller();
    Backbone.history.start();
});
