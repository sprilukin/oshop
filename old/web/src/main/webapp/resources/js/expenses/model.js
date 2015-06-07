/**
 * Item Categories module
 */
define([
    'jquery',
    'underscore',
    'backbone',
    'mustache',
    "bundle!messages",
    'common/context'
], function ($, _, Backbone, Mustache, messages, context) {

    return Backbone.Model.extend({

        url: function() {
            return Mustache.render(context + "/api/expenses/{{id}}", {id: this.id});
        },

        validate: function(attributes) {
            var validationFailed = false;
            var validationMessages = {};

            if (!attributes.date) {
                validationMessages["date"] = [messages["ui_validation_not_blank"]];
                validationFailed = true;
            }

            if (!attributes.description) {
                validationMessages["description"] = [messages["ui_validation_not_blank"]];
                validationFailed = true;
            }

            if ("" + parseInt(attributes.amount, 10) !== "" + attributes.amount) {
                validationMessages["amount"] = [messages["ui_validation_not_number"]];
                validationFailed = true;
            }

            return validationFailed ? validationMessages : undefined;
        }
    });
});