/**
 * Item Categories module
 */
define([
    'jquery',
    'underscore',
    'backbone',
    'common/sorter',
    'common/projection',
    'dashboard/expensesModel',
    'dashboard/incomesModel'
], function ($, _, Backbone, Sorter, Projection, ExpensesModel, IncomesModel) {

    return Backbone.Model.extend({

        initialize: function(options) {
            options = options || {};

            this.filter = options.filter;

            this.projection = new Projection({
                projections: [{
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
            return $.when(this.get("expenseModel").fetch(),
                    this.get("incomesModel").fetch()).then(function() {
                        that.trigger("sync");
                    });
        }
    });
});