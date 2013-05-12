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
            return Mustache.render(context + "/api/orders/{{id}}", {id: this.id});
        }
    });
});