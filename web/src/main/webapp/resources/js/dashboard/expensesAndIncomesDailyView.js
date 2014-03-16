/**
 * ShippingTypes View
 */
define([
    'jquery',
    'underscore',
    'backbone',
    'mustache',
    'common/dateFormatter',
    "bundle!messages",
    'common/highchartsConfig'
], function ($, _, Backbone, Mustache, dateFormatter, messages) {

    return Backbone.View.extend({

        initialize: function(options) {
            this.model.on("sync", this.render, this);
        },

        render: function () {
            this.expensesAndIncomesDailyChart();
            this.incomesMinusExpensesCumulativeChart();
        },

        expensesAndIncomesDailyChart: function() {
            $('#expensesAndIncomesDailyChart').highcharts({
                title: {
                    text: messages["dashboard_chart1_title"],
                    x: -20 //center
                },
                subtitle: {
                    text: messages["dashboard_chart1_subtitle"],
                    x: -20
                },
                xAxis: {
                    type: 'datetime'
                },
                yAxis: {
                    title: {
                        text: messages["dashboard_chart1_yAxis_title"]
                    },
                    plotLines: [{
                        value: 0,
                        width: 1,
                        color: '#808080'
                    }]
                },
                tooltip: {
                    valueSuffix: ' ₴'
                },
                legend: {
                    align: 'left',
                    verticalAlign: 'top',
                    y: 0,
                    floating: true,
                    borderWidth: 0
                },
                series: [{
                    name: messages["dashboard_chart1_series_income"],
                    data: this.model.get("incomesModel").get("data")
                }, {
                    name: messages["dashboard_chart1_series_expense"],
                    data: this.model.get("expenseModel").get("data")
                }]
            });
        },

        incomesMinusExpensesCumulativeChart: function() {
            $('#incomesMinusExpensesCumulativeChart').highcharts('StockChart', {
                rangeSelector : {
                    selected : 1
                },
                title : {
                    text : messages["dashboard_chart3_title"]
                },
                tooltip: {
                    valueSuffix: ' ₴'
                },
                series : [{
                    name : messages["dashboard_chart3_series_name"],
                    data : this.model.get("incomesMinusExpensesCumulative"),
                    tooltip: {
                        valueDecimals: 2
                    }
                }]
            });
        }
    });
});