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
            var expenses = this.model.get("expenseModel").get("data");
            var incomes = this.model.get("incomesModel").get("data");

            this.expensesIncomesSum(expenses, incomes);
            this.expensesIncomesPieChart(expenses, incomes);
        },

        expensesIncomesSum: function(expenses, incomes) {
            $('#expensesIncomesSumChart').highcharts({
                chart: {
                    type: 'column'
                },
                title: {
                    text: messages["dashboard_chart4_title"]
                },
                xAxis: {},
                yAxis: {
                    /*min: 0,*/
                    title: {
                        text: messages["dashboard_chart4_yAxis_title"]
                    }
                },
                tooltip: {
                    headerFormat: '<table>',
                    pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                        '<td style="padding:0"><b>{point.y:.1f} ₴</b></td></tr>',
                    footerFormat: '</table>',
                    shared: true,
                    useHTML: true
                },
                plotOptions: {
                    column: {
                        pointPadding: 0.2,
                        borderWidth: 0
                    }
                },
                series: [{
                    name: messages["dashboard_chart4_series_expense"],
                    data: expenses

                }, {
                    name: messages["dashboard_chart4_series_income"],
                    data: incomes

                }, {
                    name: messages["dashboard_chart4_series_balans"],
                    data: [incomes - expenses]
                }]
            });
        },

        expensesIncomesPieChart: function(expenses, incomes) {
            $('#expensesIncomesPieChart').highcharts({
                chart: {
                    plotBackgroundColor: null,
                    plotBorderWidth: null,
                    plotShadow: false
                },
                title: {
                    text: messages["dashboard_chart4_title"]
                },
                tooltip: {
                    pointFormat: '<b>{point.percentage:.1f}%</b>'
                },
                plotOptions: {
                    pie: {
                        allowPointSelect: true,
                        cursor: 'pointer',
                        dataLabels: {
                            enabled: true,
                            color: '#000000',
                            connectorColor: '#000000',
                            format: '<b>{point.name}</b>: {point.percentage:.1f} %'
                        }
                    }
                },
                series: [{
                    type: 'pie',
                    data: [
                        [
                            messages["dashboard_chart4_series_expense"],
                            expenses[0] * 100 / incomes[0]
                        ],
                        [
                            messages["dashboard_chart4_series_income"],
                            (incomes[0] - expenses[0]) * 100 / incomes[0]
                        ]
                    ]
                }]
            });
        }
    });
});