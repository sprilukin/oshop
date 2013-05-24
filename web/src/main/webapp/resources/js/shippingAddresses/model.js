/**
 * Item Categories module
 */
define([
    'jquery',
    'underscore',
    'backbone',
    'mustache',
    'common/messages',
    'common/context'
], function ($, _, Backbone, Mustache, messages, context) {

    var PHONE_REGEXP = /^(\+)?[\d]+$/gi;

    return Backbone.Model.extend({

        url: function() {
            return Mustache.render(context + "/api/shippingAddresses/{{id}}", {id: this.id});
        },

        validate: function(attributes) {
            var validationFailed = false;
            var validationMessages = {};

            if (!attributes.city || !attributes.city.id) {
                validationMessages["city"] = [messages["ui_validation_not_blank"]];
                validationFailed = true;
            }

            if (!attributes.address) {
                validationMessages["address"] = [messages["ui_validation_not_blank"]];
                validationFailed = true;
            }

            if (attributes.phone && !attributes.phone.match(PHONE_REGEXP)) {
                validationMessages["phone"] = [messages["ui_validation_invalid_phone"]];
                validationFailed = true;
            }

            if (!attributes.shippingType || !attributes.shippingType.id) {
                validationMessages["shipping-type"] = [messages["ui_validation_not_blank"]];
                validationFailed = true;
            }

            if (!attributes.customer || !attributes.customer.id) {
                validationMessages["customer"] = [messages["ui_validation_not_blank"]];
                validationFailed = true;
            }

            return validationFailed ? validationMessages : undefined;
        }
    });
});