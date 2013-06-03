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
                var firstExistingFilter = this.findFirstFilter(filters);
                if (firstExistingFilter) {
                    return firstExistingFilter.name;
                }
            }

            var active = _.find(this.searchOptions, function (option) {
                return option.active;
            });

            if (active) {
                return active.field;
            }

            return this.searchOptions[0].field;
        },

        render: function () {
            var activeFilter = this.getActiveFilter();

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

            this.setActiveFilter(field);
            this.resetFilters();
            this.filter.set(field, this.$el.find("input.search-query").val());

            event && event.preventDefault();
        },

        findFirstFilter: function(filters) {
            var searchFieldNames = _.map(this.searchOptions, function(item) {
                return item.field;
            });

            return _.find(filters, function(filter) {
                return _.indexOf(searchFieldNames, filter.name) >= 0 && filter.value;
            }, this);
        },

        resetFilters: function() {
            _.each(this.searchOptions, function(option) {
                this.filter.remove(option.field);
            }, this);
        }
    });

    return AdvancedSearchView;
});