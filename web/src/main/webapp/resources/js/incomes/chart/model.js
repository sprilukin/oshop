/**
 * Item Categories module
 */
define([
    'jquery',
    'underscore',
    'backbone',
    'common/dateFormatter',
    'common/sorter',
    'common/filter',
    'expenses/collection',
    'incomes/collection'
], function ($, _, Backbone, dateFormatter, Sorter, Filter, ExpensesCollection, IncomesCollection) {

    return Backbone.Model.extend({

        initialize: function(options) {
            options = options || {};

            _.bindAll(this, "_processData");

            this.sorter = new Sorter({
                sorters: [{
                    name: "date",
                    type: "asc"
                }]
            }).format();

            this.filter = new Filter({
                filters: [{
                    name: "dateBTWN",
                    value: ""
                }]
            });

            var opts = {
                filter: this.filter.format(),
                sorter: this.sorter
            };

            this.attributes.expenseCollection = new ExpensesCollection(opts);
            this.attributes.incomesCollection = new IncomesCollection(opts);

            this.filter.on("filter:change", this.fetch, this);
        },

        fetch: function(options) {
            this.get("expenseCollection").setFilterString(this.filter.format());
            this.get("incomesCollection").setFilterString(this.filter.format());

            return $.when(this.get("expenseCollection").fetch(),
                    this.get("incomesCollection").fetch())
                .then(this._processData);
        },

        setRange: function(startDate, endDate) {
            this.filter.set("dateBTWN", startDate + "," + endDate);
        },

        _processData: function() {
            //TODO
            this.trigger('sync', this);
        }
    });
});