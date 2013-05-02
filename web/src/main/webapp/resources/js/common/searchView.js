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

        initialize: function() {
            this.collection.on("sync", function() {
                this.render();
            }, this);
        },

        render: function () {
            this.$el.html(Mustache.render(searchTemplate, {searchTerm: "test"}));
        }
    });

    return SearchView;
});