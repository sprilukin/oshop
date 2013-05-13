require([
    'backbone',
    'orders/editController'
], function (Backbone, Controller) {

    var controller = new Controller();
    Backbone.history.start();
});
