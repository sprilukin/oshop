/**
 * Item Categories module
 */
define([
    'jquery',
    'underscore',
    'backbone'
], function ($, _, Backbone) {

    return Backbone.View.extend({

        el: '.addProducts',

        events: {
            "click a": "addProducts"
        },

        initialize: function(options) {
        },

        addProducts: function() {
            this.trigger("add:products");
        }
    });
});
