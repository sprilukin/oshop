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

        initialize: function() {
            this.collection.on("sync", function() {
                this.render();
            }, this);
        },

        render: function () {
            this.$el.html(Mustache.render(searchTemplate, {query: this.query}));
        },

        search: function(event) {
            this.query = $(event.currentTarget).find("input.search-query").val();
            this.trigger("search", this.query);
            event.preventDefault();
        }
    });

    return SearchView;
});