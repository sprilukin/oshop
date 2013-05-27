/**
 * Bootstrap alert wrapped in backbone view
 */
define([
    'jquery',
    'backbone',
    'mustache',
    'text!templates/search.html',
    'bootstrap'
], function ($, Backbone, Mustache, searchTemplate) {

    var SearchView = Backbone.View.extend({
        el: '.search',

        events: {
            "submit .form-search": "search"
        },

        initialize: function(options) {
            this.collection.on("sync", function() {
                this.render();
            }, this);

            this.field = options.search;
            this.filter = options.filter;
        },

        render: function () {
            this.$el.html(Mustache.render(searchTemplate, {query: this.filter.get(this.field)}));
        },

        search: function(event) {
            var query = this.$el.find("input.search-query").val();
            this.filter.set(this.field, query);
            event.preventDefault();
        }
    });

    return SearchView;
});