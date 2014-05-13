/**
 * Orders model
 */
define([
    "backbone",
    "./customer",
    "bundle!messages"
], function (Backbone, Customer, messages) {

    return Backbone.AssociatedModel.extend({
        relations: [
            {
                type: Backbone.One,
                key: 'customer',
                relatedModel: Customer
            }
        ],

        defaults: {
            currentOrderStateDate: -1,
            currentOrderStateName: "",
            customer: {},
            date: -1,
            discount: {},
            id: undefined,
            products: [],
            productsCount: 0,
            productsPrice: 0,
            shippingAddress: {},
            states: [],
            totalPrice: 0,
            version: 0
        },

        validate: function(attributes) {
            if (!attributes.customer || !attributes.customer.id) {
                return messages["ui_validation_select_customer"];
            }
        }
    });
});