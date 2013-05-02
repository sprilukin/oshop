/**
 * Bootstrap alert wrapped in backbone view
 */
define([
    'jquery',
    'backbone',
    'mustache',
    'text',
    'text!templates/search.html',
    'bootstrap'
], function ($, Backbone, Mustache, text, searchTemplate) {

    var SearchView = Backbone.View.extend({
        el: '.search',
        query: "",

        events: {
            "submit .form-search": "search"
        },

        initialize: function(options) {
            this.collection.on("sync", function() {
                this.render();
            }, this);

            this.fieldName = options.fieldName;
            this.filter = options.filter;
        },

        render: function () {
            this.$el.html(Mustache.render(searchTemplate, {query: this.filter.get(this.fieldName)}));
        },

        search: function(event) {
            this.query = $(event.currentTarget).find("input.search-query").val();
            this.trigger("search", this.query);
            event.preventDefault();
        }
    });

    return SearchView;
});