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
            this.controller = new Controller();

            this.navbarView = new NavbarView();
            this.navbarView.render();

            Backbone.history.start();
        }
    });

    return Main;
});
