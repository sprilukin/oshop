// Require.js allows us to configure shortcut alias
// Their usage will become more apparent futher along in the tutorial.
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

        // Just a short cut so we can put our html outside the js dir
        // When you have HTML/CSS designers this aids in keeping them out of the js directory
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

// Let's kick off the application

require([
    'jquery',
    'underscore',
    'backbone',
    'mustache',
    'text',
    'text!templates/itemCategories.html',
    'bootstrap'
], function($, _, Backbone, Mustache, text, itemCategoryTemplate){

    var AppRouter = Backbone.Router.extend({
        routes: {
            '': 'home'
        }
    });

    var ItemCategoriesCollection = Backbone.Collection.extend({
        url: function () {
            return "api/v2/itemCategories/"
        }
    });

    var AppView = Backbone.View.extend({
        el: '.container',
        render: function () {
            var that = this;
            var itemCategories = new ItemCategoriesCollection();
            itemCategories.fetch({
                success: function (collection) {
                    that.$el.html(Mustache.render(itemCategoryTemplate, {itemCategories: collection.models}));
                }
            });
        }
    });

    var view = new AppView();
    var router = new AppRouter();
    router.on('route:home', function () {
        view.render();
    });

    Backbone.history.start();
    console.log("It works");
});
