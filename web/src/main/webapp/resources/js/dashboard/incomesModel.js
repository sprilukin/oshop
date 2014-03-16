define([
    'underscore',
    'backbone',
    'mustache',
    'common/context'
], function (_, Backbone, Mustache, context) {

    return Backbone.Model.extend({
        URL_TEMPLATE: "/api/incomes/filter;{{filter}}/sort;{{sort}}/projection;{{projection}}",

        initialize: function(options) {
            options = options || {};

            this.template = context + this.URL_TEMPLATE;
            this.filter = options.filter || "";
            this.sorter = options.sorter || "";
            this.projection = options.projection || "";
        },

        url: function () {
            return Mustache.render(this.template, {
                filter: this.filter,
                sort: this.sorter,
                projection: this.projection
            });
        },

        parse: function(resp, options) {
            return {data: resp};
        },

        setFilterString: function(filter) {
            this.filter = filter;
        },

        setSorterString: function(sorter) {
            this.sorter = sorter;
        },

        setProjectionString: function(projection) {
            this.projection = projection;
        }
    });
});
