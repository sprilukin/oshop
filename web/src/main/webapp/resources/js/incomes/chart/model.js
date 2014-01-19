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

            this.startDate = options.startDate;
            this.endDate = options.endDate;

            this.sorter = new Sorter({
                sorters: [{
                    name: "date",
                    type: "asc"
                }]
            }).format();

            this.filter = new Filter({
                filters: [{
                    name: "dateBTWN",
                    value: this.startDate + (this.endDate ? "," + this.endDate : "")
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
            this.startDate = startDate;
            this.endDate = endDate;

            this.filter.set("dateBTWN", startDate + "," + endDate);
        },

        _processData: function() {
            this.attributes.labels = [];
            this.attributes.expenses = [];
            this.attributes.incomes = [];
            this.attributes.incomesMinusExpenses = [];
            this.attributes.incomesMinusExpensesTimeSeries = [];
            this.attributes.incomesMinusExpensesCumulative = [];
            this.attributes.incomesMinusExpensesCumulativeTimeSeries = [];

            var daysCount = (this.endDate - this.startDate) / 86400000 + 1;
            var expenseSumForDate = 0;
            var incomeSumForDate = 0;

            for (var i = 0; i < daysCount; i++) {
                var date = this.startDate + i * 86400000;

                var expenseForDate = this._getValueForDate(date, this.get("expenseCollection"));
                var incomeForDate = this._getValueForDate(date, this.get("incomesCollection"));
                expenseSumForDate += expenseForDate;
                incomeSumForDate += incomeForDate;

                this.attributes.labels.push(dateFormatter(date).format("YYYY MMM DD"));
                this.attributes.expenses.push(expenseForDate);
                this.attributes.incomes.push(incomeForDate);
                this.attributes.incomesMinusExpenses.push(incomeForDate - expenseForDate);
                this.attributes.incomesMinusExpensesTimeSeries.push([date, incomeForDate - expenseForDate]);
                this.attributes.incomesMinusExpensesCumulative.push(incomeSumForDate - expenseSumForDate);
                this.attributes.incomesMinusExpensesCumulativeTimeSeries.push([date, incomeSumForDate - expenseSumForDate]);
            }

            this.trigger('sync', this);
        },

        _getValueForDate: function(date, collection) {
            var value = 0;

            collection.each(function(model) {
                if ((model.get("date") >= date) && (model.get("date") < (date + 86400000))) {
                    value += model.get("amount");
                }
            });

            return value;
        }
    });
});