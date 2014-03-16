/**
 * Item Categories module
 */
define([
    'jquery',
    'underscore',
    'backbone',
    'common/sorter',
    'common/filter',
    'common/projection',
    'dashboard/expensesModel',
    'dashboard/incomesModel'
], function ($, _, Backbone, Sorter, Filter, Projection, ExpensesModel, IncomesModel) {

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

            this.projection = new Projection({
                projections: [{
                    name: "date",
                    value: "GROUP"
                }, {
                    name: "amount",
                    value: "SUM"
                }]
            }).format();

            var opts = {
                filter: this.filter.format(),
                sorter: this.sorter,
                projection: this.projection
            };

            this.attributes.expenseModel = new ExpensesModel(opts);
            this.attributes.incomesModel = new IncomesModel(opts);

            this.filter.on("filter:change", this.fetch, this);
        },

        fetch: function(options) {
            this.get("expenseModel").setFilterString(this.filter.format());
            this.get("incomesModel").setFilterString(this.filter.format());

            var that = this;
            return $.when(
                    this.get("expenseModel").fetch(),
                    this.get("incomesModel").fetch()
                ).then(this._processData).then(function() {
                    that.trigger("sync");
                })
        },

        setRange: function(startDate, endDate) {
            this.startDate = startDate;
            this.endDate = endDate;

            this.filter.set("dateBTWN", startDate + "," + endDate);
        },

        _processData: function() {
            this.attributes.incomesMinusExpensesCumulative = [];

            var daysCount = (this.endDate - this.startDate) / 86400000 + 1;
            var expenseSumForDate = 0;
            var incomeSumForDate = 0;

            for (var i = 0; i < daysCount; i++) {
                var date = this.startDate + i * 86400000;

                var expenseForDate = this._getValueForDate(date, this.get("expenseModel").get("data"));
                var incomeForDate = this._getValueForDate(date, this.get("incomesModel").get("data"));
                expenseSumForDate += expenseForDate;
                incomeSumForDate += incomeForDate;

                this.attributes.incomesMinusExpensesCumulative.push([date, incomeSumForDate - expenseSumForDate]);
            }

            this.trigger('sync', this);
        },

        _getValueForDate: function(date, collection) {
            var value = 0;

            if (collection) {
                for (var i = 0; i < collection.length; i++) {
                    if (collection[i][0] < date) {
                        continue;
                    } else if (collection[i][0] >= (date + 86400000)) {
                        return value;
                    } else {
                        value += collection[i][1];
                    }
                }
            }

            return value;
        }
    });
});