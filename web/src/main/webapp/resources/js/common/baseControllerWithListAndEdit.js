/**
 * Base controller with list and edit
 */
define([
    'underscore',
    'common/baseListRouter',
    'common/baseListController'
], function (_, BaseListRouter, BaseListController) {

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

    var BaseControllerWithListAndEdit = function(options) {
        this.initialize(options);
    };

    _.extend(BaseControllerWithListAndEdit.prototype, BaseListController.prototype, {

        initialize: function(options) {
            this.editView = options.editView || new options.EditView({model: new options.Model()});

            if (!options.router) {
                options.router = new Router({controller: this});
            }

            BaseListController.prototype.initialize.call(this, options);
        },

        initEventListeners: function() {
            BaseListController.prototype.initEventListeners.call(this);

            this.editView.on("close",function () {
                this.router.navigate(this.getListUrl(), {trigger: true});
            }, this);
        },

        edit: function (id) {
            this.editView.model.clear({silent: true});

            if (id) {
                this.editView.model.set("id", id, {silent: true});
                this.editView.model.fetch({wait: true});
            } else {
                this.editView.model.trigger("change");
            }
        }
    });

    return BaseControllerWithListAndEdit;
});