require([
    'backbone',
    'orders/listController'
], function (Backbone, Controller) {

    var controller = new Controller();
    Backbone.history.start();
});
