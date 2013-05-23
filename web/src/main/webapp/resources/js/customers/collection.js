/**
 * Orders collection
 */
define([
    'backbone',
    'mustache',
    'common/baseCollection'
], function (Backbone, Mustache, BaseCollection) {

    return BaseCollection.extend({
        URL_TEMPLATE: "/api/customers/filter;{{filter}}/sort;{{sort}}"
    });
});