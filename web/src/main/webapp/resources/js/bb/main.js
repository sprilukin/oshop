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
    'bb/warningView',
    'text',
    'text!templates/itemCategories.html',
    'bootstrap'
], function ($, _, Backbone, Mustache, warningView, text, itemCategoryTemplate) {

    var ItemCategories = Backbone.Collection.extend({
        total: null,
        url: function () {
            return "api/itemCategories/"
        },

        parse: function(json) {
            this.total = json.size;
            return json.values;
        }
    });
    var itemCategories = new ItemCategories();

    var AppView = Backbone.View.extend({
        el: '#itemCategoriesTableContainer',
        render: function () {
            var that = this;
            itemCategories.fetch({
                success: function (collection) {
                    that.$el.html(Mustache.render(itemCategoryTemplate, collection));
                }
            });
        }
    });
    var view = new AppView();

    var AppRouter = Backbone.Router.extend({
        routes: {
            '': 'home',
            'add': 'add',
            'edit/:id': 'edit',
            'delete/:id': 'remove'
        },

        home: function() {
            view.render();
        },

        remove: function(id) {
            var itemCategory = itemCategories.get(id);
            if (itemCategory) {
                itemCategory.destroy({wait: true});
            } else {
                warningView.render(Mustache.render("Item Category {{id}} not found", {id: id}));
                this.navigate("", {trigger: true});
            }
        }
    });

    var router = new AppRouter();
    itemCategories.on('all', function() {
        console.log(arguments);
    });
    itemCategories.on('destroy', function() {
        view.render();
    });

    Backbone.history.start();
});
