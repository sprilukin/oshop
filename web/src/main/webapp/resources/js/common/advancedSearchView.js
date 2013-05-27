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

            this.field = this.searchOptions;

            if (typeof this.searchOptions !== "string") {
                var filters = this.filter.getAll();
                if (filters && filters.length) {
                    this.field = filters[0].name;
                } else {
                    this.field = this.searchOptions[0].field;
                }

                this.setActiveFilter(this.field);
            }
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

        render: function () {
            if (typeof this.searchOptions === "string") {
                SearchView.prototype.render.call(this);
            } else {
                this.$el.html(Mustache.render(advancedSearchTemplate, {
                    fields: this.searchOptions,
                    query: this.filter.get(this.field)
                }));
            }
        },

        onKeyPress: function(event) {
            var code = (event.keyCode ? event.keyCode : event.which);
            if (code == 13) {
                this.search(event);
            }
        },

        changeFilter: function(event) {
            var field = $(event.currentTarget).attr("data-field");

            this.field = field;
            this.setActiveFilter(this.field);

            this.filter.reset();
            this.filter.set(field, this.$el.find("input.search-query").val());

            event.preventDefault();
        }
    });

    return AdvancedSearchView;
});