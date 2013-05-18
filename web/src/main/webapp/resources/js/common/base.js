define([
    'underscore',
    'backbone',
    'common/navbarView'
], function (_, Backbone, NavbarView) {

    var Main = function(Controller) {
        this.initialize(Controller);
    };

    _.extend(Main.prototype, {
        initialize: function(Controller) {
            this.navbarView = new NavbarView();
            this.navbarView.render();

            this.controller = new Controller();

            Backbone.history.start();
        }
    });

    return Main;
});
