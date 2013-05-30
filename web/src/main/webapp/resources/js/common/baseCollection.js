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
        TOTAL_COLLECTION_SIZE_HEADER: "totalListSize",

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
            var that = this;
            var success = function(collection, models, params) {
                that.total = parseInt(params.xhr.getResponseHeader(that.TOTAL_COLLECTION_SIZE_HEADER), 10);
                options && options.success && options.success.apply(that, arguments);
            };

            Backbone.Collection.prototype.fetch.call(
                this, _.extend({data: {limit: this.limit, offset: this.offset}}, options, {success: success})
            );
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
        }
    });
});