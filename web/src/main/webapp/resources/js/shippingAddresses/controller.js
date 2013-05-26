/**
 * Shipping Address controller
 */
define([
    'underscore',
    'customers/model',
    'shippingAddresses/model',
    'shippingAddresses/collection',
    'shippingAddresses/listView',
    'shippingAddresses/editView',
    'common/baseControllerWithListAndEdit'
], function (_, CustomerModel, Model, Collection, ListView, EditView, BaseControllerWithListAndEdit) {

    var getCustomerId = function() {
        var matches = window.location.pathname.match(/customers\/([\d]+)([\/#\?].*)?$/);
        if (matches && matches.length > 0) {
            return matches[1];
        } else {
            return null;
        }
    };

    var loadCustomer = function(customerId, callback) {
        if (customerId) {
            new CustomerModel({id: customerId}).fetch({
                wait: true,
                success: function (model) {
                    callback && callback(model.attributes);
                }
            });
        } else {
            callback && callback(null);
        }
    };

    var ShippingAddressesController = function() {
        this.customerId = getCustomerId();
        this.initialize({
            EditView: EditView,
            Model: Model,
            collection: new Collection({customerId: this.customerId}),
            View: ListView,
            search: "address"
        });
    };

    _.extend(ShippingAddressesController.prototype, BaseControllerWithListAndEdit.prototype, {
        edit: function (id) {
            this.editView.model.clear({silent: true});

            if (id) {
                this.editView.model.set("id", id, {silent: true});
                this.editView.model.fetch({wait: true});
            } else {
                if (this.customerId) {
                    var that = this;
                    loadCustomer(this.customerId, function(customer) {
                        that.editView.model.set("customer", customer);
                    });
                } else {
                    this.editView.model.trigger("change");
                }
            }
        }
    });

    return ShippingAddressesController;
});