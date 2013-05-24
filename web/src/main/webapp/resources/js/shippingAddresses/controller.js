/**
 * Products controller
 */
define([
    'jquery',
    'underscore',
    'backbone',
    'mustache',
    'customers/model',
    'shippingAddresses/model',
    'shippingAddresses/collection',
    'shippingAddresses/listView',
    'shippingAddresses/editView',
    'common/baseListRouter',
    'common/baseListController'
], function ($, _, Backbone, Mustache, CustomerModel, Model, Collection, ListView, EditView, BaseListRouter, BaseListController) {

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

    var getCustomerId = function() {
        var matches = window.location.pathname.match(/customers\/([\d]+)([\/#\?].*)?$/);
        if (matches && matches.length > 0) {
            return matches[1];
        } else {
            return null;
        }
    };

    var ShippingAddressesController = function() {
        this.customerId = getCustomerId();
        this.editView = new EditView({model: new Model()});

        this.initialize({
            Model: Model,
            collection: new Collection({customerId: this.customerId}),
            View: ListView,
            search: "address",
            Router: Router
        });
    };

    _.extend(ShippingAddressesController.prototype, BaseListController.prototype, {
        initEventListeners: function() {
            BaseListController.prototype.initEventListeners.call(this);

            this.editView.on("close",function () {
                this.router.navigate(this.getListUrl(), {trigger: true});
            }, this);
        },

        loadCustomer: function(callback) {
            if (this.customerId) {
                if (!this.customer) {
                    this.customer = new CustomerModel({id: this.customerId});
                    this.customer.fetch({
                        wait: true,
                        success: function (model) {
                            callback && callback(model.attributes);
                        }
                    });
                }
            } else {
                callback && callback(null);
            }
        },

        edit: function (id) {
            var that = this;

            if (id) {
                this.editView.model.clear({silent: true});
                this.editView.model.set("id", id, {silent: true});
                this.editView.model.fetch({wait: true});
            } else {
                if (this.customerId) {
                    this.loadCustomer(function(customer) {
                        that.editView.model.clear({silent: true});
                        that.editView.model.set("customer", customer);
                    });
                } else {
                    this.editView.model.clear({silent: true});
                    this.editView.model.trigger("change");
                }
            }
        }
    });

    return ShippingAddressesController;
});