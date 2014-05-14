/**
 * Orders model
 */
define([
    "backbone",
    "bundle!messages"
], function (Backbone, messages) {

    return Backbone.Model.extend({
        validate: function(attributes) {
            if (!attributes.customer || !attributes.customer.id) {
                return messages["ui_validation_select_customer"];
            }
        }
    });
});