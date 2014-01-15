/**
 * Product Categories collection
 */
define([
    'backbone',
    'mustache',
    'common/baseCollection'
], function (Backbone, Mustache, BaseCollection) {

    return BaseCollection.extend({
        URL_TEMPLATE: "/api/incomes/filter;{{filter}}/sort;{{sort}}"
    });
});