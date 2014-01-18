/**
 * ShippingTypes View
 */
define([
    'jquery',
    'underscore',
    'backbone',
    'mustache',
    'common/dateFormatter',
    'chart'
], function ($, _, Backbone, Mustache, dateFormatter, Chart) {

    return Backbone.View.extend({

        initialize: function(options) {
            this.model.on("sync", this.render, this);
            this.context = $("#expenseChart").get(0).getContext("2d");
            this.context2 = $("#expenseChart2").get(0).getContext("2d");
            this.context3 = $("#expenseChart3").get(0).getContext("2d");
        },

        setRange: function(startDate, endDate) {
            this.model.setRange(startDate, endDate);
        },

        render: function () {
            this.expensesAndIncomesChart();
            this.incomesMinusExpensesChart();
            this.incomesMinusExpensesCumulativeChart();
        },

        expensesAndIncomesChart: function() {
            var data = {
                labels : this.model.get("labels"),
                datasets : [
                    {
                        fillColor : "rgba(220,220,220,0.5)",
                        strokeColor : "rgba(220,220,220,1)",
                        pointColor : "rgba(220,220,220,1)",
                        pointStrokeColor : "#fff",
                        data : this.model.get("expenses")
                    },
                    {
                        fillColor : "rgba(151,187,205,0.5)",
                        strokeColor : "rgba(151,187,205,1)",
                        pointColor : "rgba(151,187,205,1)",
                        pointStrokeColor : "#fff",
                        data : this.model.get("incomes")
                    }
                ]
            };

            this.chart = new Chart(this.context).Line(data, {bezierCurve : false});
        },

        incomesMinusExpensesChart: function() {
            var data = {
                labels : this.model.get("labels"),
                datasets : [
                    {
                        fillColor : "rgba(151,187,205,0.5)",
                        strokeColor : "rgba(151,187,205,1)",
                        pointColor : "rgba(151,187,205,1)",
                        pointStrokeColor : "#fff",
                        data : this.model.get("incomesMinusExpenses")
                    }
                ]
            };

            this.chart2 = new Chart(this.context2).Line(data, {bezierCurve : false});
        },

        incomesMinusExpensesCumulativeChart: function() {
            var data = {
                labels : this.model.get("labels"),
                datasets : [
                    {
                        fillColor : "rgba(151,187,205,0.5)",
                        strokeColor : "rgba(151,187,205,1)",
                        pointColor : "rgba(151,187,205,1)",
                        pointStrokeColor : "#fff",
                        data : this.model.get("incomesMinusExpensesCumulative")
                    }
                ]
            };

            this.chart3 = new Chart(this.context3).Line(data, {bezierCurve : false});
        }
    });
});