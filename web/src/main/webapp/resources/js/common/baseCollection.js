/**
 * Base collection
 */
define([
    'backbone',
    'mustache',
    'common/context'
], function (Backbone, Mustache, context) {

    return Backbone.Collection.extend({

        URL_TEMPLATE: undefined,

        initialize: function(options) {
            this.total = 0;
            this.template = context + this.URL_TEMPLATE;
            this.filter = "";
            this.sorter = "";
            this.limit = undefined;
            this.offset = 0;
        },

        url: function () {
            return Mustache.render(this.template, {filter: this.filter, sort: this.sorter})
        },

        fetch: function(options) {
            Backbone.Collection.prototype.fetch.call(this,
                _.extend({data: {limit: this.limit, offset: this.offset}}, options));
        },

        setFilterString: function(filter) {
            this.filter = filter;
        },

        setSorterString: function(sorter) {
            this.sorter = sorter;
        },

        setLimit: function(limit) {
            this.limit = limit;
        },

        getLimit: function() {
            return this.limit;
        },

        setOffset: function(offset) {
            this.offset = offset;
        },

        getOffset: function() {
            return this.offset;
        },

        getTotal: function() {
            return this.total;
        },

        parse: function(json) {
            if (json) {
                this.total = json.size;
                return json.values;
            } else {
                this.total = 0;
                return [];
            }
        }
    });
});