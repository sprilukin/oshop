/**
 * Base List Router
 */
define([
    'underscore',
    'backbone'
], function (_, Backbone) {

    return Backbone.Router.extend({

        routes: {
            '': 'list',
            'list/filter;:filter/sort;:sort/:page': 'list',
            'delete/:id': 'remove'
        },

        initialize: function (options) {
            this.controller = options.controller;
        },

        list: function (filter, sort, page) {
            this.controller.list(filter, sort, page);
        },

        remove: function (id) {
            this.controller.remove(id);
        }
    });
});