require.config({
    baseUrl: "resources/js",
    paths: {
        // Major libraries
        jquery: 'lib/jquery-1.9.1',
        jqueryui: 'lib/jquery-ui-1.10.1.custom',
        underscore: 'lib/underscore',
        backbone: 'lib/backbone',
        mustache: 'lib/mustache',
        bootstrap: 'lib/bootstrap',
        // Require.js plugins
        text: 'lib/require.text',
        templates: '../../templates'
    },
    shim: {
        bootstrap: {
            deps: ["jquery"]
        },
        underscore: {
            exports: '_'
        },
        backbone: {
            deps: ["underscore", "jquery"],
            exports: "Backbone"
        }
    }
});

require([
    'backbone',
    'itemCategories/router'
], function (Backbone, Router) {

    var router = new Router();
    Backbone.history.start();
});
