/**
 * Item Categories module
 */
define([
    'jquery',
    'underscore',
    'backbone',
    'mustache',
    'common/context',
    'customers/model',
    'orders/model',
    'orders/collection',
    'orders/editOrder/editView',
    'orders/editOrder/addView',
    'common/warningView'
], function ($, _, Backbone, Mustache, context, CustomerModel, Model, Collection, EditView, AddView, WarningView) {

    var parseId = function(template) {
        var regexp = Mustache.render(template, {id: "([\\d]+)"}) + "([/#\\?].*)?$";
        var matches = window.location.pathname.match(new RegExp(regexp));
        if (matches && matches.length > 0) {
            return matches[1];
        } else {
            return null;
        }
    };

    var OrderController = function() {
        this.model = new Model();
        this.editView = new EditView({model: this.model});
        this.addView = new AddView({model: this.model});
        this.initialize();
    };

    _.extend(OrderController.prototype, {
        initialize: function() {
            this.addView.on("close:submit", function() {
                window.location = context + "/orders/" +  this.model.id;
            }, this);
            this.addView.on("close:cancel", function() {
                window.location = context + "/orders";
            }, this);

            var orderId = parseId("orders/{{id}}");
            if (orderId) {
                this.edit(orderId);
                return;
            }

            var customerId = parseId("/customers/{{id}}/orders/add");
            this.add(customerId);
        },

        add: function(id) {
            var that = this;

            if (id) {
                var customerModel = new CustomerModel({id: id});
                customerModel.fetch({
                    success: function() {
                        that.model.set("customer", customerModel.attributes, {silent: true});
                        that.addView.render();
                    },
                    error: function(model, xhr) {
                        //new WarningView({model: JSON.parse(xhr.responseText)}).render();
                    }
                });
            } else {
                this.addView.render();
            }
        },

        edit: function(id) {
            this.model.set("id", id, {silent: true});
            this.model.fetch();
        }
    });

    return OrderController;
});