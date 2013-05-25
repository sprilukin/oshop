/**
 * Product Categories controller
 */
define([
    'jquery',
    'underscore',
    'backbone',
    'customers/model',
    'customers/collection',
    'customers/listView',
    'customers/editView',
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

    var ListCustomersController = function() {
        this.editView = new EditView({model: new Model()});

        this.initialize({
            Model: Model,
            collection: new Collection(),
            View: ListView,
            search: "name",
            Router: Router
        });
    };

    _.extend(ListCustomersController.prototype, BaseListController.prototype, {
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

    return ListCustomersController;
});