require([
    'backbone',
    'productCategories/controller'
], function (Backbone, Controller) {

    var controller = new Controller();
    Backbone.history.start();
});
