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

    return Backbone.Model.extend({

        url: function() {
            return Mustache.render(context + "/api/products/{{id}}", {id: this.id});
        },

        validate: function(attributes) {
            var validationFailed = false;
            var validationMessages = {};

            if (!attributes.name) {
                validationMessages["name"] = [messages["ui_validation_not_blank"]];
                validationFailed = true;
            }

            if ("" + parseInt(attributes.price, 10) !== attributes.price) {
                validationMessages["price"] = [messages["ui_validation_not_number"]];
                validationFailed = true;
            }

            if (!attributes.category || !attributes.category.id) {
                validationMessages["category"] = [messages["ui_validation_not_blank"]];
                validationFailed = true;
            }

            return validationFailed ? validationMessages : undefined;
        }
    });
});