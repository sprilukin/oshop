/**
 * Bootstrap alert wrapped in backbone view
 */
define([
    'jquery',
    'backbone',
    'mustache',
    'text!common/templates/sorter.html'
], function ($, Backbone, Mustache, sortTemplate) {

    var SortView = Backbone.View.extend({
        events: {
            "click span.sort": "toggleSort"
        },

        initialize: function(options) {
            this.column =  options.column;
            this.sorter = options.sorter;
        },

        render: function() {
            this.setElement($(Mustache.render(".sorter[data-column='{{column}}']", {column: this.column})));

            var sortType = this.sorter.get(this.column);

            var model = {
                noSort: !sortType,
                asc: sortType === "asc",
                desc: sortType === "desc"
            };

            this.$el.html(Mustache.render(sortTemplate, model))
        },

        toggleSort: function() {
            this.sorter.toggle(this.column)
        }
    });

    return SortView;
});