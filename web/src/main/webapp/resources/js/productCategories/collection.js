/**
 * Item Categories module
 */
define([
    'backbone',
    'mustache',
    'common/context'
], function (Backbone, Mustache, context) {

    return Backbone.Collection.extend({

        total: null,

        url: function () {
            return Mustache.render(context + "/api/productCategories/filter;{{filter}}/sort;{{sort}}", {
                filter: this.filter,
                sorter: this.sorter
            })
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