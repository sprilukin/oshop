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
            if (!attributes.name) {
                return messages["ui_validation_not_blank"];
            }
        }
    });
});