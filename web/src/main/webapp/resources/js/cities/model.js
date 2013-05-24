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
            return Mustache.render(context + "/api/cities/{{id}}", {id: this.id});
        },

        validate: function(attributes) {
            var validationFailed = false;
            var validationMessages = {};

            if (!attributes.name) {
                validationMessages["name"] = [messages["ui_validation_not_blank"]];
                validationFailed = true;
            }

            if (!attributes.region) {
                validationMessages["region"] = [messages["ui_validation_not_blank"]];
                validationFailed = true;
            }

            return validationFailed ? validationMessages : undefined;
        }
    });
});