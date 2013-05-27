/**
 * Advanced search view
 */
define([
    'jquery',
    'backbone',
    'mustache',
    'common/searchView',
    'text!templates/advancedSearch.html'
], function ($, Backbone, Mustache, SearchView, advancedSearchTemplate) {

    var AdvancedSearchView = SearchView.extend({

        events: {
            "keypress .search-query": "onKeyPress",
            "click .search-button": "search",
            "click .dropdown-menu a": "changeFilter"
        },

        initialize: function(options) {
            SearchView.prototype.initialize.call(this, options);
            this.searchOptions = options.search;
        },

        setActiveFilter: function(field) {
            _.each(this.searchOptions, function (option) {
                if (field === option.field) {
                    option.active = true;
                } else {
                    delete option.active;
                }
            });
        },

        getActiveFilter: function() {
            var filters = this.filter.getAll();
            if (filters && filters.length) {
                return filters[0].name;
            } else {
                return this.searchOptions[0].field;
            }
        },

        render: function () {
            var activeFilter = this.getActiveFilter();
            this.setActiveFilter(activeFilter);

            this.$el.html(Mustache.render(advancedSearchTemplate, {
                fields: this.searchOptions,
                query: this.filter.get(activeFilter)
            }));
        },

        search: function(event) {
            this.changeFilter();
        },

        onKeyPress: function(event) {
            var code = (event.keyCode ? event.keyCode : event.which);
            if (code == 13) {
                this.search(event);
            }
        },

        changeFilter: function(event) {
            var field = event ? $(event.currentTarget).attr("data-field") : this.getActiveFilter();

            this.filter.reset();
            this.filter.set(field, this.$el.find("input.search-query").val());

            event && event.preventDefault();
        }
    });

    return AdvancedSearchView;
});