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
    'jquery',
    'underscore',
    'backbone',
    'mustache',
    'text',
    'bb/restApi',
    'text!templates/itemCategories.html',
    'bootstrap'
], function ($, _, Backbone, Mustache, text, restApi, itemCategoryTemplate) {

    var ItemCategoriesCollection = Backbone.Collection.extend({
        total: null,
        url: function () {
            return "api/itemCategories/"
        },

        parse: function(json) {
            this.total = json.size;
            return json.values;
        }

        /*fetch: function (params) {
         var collection = this;

         restApi.itemCategories.list(function(json, status) {
         collection.total = json.size;
         collection[params.reset ? 'reset' : 'set'](json.values, params);
         params.success && params.success(collection, json, params);
         collection.trigger('sync', collection, json, params);
         });
         }*/
    });

    var AppRouter = Backbone.Router.extend({
        routes: {
            '': 'home'
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
});
