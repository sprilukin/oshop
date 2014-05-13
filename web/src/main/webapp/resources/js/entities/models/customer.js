/**
 * Customers model
 */
define([
    'backbone',
    "bundle!messages"
], function (Backbone, messages) {

    return Backbone.Model.extend({

        validate: function(attributes) {
            var validationFailed = false;
            var validationMessages = {};

            if (!attributes.name) {
                validationMessages["name"] = [messages["ui_validation_not_blank"]];
                validationFailed = true;
            }

            return validationFailed ? validationMessages : undefined;
        }
    });
});