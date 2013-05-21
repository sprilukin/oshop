/**
 * Product Categories controller
 */
define([
    'jquery',
    'underscore',
    'backbone',
    'orderStates/model',
    'orderStates/collection',
    'orderStates/listView',
    'orderStates/editView',
    'common/baseListRouter',
    'common/baseListController'
], function ($, _, Backbone, Model, Collection, ListView, EditView, BaseListRouter, BaseListController) {

    var Router = BaseListRouter.extend({

        routes: {
            '': 'list',
            'list/filter;:filter/sort;:sort/:page': 'list',
            'add': 'edit',
            'edit/:id': 'edit',
            'delete/:id': 'remove'
        },

        edit: function (id) {
            this.controller.edit(id);
        }
    });

    var ProductCategoriesController = function() {
        this.editView = new EditView({model: new Model()});

        this.initialize({
            Model: Model,
            collection: new Collection(),
            View: ListView,
            search: "name",
            Router: Router
        });
    };

    _.extend(ProductCategoriesController.prototype, BaseListController.prototype, {
        initEventListeners: function() {
            BaseListController.prototype.initEventListeners.call(this);

            this.editView.on("close",function () {
                this.router.navigate(this.getListUrl(), {trigger: true});
            }, this);
        },

        edit: function (id) {
            if (id) {
                this.editView.model.clear({silent: true});
                this.editView.model.set("id", id, {silent: true});
                this.editView.model.fetch({wait: true});
            } else {
                this.editView.model.clear({silent: true});
                this.editView.model.trigger("change");
            }
        }
    });

    return ProductCategoriesController;
});