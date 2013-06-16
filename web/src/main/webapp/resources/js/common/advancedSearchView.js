/**
 * Advanced search view
 */
define([
    'jquery',
    'backbone',
    'mustache',
    'common/searchView',
    'common/dateFormatter',
    'common/advancedSearchFilters',
    'text!templates/advancedSearch.html'
], function ($, Backbone, Mustache, SearchView, dateFormatter, advancedSearchFilters, advancedSearchTemplate) {

    var AdvancedSearchView = SearchView.extend({

        events: {
            "keypress .search-query": "onKeyPress",
            "click .search-button": "search",
            "click .dropdown-menu a.field": "changeFilter",
            "click .dropdown-menu a.comparison": "changeComparison"
        },

        initialize: function(options) {
            SearchView.prototype.initialize.call(this, options);
            this.searchOptions = options.search;
            this.activeFilter = null;
        },

        setActiveFilter: function(filter) {
            this.activeFilter = filter;
        },

        getActiveFilter: function() {
            var fieldNames = _.map(this.filter.getAll(), function(filter) {
                return filter.name;
            });

            var activeFilter = this.searchOptions.findFirst(fieldNames);

            activeFilter = activeFilter || this.activeFilter || {
                filter: this.searchOptions.filters[0],
                comparison: this.searchOptions.filters[0].dataType.comparisons[0]
            };

            return activeFilter;
        },

        mapFiltersForRendering: function(activeFilter) {
            return _.map(this.searchOptions.filters, function(option) {
                if (option.field === activeFilter.filter.field) {
                    option = _.extend({active: true}, option);
                }

                return option;
            }, this);
        },

        mapActiveFilterForRendering: function(activeFilter) {
            var filterClone = _.extend({}, activeFilter);
            filterClone.filter = _.extend({}, activeFilter.filter);
            filterClone.filter.dataType = _.extend({}, activeFilter.filter.dataType);
            filterClone.filter.dataType.comparisons = _.map(activeFilter.filter.dataType.comparisons, function(comparison) {
                if (comparison.shortLabel === activeFilter.comparison.shortLabel) {
                    return _.extend({active: true}, comparison);
                }

                return comparison;
            });

            return filterClone;
        },

        render: function () {
            var activeFilter = this.getActiveFilter();
            this.setActiveFilter(activeFilter);

            this.$el.html(Mustache.render(advancedSearchTemplate, {
                filters: this.mapFiltersForRendering(activeFilter),
                activeFilter: this.mapActiveFilterForRendering(activeFilter),
                query: this.filter.get(activeFilter.filter.field + activeFilter.comparison.suffix)
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
            var activeFilter = this.getActiveFilter();

            var field = event ? $(event.currentTarget).attr("data-field") : activeFilter.filter.field;
            var filter = this.searchOptions.findByName(field);
            var comparison = event ? filter.dataType.comparisons[0] : activeFilter.comparison;

            this.setActiveFilter({
                filter: filter,
                comparison: comparison
            });
            this.resetFilters();
            this.filter.set(field + comparison.suffix, this.$el.find("input.search-query").val());

            event && event.preventDefault();
        },

        changeComparison: function(event) {
            var activeFilter = this.getActiveFilter();

            var suffix = event ? $(event.currentTarget).attr("data-suffix") : activeFilter.comparison.suffix;
            var comparison = this.searchOptions.findComparisonBySuffix(activeFilter.filter, suffix);

            this.setActiveFilter({
                filter: activeFilter.filter,
                comparison: comparison
            });
            this.resetFilters();
            this.filter.set(activeFilter.filter.field + comparison.suffix, this.$el.find("input.search-query").val());

            event && event.preventDefault();
        },

        resetFilters: function() {
            _.each(this.searchOptions.filters, function(option) {
                _.each(option.dataType.comparisons, function(comparison) {
                    this.filter.remove(option.field + comparison.suffix);
                }, this)
            }, this);
        }
    });

    return AdvancedSearchView;
});