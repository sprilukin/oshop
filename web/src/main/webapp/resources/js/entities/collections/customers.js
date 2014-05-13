/**
 * Customers collection
 */
define([
    'backbone',
    '../models/customer'
], function (Backbone, Customer) {

    return Customer.Collection.extend({
        model: Customer,

        url: "api/v2/customers"
    });
});