require([
    'backbone',
    'itemCategories/router'
], function (Backbone, Router) {

    var router = new Router();
    Backbone.history.start();
});
