require([
    'backbone',
    'orders/editOrder/editController'
], function (Backbone, Controller) {

    var controller = new Controller();
    Backbone.history.start();
});
